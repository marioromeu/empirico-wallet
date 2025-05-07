package br.com.itads.empirico.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.domain.Asset;
import br.com.itads.empirico.application.domain.Position;
import br.com.itads.empirico.application.domain.Result;
import br.com.itads.empirico.application.domain.Trade;
import br.com.itads.empirico.application.repository.PositionRepository;
import br.com.itads.empirico.application.repository.TradeRepository;
import br.com.itads.empirico.mock.MockBuilderForTestsJunit;

class TradeServiceTest {

	PositionRepository positionRepository = mock(PositionRepository.class);	
	TradeRepository tradeRepository = mock(TradeRepository.class);
	
	TradeService tradeService;
	
	
	TradeServiceTest() {
		tradeService = new TradeService(positionRepository, tradeRepository);
	}
	
	/**
	 * RF01 – Cadastrar operações sobre um ativo :
	 *	1.1-COMPRA
	 *	1.2-VENDA		
	 * @param trade
	 */
	@Test
	void RF01_11_E_12_processTrade_with_position_ok_only_one_buy() {
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade trade = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(trade));		
		Position position = tradeService.processTrade(trade, bitcoin.getTicker());
		position.consolidate();
		assertNotNull(position);		
	}

	@Test
	void RF01_11_E_12_processTrade_with_position_not_ok_only_one_buy() {
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade trade = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		
		when(positionRepository.getBy(any())).thenReturn(null);		
		Position position = tradeService.processTrade(trade, bitcoin.getTicker());
		position.consolidate();
		assertTrue(position.getPositionTotalPrice() > 0);		
	}	

	@Test
	void RF01_11_E_12_processTrade_with_position_ok_two_or_more_buy() {
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade trade1 = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		Trade trade2 = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		Trade trade3 = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);

		Double sumOfPositions = 0d;
		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(trade1));		
		Position position1 = tradeService.processTrade(trade1, bitcoin.getTicker());
		position1.consolidate();
		sumOfPositions += trade1.getTotalTradeValue();
		assertNotNull(position1);

		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(trade2));
		Position position2 = tradeService.processTrade(trade2, bitcoin.getTicker());
		position2.consolidate();
		sumOfPositions += trade2.getTotalTradeValue();
		assertNotNull(position2);
		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(trade3));
		Position position3 = tradeService.processTrade(trade3, bitcoin.getTicker());
		position3.consolidate();
		sumOfPositions += trade3.getTotalTradeValue();
		assertNotNull(position3);

		Position consolidatedPosition = tradeService.getPosition(bitcoin.getTicker());
		
		assertNotNull(consolidatedPosition);
		assertEquals(consolidatedPosition.getPositionTotalPrice(), sumOfPositions);
		assertTrue(consolidatedPosition.getPositionAveragePrice().equals((sumOfPositions/3)));

	}
	
	
	/**
 	 * RF01 – Cadastrar operações sobre um ativo :
	 *	1.3-LUCRO
	 * @param result
	 * @param uuid
	 */
	void RF01_13_processResult_with_position_ok() {
		Result result = MockBuilderForTestsJunit.buildProfitResult();

		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade buyBtc = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		
		Position position = MockBuilderForTestsJunit.buildPosition(buyBtc);

		when(positionRepository.getBy(any())).thenReturn(position);

		position = tradeService.processResult(result, bitcoin.getTicker());		
		assertNotNull(position);
	}

}
