package br.com.itads.empirico.view.web.server.strategy.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.tinylog.Logger;

import com.sun.net.httpserver.HttpExchange;

import br.com.itads.empirico.adapters.dto.DashboardDTO;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.util.FixUtils;
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
					<table border=1 align=center>
						<tr>
							<td>
								REALIZAVEL (R$)
							</td>
							<td>
								TOTAL %
							</td>							
							<td>
								TOTAL + LUCROS (R$)
							</td>
							<td>
								TOTAL %
							</td>
						</tr>
						<tr>
							<td>
								"""+paint(String.format("R$ %,.2f", calcTotalRealizavel(dataList)))+"""
							</td>						
							<td>
								"""+calcTotalRealizavelPercent(dataList)+"""
							</td>
							
							<td>
								"""+paint(String.format("R$ %,.2f", calcTotalRS(dataList)))+"""
							</td>
							<td>
								"""+calcTotalPercent(dataList)+"""
							</td>
						</tr>						
					</table>				
				
					<table border=1 align=center>
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
							<td>
								R.O.I.
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
			
			BigDecimal rentability = dashboardDTO.calcRentabilityAddResults();
			String color = rentability.compareTo(BigDecimal.ZERO) < 0 ? " style=\"background-color:#ffaaaa;\"" : " style=\"background-color:#aaffaa;\"";
			String spanColor = rentability.compareTo(BigDecimal.ZERO) < 0 ? " style=\"color:#ffaaaa;\"" : " style=\"color:#aaffaa;\"";

			BigDecimal yield = dashboardDTO.calcYield();
			
			response.append("	<tr>									");
			response.append("		<td"+ color +">						");
			response.append(			dashboardDTO.asset()	 		 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			dashboardDTO.quantity()	 		 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			dashboardDTO.averagePrice()		 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			 dashboardDTO.actualQuote()	 	 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			dashboardDTO.calcRentability()	 );
			response.append("		%</td>								");
			response.append("		<td"+ color +">						");
			response.append(			dashboardDTO.calcTotalOfInvest() );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(			dashboardDTO.totalResult()	 	 );
			response.append("		</td>								");
			response.append("		<td"+ color +">						");
			response.append(     		rentability						 );
			response.append("		%</td>								");
			response.append("		<td>								");
			response.append(" 			<span "+spanColor+">"			 ); 
			response.append( 				yield + " %"				 );
			response.append(" 			</span>       					");
			response.append("		</td>								");			
			response.append("	</tr>									");
			response.append("	     									");
		}
		return response.toString();
	}

	private List<DashboardDTO> getDataList(UUID uuid) {
		
		List<DashboardDTO> dataList = new ArrayList<>();
		
		Wallet wallet = walletAdapter.doDashboard(uuid);
		
		for (Map.Entry<String, Position> key : wallet.getPositionByAssetClass().entrySet()) {

			Logger.info("agora="+key.getValue().getAssetTicker());
			
        	BigDecimal quote = BigDecimal.ZERO;
        	
        	String localKey = FixUtils.fix(key.getKey());

    		quote = AdapterBuilder.buildAssetAdapter().getLastAssetQuote(localKey).getClosedPrice();
    		
        	String result = (quote.compareTo(key.getValue().getPositionTotalPrice())) < 0 ? "POSITIVO" : "NEGATIVO";
        	
        	dataList.add(
        			new DashboardDTO(
        					localKey, 
        					key.getValue().getQuantity(), 
        					key.getValue().getPositionAveragePrice(),
        					quote,
        					key.getValue().getPositionTotalPrice(),
        					key.getValue().getTotalResults(),        					
        					result//rentabilidade + dividendo
        			)
        	);
        	Logger.info("Posição encontrada: " + key.getValue().getAssetTicker() + "[" + key.getValue().getPositionTotalPrice()+"]");

		}

    	Logger.info("Retornando datalist: " + dataList.size());
        return dataList.stream().sorted(Comparator.comparing(DashboardDTO::calcYield).reversed()).toList();
    	//return dataList.stream().sorted(Comparator.comparing(DashboardDTO::calcRentabilityAddResults).reversed()).toList();

	}

	private String calcTotalRealizavelPercent(List<DashboardDTO> dataList) {
		BigDecimal deltaTotalInvested = calcTotalRealizavel(dataList);
		BigDecimal actualPosition = calcTotalActualQuote(dataList);
		BigDecimal delta = deltaTotalInvested.compareTo(BigDecimal.ZERO) >= 0 ? deltaTotalInvested : deltaTotalInvested.multiply(new BigDecimal(-1));
		BigDecimal applyiedResources = delta.add(actualPosition);
		BigDecimal b = delta.multiply(new BigDecimal("100")).divide(applyiedResources, RoundingMode.HALF_UP);
		if (deltaTotalInvested.compareTo(BigDecimal.ZERO) < 0) {
			b = b.multiply(new BigDecimal(-1));
		}
		String s = b.toString().concat(" %");
		return paint(s); 
	}

	private String calcTotalPercent(List<DashboardDTO> dataList) {

		BigDecimal deltaTotalInvested = calcTotalRS(dataList);
		BigDecimal actualPosition = calcTotalActualQuote(dataList);
		BigDecimal delta = deltaTotalInvested.compareTo(BigDecimal.ZERO) >= 0 ? deltaTotalInvested : deltaTotalInvested.multiply(new BigDecimal(-1));
		BigDecimal applyiedResources = delta.add(actualPosition);
		BigDecimal b = delta.multiply(new BigDecimal("100")).divide(applyiedResources, RoundingMode.HALF_UP);
		if (deltaTotalInvested.compareTo(BigDecimal.ZERO) < 0) {
			b = b.multiply(new BigDecimal(-1));
		}
		String s = b.toString().concat(" %");
		return paint(s); 
	}	
	
	private BigDecimal calcTotalRealizavel(List<DashboardDTO> dataList) {
		return dataList.stream()
				.map(DashboardDTO::calcTotalOfInvest)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private BigDecimal calcTotalActualQuote(List<DashboardDTO> dataList) {
		return dataList.stream()
			.map(DashboardDTO::calcActualPosition)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
	private BigDecimal calcTotalRS(List<DashboardDTO> dataList) {
		return dataList.stream()
				.map(DashboardDTO::calcTotalOfInvestAddResult)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private String paint(String s) {
		if (s.contains("-")) {
			return "<span style=\"color:red;\">" + s + "</span>";
		} else {
			return "<span style=\"color:green;\">" + s + "</span>";
		}
	}

}
