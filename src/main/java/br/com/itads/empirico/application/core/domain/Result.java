package br.com.itads.empirico.application.core.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.itads.empirico.application.core.domain.enums.ResultTypeEnum;

public record Result (
		String assetTicker, 
		LocalDateTime datetime, 
		BigDecimal price, 
		String description, 
		ResultTypeEnum resultTypeEnum) implements Serializable {

}