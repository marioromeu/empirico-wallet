package br.com.itads.empirico.application.domain;

import java.time.LocalDateTime;

import br.com.itads.empirico.application.domain.enums.ResultTypeEnum;

public class Result {
	
	public Result(LocalDateTime datetime, Double price, String description, ResultTypeEnum resultTypeEnum) {
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
	Double price;
	
	/**
	 * Descrição da origem e motivo do resultado
	 */
	String description;
	
	/**
	 * Tipo do resultado : 
	 */
	ResultTypeEnum resultTypeEnum;
}