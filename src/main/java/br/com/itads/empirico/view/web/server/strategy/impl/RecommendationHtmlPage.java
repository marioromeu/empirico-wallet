package br.com.itads.empirico.view.web.server.strategy.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.tinylog.Logger;

import com.sun.net.httpserver.HttpExchange;

import br.com.itads.empirico.adapters.dto.RecommendationDTO;
import br.com.itads.empirico.adapters.in.RecommendationAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Recommendation;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.view.web.server.strategy.HtmlPage;

public class RecommendationHtmlPage implements HtmlPage {

	private WalletAdapter walletAdapter;
	private RecommendationAdapter recommendationAdapter;
	
	public RecommendationHtmlPage() {
		this.recommendationAdapter = AdapterBuilder.buildRecommendationAdapter();
		this.walletAdapter = AdapterBuilder.buildWalletAdapter();
	}
	
	@Override
	public String handle(Object o) {
		return "";
	}

	@Override
	public Optional<List<String>> fillStringParams(HttpExchange t) {
		UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
		User user = new User("Mario Romeu", "marioromeu");
		
		String assetTicker;
		String params = t.getRequestURI().getRawQuery();
		List<String> list = new ArrayList<>();
		
		list.add( user.getName() );
		
		if (Objects.nonNull(params)) {
			assetTicker = params.split("=")[1];						
			
			list.add( buildHtmlTableAsString(assetTicker, user, uuid) );			
		} else {
			buildRecomendations(uuid, user);
			list.add( "Recomendacoes atualizadas com sucesso!" );
		}		
		return Optional.of( list );
	}

	private String buildHtmlTableAsString(String assetTicker, User user, UUID uuid) {
		
		List<RecommendationDTO> dataList = getDataList(assetTicker, user, uuid);
        
		String htmlTable = 
				"""
					<table border=1 align=center>
						<tr>
							<td>
								UUID
							</td>
							<td>
								INICIO VALIDADE
							</td>
							<td>
								FIM VALIDADE
							</td>
							<td>
								TIPO
							</td>
							<td>
								DESCRICAO
							</td>
							<td>
								LINK
							</td>
						</tr>
						"""
						+
						buildContent(dataList)
						+
						"""
					</table>
				""";

        return htmlTable;

	}
	
	private String buildContent(List<RecommendationDTO> dataList) {

		StringBuilder response = new StringBuilder();
		
		for (RecommendationDTO recommendationDTO : dataList) {
			
			String color = recommendationDTO.recommendationTypeEnum().getHtmlBackgroundTagColor();
			
			response.append("	<tr>									");
			response.append("		<td"+ color +">						");
			response.append(			recommendationDTO.uuid() 		 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(	format(recommendationDTO.initDateTime()) );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(	format(recommendationDTO.endDateTime())	 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			title(recommendationDTO)	 	 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			recommendationDTO.description()  );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			recommendationDTO.link() 		 );
			response.append("		</td>								");
			response.append("	</tr>									");
			
		}

		return response.toString();

	}

	private String format(LocalDateTime localDateTime) {
		return localDateTime.format(
				new DateTimeFormatterBuilder()
					.appendPattern("dd/MM/yyyy HH:mm")
					.toFormatter()
		);
	}

	private String title(RecommendationDTO recommendationDTO) {
		return recommendationDTO.degree() + " - " + recommendationDTO.recommendationTypeEnum().name();
	}

	private List<RecommendationDTO> getDataList(String assetTicker, User user, UUID uuid) {
		
		List<RecommendationDTO> dataList = new ArrayList<>();
		
		Map<String, List<Recommendation>> map = recommendationAdapter.getRecommendationsFor(assetTicker, user, uuid);
		
		map.entrySet().stream().forEach(entry -> {
				List<Recommendation> recommendations = entry.getValue();
				recommendations.stream().forEach(recommendation -> {
						dataList.add(new RecommendationDTO(
								recommendation.getUuid(), 
								recommendation.getInitDateTime(), 
								recommendation.getDescription(),
								recommendation.getEndDateTime(),
								recommendation.getRecommendationTypeEnum(),
								recommendation.getAsset(),
								recommendation.getDegree(),
								recommendation.getLink()
						));
				});
		});

    	Logger.info("Retornando datalist: " + dataList.size());
        return dataList;

	}

	private void buildRecomendations(UUID uuid, User user) {
		Wallet wallet = walletAdapter.getWallet(uuid, user);
		wallet.getPositionByAssetClass().keySet().stream().forEach(key -> {
			System.out.println("Building recommendation for " + key);
			recommendationAdapter.buildRecommendationFor(key, user.getUsername(), uuid.toString());
		});
	}	

}
