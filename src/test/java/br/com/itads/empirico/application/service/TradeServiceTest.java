package br.com.itads.empirico.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.service.TradeService;
import br.com.itads.empirico.application.ports.out.repository.PositionRepository;
import br.com.itads.empirico.application.ports.out.repository.TradeRepository;
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
		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(bitcoin.getTicker()));		
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
		position.incAsset(trade);
		position.consolidate();
		assertTrue(position.getPositionTotalPrice().compareTo(BigDecimal.ZERO) > 0);		
	}	

	@Test
	void RF01_11_E_12_processTrade_with_position_ok_two_or_more_buy() {
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade trade1 = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		Trade trade2 = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		Trade trade3 = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);

		BigDecimal sumOfPositions = BigDecimal.ZERO;
		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(bitcoin.getTicker()));		
		Position position = tradeService.processTrade(trade1, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add( trade1.getTotalTradeValue() );
		assertNotNull(position);

		when(positionRepository.getBy(any())).thenReturn( position );
		position = tradeService.processTrade(trade2, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add( trade2.getTotalTradeValue() );
		assertNotNull(position);
		
		when(positionRepository.getBy(any())).thenReturn( position );
		position = tradeService.processTrade(trade3, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add( trade3.getTotalTradeValue() );
		assertNotNull(position);

		assertEquals(position.getPositionTotalPrice(), sumOfPositions);

		assertTrue(
				position.getPositionAveragePrice().equals( 
						sumOfPositions.divide(new BigDecimal(3)) 
				)
		);

	}
	
	
	/**
 	 * RF01 – Cadastrar operações sobre um ativo :
	 *	1.3-LUCRO
	 * @param result
	 * @param uuid
	 */
	@Test
	void RF01_13_processResult_with_position_ok() {
		Result result = MockBuilderForTestsJunit.buildProfitResult();

		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade buyBtc = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		
		Position position = MockBuilderForTestsJunit.buildPosition(bitcoin.getTicker());
		position.incAsset(buyBtc);

		when(positionRepository.getBy(any())).thenReturn(position);

		position = tradeService.processResult(result, bitcoin.getTicker());		
		position.consolidate();
		
		BigDecimal totalPlusResult = buyBtc.getTotalTradeValue().add( result.price() );
		
		assertEquals(totalPlusResult, position.getPositionTotalPriceWithResults());
		
	}

	/**
	 * 2 Buy and 2 Sell of only one asset.
	 */
	@Test
	void RF01_11_and_12_and_13_processTrade_with_position_ok_two_or_more_buy_and_sell() {
		
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		
		Trade buyTrade1   = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		Trade sellTrade2  = MockBuilderForTestsJunit.buildSellTrade(bitcoin);
		Trade buyTrade3  = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		Trade sellTrade4 = MockBuilderForTestsJunit.buildSellTrade(bitcoin);

		Result result = MockBuilderForTestsJunit.buildProfitResult();
		
		BigDecimal sumOfPositions = BigDecimal.ZERO;
		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition(bitcoin.getTicker()));		
		Position position = tradeService.processTrade(buyTrade1, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add( buyTrade1.getTotalTradeValue());
		assertNotNull(position);

		when(positionRepository.getBy(any())).thenReturn( position );
		position = tradeService.processTrade(sellTrade2, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add(sellTrade2.getTotalTradeValue());
		assertNotNull(position);
		
		when(positionRepository.getBy(any())).thenReturn( position );
		position = tradeService.processTrade(buyTrade3, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add(buyTrade3.getTotalTradeValue());
		assertNotNull(position);

		when(positionRepository.getBy(any())).thenReturn( position );
		position = tradeService.processTrade(sellTrade4, bitcoin.getTicker());
		position.consolidate();
		sumOfPositions = sumOfPositions.add( sellTrade4.getTotalTradeValue() );
		assertNotNull(position);		

		assertEquals(position.getPositionTotalPrice(), sumOfPositions);
		
		position = tradeService.processResult(result, bitcoin.getTicker());		
		position.consolidate();
		sumOfPositions = sumOfPositions.add(result.price());
		
		assertEquals(position.getPositionTotalPriceWithResults(), sumOfPositions);

	}
	
}
