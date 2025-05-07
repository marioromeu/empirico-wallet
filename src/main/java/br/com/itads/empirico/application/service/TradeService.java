package br.com.itads.empirico.application.service;

import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.application.domain.Position;
import br.com.itads.empirico.application.domain.Result;
import br.com.itads.empirico.application.domain.Trade;
import br.com.itads.empirico.application.repository.PositionRepository;
import br.com.itads.empirico.application.repository.TradeRepository;

public class TradeService {

	private PositionRepository positionRepository;	
	private TradeRepository tradeRepository;
	
	public TradeService(
			PositionRepository positionRepository,	
			TradeRepository tradeRepository) {
		this.positionRepository = positionRepository;	
		this.tradeRepository = tradeRepository; 
	}
	
	/**
	 * RF1 – Cadastrar operações sobre um ativo :
	 *	1.1-COMPRA
	 *	1.2-VENDA		
	 * @param trade
	 */
	public Position processTrade(Trade trade, UUID uuid) {
		Position position = positionRepository.getBy(uuid);
		if (Objects.isNull(position)) {
			position = new Position(trade);
		} else {
			position.incAsset(trade);
		}
		return position;
	}

	/**
 	 * RF1 – Cadastrar operações sobre um ativo :
	 *	1.3-LUCRO
	 * @param result
	 * @param uuid
	 */
	public Position processResult(Result result, UUID positionUuid) {
		Position position = positionRepository.getBy(positionUuid);
		if (Objects.nonNull(position)) {
			position.addResult(null, result);
		}
		return position;
	}

	/**
	 * RF4 – Salvar data de compra dos ativos, para permitir calcular histórico de valorização pelo tempo de carrego do ativo.
	 * @param trade
	 */
	public void saveTrade(Trade trade) {
		tradeRepository.saveOrUpdate(trade);
	}
	
}
