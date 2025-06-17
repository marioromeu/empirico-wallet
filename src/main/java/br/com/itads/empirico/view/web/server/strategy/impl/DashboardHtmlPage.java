package br.com.itads.empirico.view.web.server.strategy.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sun.net.httpserver.HttpExchange;

import br.com.itads.empirico.adapters.dto.DashboardDTO;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.view.web.server.strategy.HtmlPage;

public class DashboardHtmlPage implements HtmlPage {

	WalletAdapter walletAdapter;
	
	public DashboardHtmlPage() {
		walletAdapter = AdapterBuilder.buildWalletAdapter();
	}
	
	@Override
	public String handle(Object o) {
		return "";
	}

	@Override
	public Optional<List<String>> fillStringParams(HttpExchange t) {
		UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
		User user = new User("Mario Romeu", "marioromeu");

		List<String> list = new ArrayList<>();
		
		list.add( user.getName() );
		list.add( buildHtmlTableAsString(uuid) );
		
		return Optional.of( list );
	}

	private String buildHtmlTableAsString(UUID uuid) {
		
		List<DashboardDTO> dataList = getDataList(uuid);
        
		String htmlTable = 
				"""
				<table border=1>
					<tr>
						<td>
							ATIVO
						</td>
						<td>
							QTDE
						</td>
						<td>
							PRECO MEDIO
						</td>
						<td>
							PRECO ATUAL
						</td>
						<td>
							RENTABILIDADE
						</td>
						<td>
							RESULTADO
						</td>
						<td>
							PROVENTOS
						</td>
						<td>
							RENTABILIDADE+PROVENTOS
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

	private String buildContent(List<DashboardDTO> dataList) {
		StringBuilder response = new StringBuilder();
		for (DashboardDTO dashboardDTO : dataList) {
			System.out.println(dashboardDTO);
			
			response.append("	<tr>								");
			response.append("		<td>							");
			response.append(			dashboardDTO.asset()	 	);
			response.append("		</td>							");
			response.append("		<td>							");
			response.append(			dashboardDTO.quantity()	 	);
			response.append("		</td>							");
			response.append("		<td>							");
			response.append(			dashboardDTO.averagePrice()	 );
			response.append("		</td>							");
			response.append("		<td>							");
			response.append(			dashboardDTO.actualQuote()	 );
			response.append("		</td>							");
			response.append("		<td>							");
			response.append(			dashboardDTO.profitability() );
			response.append("		</td>							");
			response.append("		<td>							");
			response.append(			dashboardDTO.totalPosition() );
			response.append("		</td>							");
			response.append("		<td>							");
			response.append(			dashboardDTO.totalResult()	 );
			response.append("		</td>							");
			response.append("		<td>							");
			response.append( dashboardDTO.profitabilityPlusResults() );
			response.append("		</td>							");			
			response.append("	</tr>								");
			response.append("	     								");
		}
		return response.toString();
	}

	private List<DashboardDTO> getDataList(UUID uuid) {
		
		List<DashboardDTO> dataList = new ArrayList<>();
		
		Wallet wallet = walletAdapter.doDashboard(uuid);
		
        wallet.getPositionByAssetClass().entrySet().forEach( key -> {
        	
        	BigDecimal quote = AdapterBuilder.buildAssetAdapter().getLastAssetQuote(key.getKey()).getClosedPrice();
        	
        	String result = (quote.compareTo(key.getValue().getPositionTotalPrice())) < 0 ? "POSITIVO" : "NEGATIVO";
        	
        	dataList.add(
        			new DashboardDTO(
        					key.getKey(), 
        					key.getValue().getQuantity().toString(), 
        					key.getValue().getPositionTotalPrice().toString(), 
        					key.getValue().getPositionAveragePrice().toString(), 
        					quote.toString(), 
        					key.getValue().getPositionTotalPriceWithResults().toString(), 
        					key.getValue().getPositionAveragePriceWithResults().toString(), 
        					result
        			)
        	);

        });

        return dataList;

	}

}
