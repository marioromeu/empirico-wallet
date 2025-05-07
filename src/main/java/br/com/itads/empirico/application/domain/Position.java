package br.com.itads.empirico.application.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.application.domain.enums.ResultTypeEnum;

public class Position {
	
	/**
	 * quantidade de ativos da posição
	 */
	private Double quantity = 0d;
	
	/**
	 * Preço médio de uma posição
	 */
	private Double averagePrice = 0d;
	
	/**
	 * Preço total de uma posição
	 */
	private Double totalPrice = 0d;

	/**
	 * Lista dos trades realizados sobre o ativo
	 */
	private Map<UUID, Trade> trades = new HashMap<>();
	
	/**
	 * Lista dos trades realizados sobre o ativo
	 */
	private List<Result> results = new ArrayList<>();

	public Position(Trade trade) {
		incAsset(trade);
	}
	
	public void consolidate() {		
		this.trades.entrySet().stream().forEach(trade -> {
			totalPrice += trade.getValue().getTotalTradeValue();
		});		
		averagePrice = (totalPrice / quantity);
	}
	
	public Position mergeTrades(Position newPosition) {
		this.trades.putAll(newPosition.trades);
		return this;
	}
	
	public void incAsset(Trade buy) {
		trades.put(buy.getUuid(), buy);
		quantity += buy.getQuantity();
	}
	
	public void addResult(String ticker, Result result) {
		results.add(result);
	}

	/**
	 * TODO corrigir o comportamento 
	 * @param ticker
	 * @param result
	 */
	public void decAsset(Trade buy) {
		trades.remove(buy.getUuid());
	}

	public List<Result> getProfits() {
		return getResults(
				List.of(
						ResultTypeEnum.ALUGUEL,
						ResultTypeEnum.DIVIDENDO,
						ResultTypeEnum.JCP,
						ResultTypeEnum.RENDIMENTO
				)
		);
	}
	
	public List<Result> getCosts() {
		return getResults(
				List.of(
						ResultTypeEnum.COME_COTAS,
						ResultTypeEnum.CORRETAGEM
				)
		);
	}
	
	private List<Result> getResults(List<ResultTypeEnum> resultTypeEnums) {
		return results;
	}
	
	
	/**
	 * Realiza operação de compra de um ativo
	 */
	public void process(Trade trade) {
		if (trade.getTotalTradeValue() > 0) {
			this.incAsset(trade);
		} else {
			this.decAsset(trade);
		}
	}

	public String getAssetTicker() {
		return trades.entrySet().stream().findFirst().get().getValue().getAsset().getTicker();
	}

	public Double getPositionAveragePrice() {
		System.out.println("Preço medio -> "+ averagePrice);
		return averagePrice;
	}
	
	public Double getPositionTotalPrice() {
		return totalPrice;
	}	
	
}