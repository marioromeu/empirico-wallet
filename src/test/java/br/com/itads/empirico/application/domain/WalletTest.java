package br.com.itads.empirico.application.domain;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;

class WalletTest {

	@Test
	void consolidateWalletTest() {
		
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		UUID uuid3 = UUID.randomUUID();

		User user = new User("Mário Romeu", "marioromeu");
		
		Wallet wallet = new Wallet(UUID.randomUUID(), user);

		Asset bitcoin = new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);

		Trade buyBTC = new Trade(uuid1, "COMPRA", LocalDateTime.now(),  new BigDecimal(1),  new BigDecimal(100), bitcoin);
		Position position = new Position(buyBTC.getAsset().getTicker());
		wallet.updatePosition(position);		
		
		Trade buyBTC2 = new Trade(uuid2, "COMPRA", LocalDateTime.now(), new BigDecimal(1),  new BigDecimal(100), bitcoin);
		position.incAsset(buyBTC2);
		wallet.updatePosition(position);
		
		Trade buyBTC3 = new Trade(uuid3, "COMPRA", LocalDateTime.now(), new BigDecimal(1),  new BigDecimal(90), bitcoin);
		position.incAsset(buyBTC3);
		wallet.updatePosition(position);
		
		wallet.consolidate();
		
		System.out.println("total carteira -> " + wallet.getConsolidatedValue());
		
		assertTrue(wallet.getConsolidatedValue().compareTo(BigDecimal.ZERO) > 0d);
	}
	
}
