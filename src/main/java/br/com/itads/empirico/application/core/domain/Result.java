package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.itads.empirico.application.core.domain.enums.ResultTypeEnum;

public class Result implements Serializable {
	
	private static final long serialVersionUID = 3222996493118313990L;

	public Result(LocalDateTime datetime, BigDecimal price, String description, ResultTypeEnum resultTypeEnum) {
		this.datetime = datetime ; 
		this.price = price; 
		this.description = description; 
		this.resultTypeEnum = resultTypeEnum; 
	}
	
	/**
	 * Momento em que o resultado foi entregue para a carteira
	 */
	LocalDateTime datetime;
	
	/**
	 * Valor do resultado
	 */
	BigDecimal price;
	
	/**
	 * Descrição da origem e motivo do resultado
	 */
	String description;
	
	/**
	 * Tipo do resultado : 
	 */
	ResultTypeEnum resultTypeEnum;

	public BigDecimal getPrice() {
		return price;
	}

}