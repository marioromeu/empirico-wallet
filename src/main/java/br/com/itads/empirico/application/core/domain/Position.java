package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.enums.ResultTypeEnum;

public class Position implements Serializable {
	
	private static final long serialVersionUID = -7432859363317610079L;

	private String assetTicker;
	
	/**
	 * quantidade de ativos da posição
	 */
	private BigDecimal quantity = BigDecimal.ZERO;
	
	/**
	 * Preço médio de uma posição
	 */
	private BigDecimal averagePrice = BigDecimal.ZERO;

	/**
	 * Preço médio de uma posição
	 */
	private BigDecimal averagePriceWithResults = BigDecimal.ZERO;	
	
	/**
	 * Preço total de uma posição
	 */
	private BigDecimal totalPrice = BigDecimal.ZERO;

	/**
	 * Preço total de uma posição
	 */
	private BigDecimal totalPriceWithResults = BigDecimal.ZERO;
	
	/**
	 * Lista dos trades realizados sobre o ativo
	 */
	private Map<UUID, Trade> trades = new HashMap<>();
	
	/**
	 * Lista dos trades realizados sobre o ativo
	 */
	private List<Result> results = new ArrayList<>();

	public Position(String assetTicker) {
		this.assetTicker = assetTicker;
	}
	
	public void consolidate() {
		totalPrice = BigDecimal.ZERO;
		totalPriceWithResults = BigDecimal.ZERO;
		
		/* Get all trades */
		for (Map.Entry<UUID, Trade> trade : this.trades.entrySet()) {		
			totalPrice = totalPrice.add(trade.getValue().getTotalTradeValue());
		};
		/* calc average price of position */
		averagePrice = totalPrice.divide(quantity, RoundingMode.HALF_UP);//16.84
		
		totalPriceWithResults = totalPrice;

		/* Get all results */
		this.results.stream().forEach(result -> {
			totalPriceWithResults = totalPriceWithResults.add(result.price());
		});

		BigDecimal resultPrice = getResultPrice();
		BigDecimal totalPriceSubResults = totalPrice.subtract(resultPrice);
		
		/* calc average price of position */
		averagePriceWithResults = totalPriceSubResults.divide(quantity, RoundingMode.HALF_UP);//16.84

	}
	
	public Position mergeTrades(Position newPosition) {
		this.trades.putAll(newPosition.trades);
		return this;
	}
	
	public Position incAsset(Trade buy) {
		trades.put(buy.getUuid(), buy);
		quantity = quantity.add(buy.getQuantity());
		return this;
	}
	
	public Position addResult(Result result) {
		results.add(result);
		return this;
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

	public List<Result> getResults() {
		return results;
	}
	
	public BigDecimal getTotalResults() {
		BigDecimal total = results.stream()
				.map(Result::price)
				.reduce(BigDecimal.ZERO, BigDecimal::add);		
		return total;
	}
	
	public String getAssetTicker() {
		return assetTicker;
	}

	public BigDecimal getPositionAveragePrice() {
		return averagePrice;
	}
	
	public BigDecimal getPositionTotalPrice() {
		return totalPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPositionAveragePriceWithResults() {
		BigDecimal result = getResultPrice();
		BigDecimal divided = result.divide(quantity, RoundingMode.HALF_UP);
		return averagePrice.subtract( divided );
	}

	private BigDecimal getResultPrice() {
		BigDecimal resultPrice =BigDecimal.ZERO;
		for (Result resultParam : results) {
			resultPrice = resultPrice.add( resultParam.price() );	
		}
		return resultPrice;
	}
	
	public BigDecimal getPositionTotalPriceWithResults() {
		return totalPrice.add(getResultPrice());
	}

}