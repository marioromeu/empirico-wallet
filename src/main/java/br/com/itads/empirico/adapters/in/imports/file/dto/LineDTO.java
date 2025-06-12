package br.com.itads.empirico.adapters.in.imports.file.dto;

import java.math.BigDecimal;

public record LineDTO(
		String data, 
		String papel, 
		String descricao, 
		BigDecimal quantidade, 
		BigDecimal precoOperacao,
		BigDecimal financeiroOperacao, 
		BigDecimal saldoOperacao, 
		BigDecimal precoMedio, 
		BigDecimal proventos, 
		BigDecimal saldoProvento,
		BigDecimal precoMedioComProventos) {
		
	}