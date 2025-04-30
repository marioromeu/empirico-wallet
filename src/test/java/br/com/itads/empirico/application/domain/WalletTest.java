package br.com.itads.empirico.application.domain;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.domain.enums.AssetClassEnum;

class WalletTest {

	@Test
	void consolidateWalletTest() {
		Wallet wallet = new Wallet();
		Position position = new Position();
		
		Asset bitcoin = new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
		
		Trade buyBTC = new Trade("COMPRA", LocalDateTime.now(),  1d,  100d, bitcoin);
		position.incAsset(UUID.randomUUID(), buyBTC);
		wallet.updatePosition(position);		
		
		Trade buyBTC2 = new Trade("COMPRA", LocalDateTime.now(), 1d, 100d, bitcoin);
		position.incAsset(UUID.randomUUID(), buyBTC2);
		wallet.updatePosition(position);
		
		Trade buyBTC3 = new Trade("COMPRA", LocalDateTime.now(), 1d,  90d, bitcoin);
		position.incAsset(UUID.randomUUID(), buyBTC3);
		wallet.updatePosition(position);
		
		wallet.consolidate();
		
		System.out.println("total carteira -> " + wallet.getConsolidatedValue());
		
		assertTrue(wallet.getConsolidatedValue() > 0d);
	}
	
}
