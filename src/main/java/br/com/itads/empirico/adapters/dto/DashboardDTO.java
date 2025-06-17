package br.com.itads.empirico.adapters.dto;

public record DashboardDTO(
		String asset, 
		String quantity, 
		String averagePrice, 
		String actualQuote, 
		String profitability,
		String totalPosition, 
		String totalResult, 
		String profitabilityPlusResults) {
}