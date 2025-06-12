package br.com.itads.empirico.application.core.service;

import java.util.Objects;

import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.ports.out.repository.PositionRepository;
import br.com.itads.empirico.application.ports.out.repository.TradeRepository;

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
	public Position processTrade(Trade trade, String ticker) {
		Position position = positionRepository.getBy(ticker);
		if (Objects.isNull(position)) {
			position = new Position(ticker);
		}

		position = position.incAsset(trade);
		
		positionRepository.saveOrUpdate(position);
		return position;
	}

	/**
 	 * RF1 – Cadastrar operações sobre um ativo :
	 *	1.3-LUCRO
	 * @param result
	 * @param uuid
	 */
	public Position processResult(Result result, String ticker) {
		Position position = positionRepository.getBy(ticker);
		if (Objects.isNull(position)) {
			position = new Position(ticker);
		}
		
		position = position.addResult(result);
		
		positionRepository.saveOrUpdate(position);
		return position;
	}

	/**
	 * RF4 – Salvar data de compra dos ativos, para permitir calcular histórico de valorização pelo tempo de carrego do ativo.
	 * @param trade
	 */
	public void saveTrade(Trade trade) {
		tradeRepository.saveOrUpdate(trade);
	}

	public Position getPosition(String ticker) {
		return positionRepository.getBy( ticker );
	}
	
}
