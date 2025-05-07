package br.com.itads.empirico.application.domain;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.domain.enums.AssetClassEnum;

class WalletTest {

	@Test
	void consolidateWalletTest() {
		
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		UUID uuid3 = UUID.randomUUID();

		Wallet wallet = new Wallet();

		Asset bitcoin = new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);

		Trade buyBTC = new Trade(uuid1, "COMPRA", LocalDateTime.now(),  1d,  100d, bitcoin);
		Position position = new Position(buyBTC);
		wallet.updatePosition(position);		
		
		Trade buyBTC2 = new Trade(uuid2, "COMPRA", LocalDateTime.now(), 1d, 100d, bitcoin);
		position.incAsset(buyBTC2);
		wallet.updatePosition(position);
		
		Trade buyBTC3 = new Trade(uuid3, "COMPRA", LocalDateTime.now(), 1d,  90d, bitcoin);
		position.incAsset(buyBTC3);
		wallet.updatePosition(position);
		
		wallet.consolidate();
		
		System.out.println("total carteira -> " + wallet.getConsolidatedValue());
		
		assertTrue(wallet.getConsolidatedValue() > 0d);
	}
	
}
