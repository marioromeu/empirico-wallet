package br.com.itads.empirico.application.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Wallet {

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
	private Double consolidatedValue = 0d;
	
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
	public Wallet() {
	}

	
	
	/**
	 * Método para consolidar todos os valores das posições e atualizar o valor total da carteira.
	 */
	public void consolidate() {
		positionAssets.entrySet().stream().forEach(entryPosition -> {
			entryPosition.getValue().consolidate();
			System.out.println(entryPosition.getValue().getPositionAveragePrice());
			consolidatedValue += entryPosition.getValue().getPositionTotalPrice();
		});		
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
	public Double getConsolidatedValue() {
		return consolidatedValue;
	}

	public UUID getUuid() {
		return uuid;
	}
	
}
