package br.com.itads.empirico.adapters.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record DashboardAdapterDTO(
		DashboardDTO dto,
		BigDecimal rentability,
		BigDecimal rentabilityPlusResults, 
		BigDecimal yield) implements Serializable {
}