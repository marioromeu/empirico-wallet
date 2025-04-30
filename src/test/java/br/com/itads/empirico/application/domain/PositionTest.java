package br.com.itads.empirico.application.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.domain.enums.AssetClassEnum;

class PositionTest {

	@Test
	void consolidateTest() {
		Position position = new Position();
		
		Asset bitcoin = new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
		
		Double tradeValue = 100d;
		
		Trade buyBTC = new Trade("COMPRA", LocalDateTime.now(),  1d,  100d, bitcoin);
		position.incAsset(UUID.randomUUID(), buyBTC);

		tradeValue += 150;
		
		Trade buyBTC2 = new Trade("COMPRA", LocalDateTime.now(),  1d,  150d, bitcoin);
		position.incAsset(UUID.randomUUID(), buyBTC2);
		
		
		position.consolidate();

		System.out.println("preço medio da posicao -> " + position.getPositionAveragePrice() );
		System.out.println("total da posicao -> " + position.getPositionTotalPrice() );
		
		assertEquals(tradeValue, position.getPositionTotalPrice() );

	}
	
}
