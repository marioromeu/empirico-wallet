package br.com.itads.empirico.adapters.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public record DashboardDTO(
		String asset, 
		BigDecimal quantity, 
		BigDecimal averagePrice, 
		BigDecimal actualQuote,
		BigDecimal totalPosition, 
		BigDecimal totalResult,
		String profitabilityPlusResults) implements Serializable {
	
	public BigDecimal calcRentability() {
		BigDecimal actual = actualQuote.multiply(new BigDecimal(""+100));
		BigDecimal total = actual.divide(averagePrice, RoundingMode.HALF_UP);
		BigDecimal response = total.subtract(new BigDecimal(""+100) );//remove 100% para pegar apenas o excedente
		return response;
	}
	
	public BigDecimal calcActualPosition() {
		return actualQuote.multiply(quantity);
	}

	public BigDecimal calcRentabilityAddResults() {
		BigDecimal rentability = calcRentability();
		return rentability.add(calcRentabilityOfResult());
	}

	public BigDecimal calcRentabilityOfResult() {		
		BigDecimal totalPrincipal = calcTotalOfInvest();
		BigDecimal rentabilityOfPrincipal = calcRentability();		
		BigDecimal base = totalResult.multiply( rentabilityOfPrincipal );
		return base.divide( totalPrincipal, RoundingMode.HALF_UP );		
	}
	
	public BigDecimal calcTotalOfInvest() {
		BigDecimal totalOfInvestment = averagePrice.multiply(quantity);//quanto investi
		BigDecimal totalOfPosition = actualQuote.multiply(quantity);//quanto vale hj
		return totalOfPosition.subtract(totalOfInvestment);
	}

	public BigDecimal calcTotalOfInvestAddResult() {
		BigDecimal delta = actualQuote.subtract(averagePrice);
		BigDecimal liquidPosition = delta.multiply(quantity);//quanto vale hj
		return liquidPosition.add(totalResult);
	}

	public BigDecimal calcYield() {
		BigDecimal myRentability = calcRentability().setScale(2, RoundingMode.HALF_UP);
		BigDecimal myResults = calcRentabilityAddResults().setScale(2, RoundingMode.HALF_UP);
		return myResults.subtract(myRentability);
	}
	
}