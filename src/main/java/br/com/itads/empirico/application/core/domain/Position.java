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
	 * Preço total de uma posição
	 */
	private BigDecimal totalPrice = BigDecimal.ZERO;

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
		
		/* Get all trades */
		this.trades.entrySet().stream().forEach(trade -> {
			totalPrice = totalPrice.add(trade.getValue().getTotalTradeValue());
		});
		
		/* Get all results */
		this.results.stream().forEach(result -> {
			totalPrice = totalPrice.add(result.getPrice());
		});
		
		/* calc average price of position */
		averagePrice = totalPrice.divide(quantity, RoundingMode.HALF_UP);
	}
	
	public Position mergeTrades(Position newPosition) {
		this.trades.putAll(newPosition.trades);
		return this;
	}
	
	public void incAsset(Trade buy) {
		trades.put(buy.getUuid(), buy);
		quantity = quantity.add(buy.getQuantity());
	}
	
	public void addResult(String ticker, Result result) {
		results.add(result);
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

	public String getAssetTicker() {
		return assetTicker;
	}

	public BigDecimal getPositionAveragePrice() {
		return averagePrice;
	}
	
	public BigDecimal getPositionTotalPrice() {
		return totalPrice;
	}
	
	public BigDecimal getPositionAveragePriceWithResults() {		
		return averagePrice.add( (getResultPrice().divide(quantity)) );
	}

	private BigDecimal getResultPrice() {
		BigDecimal resultPrice =BigDecimal.ZERO;
		for (Result resultParam : results) {
			resultPrice.add( resultParam.getPrice() );	
		}
		return resultPrice;
	}
	
	public BigDecimal getPositionTotalPriceWithResults() {
		return totalPrice.add(getResultPrice());
	}

}