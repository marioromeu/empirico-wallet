package br.com.itads.empirico.application.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;

class PositionTest {

	@Test
	void consolidateTest() {
	
		Asset bitcoin = new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
		
		BigDecimal tradeValue = new BigDecimal(100);
		
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		
		Trade buyBTC = new Trade(uuid1, "COMPRA", LocalDateTime.now(),  new BigDecimal(1),  new BigDecimal(100), bitcoin);
		Position position = new Position(buyBTC.getAsset().getTicker());
		position.incAsset(buyBTC);
		
		tradeValue = tradeValue.add( new BigDecimal(150));
		
		Trade buyBTC2 = new Trade(uuid2, "COMPRA", LocalDateTime.now(),  new BigDecimal(1),  new BigDecimal(150), bitcoin);
		position.incAsset(buyBTC2);
		
		position.consolidate();

		System.out.println("preço medio da posicao -> " + position.getPositionAveragePrice() );
		System.out.println("total da posicao -> " + position.getPositionTotalPrice() );
		
		assertEquals(tradeValue, position.getPositionTotalPrice() );

	}
	
}
