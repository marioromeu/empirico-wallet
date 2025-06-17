package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Wallet implements Serializable {

	private static final long serialVersionUID = 8126885014554266414L;

	/**
	 * Código unico da carteira
	 */
	private UUID uuid;
	
	/**
	 * Nome da carteira
	 */
	private String name;
	
	/**
	 * Valor total da carteira
	 */
	private BigDecimal consolidatedValue = BigDecimal.ZERO;
	
	/**
	 * Owner da carteira
	 */
	private User user;
	
	/**
	 * Posições da carteira
	 */
	private Map<String, Position> positionAssets = new HashMap<>();
	
	
	
	/**
	 * Constructor
	 */
	public Wallet(UUID uuid, User user) {
		this.uuid = uuid;
		this.user = user;
	}

	
	
	/**
	 * Método para consolidar todos os valores das posições e atualizar o valor total da carteira.
	 */
	public void consolidate() {
		consolidatedValue = BigDecimal.ZERO;
		
		for (Map.Entry<String, Position> entryPosition : positionAssets.entrySet()) {
			entryPosition.getValue().consolidate();
			consolidatedValue = consolidatedValue.add(entryPosition.getValue().getPositionTotalPrice());
		}

	}
	
	/**
	 * Método para categorizar o tamanho das posições da carteira por classe de ativo.
	 */
	public Map<String, Position> getPositionByAssetClass() {
		return positionAssets;
	}
	
	public void updatePosition(Position position) {
		Position p = positionAssets.get(position.getAssetTicker());
		
		if (p != null) {
			p = p.mergeTrades(position);
		} else {
			p = position;
		}

		positionAssets.put(position.getAssetTicker(), p);

	}
	
	/**
	 * Método para obter o valor total da carteira
	 * @return
	 */
	public BigDecimal getConsolidatedValue() {
		return consolidatedValue;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public User getUser() {
		return user;
	}
	
}
