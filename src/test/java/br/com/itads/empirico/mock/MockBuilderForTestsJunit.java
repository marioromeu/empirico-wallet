package br.com.itads.empirico.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.core.domain.enums.ResultTypeEnum;

public class MockBuilderForTestsJunit {

	public static Trade buildBuyTrade(Asset asset) {		
		return new Trade(UUID.randomUUID(), "Compra de Ativo", LocalDateTime.now(), new BigDecimal(1), new BigDecimal(10), asset);		
	}

	public static Asset buildAssetBTC() {
		return new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
	}

	public static Position buildPosition(String ticker) {
		return new Position(ticker);
	}

	public static Result buildProfitResult() {
		return new Result("BTC", LocalDateTime.now(), new BigDecimal(0.10), "earning", ResultTypeEnum.RENDIMENTO);
	}

	public static Wallet buildFullWallet() {
		Wallet wallet = buildWallet();
		Asset bitcoin = buildAssetBTC();

		Trade buyBTC2 = buildBuyTrade(bitcoin);
		Trade buyBTC3 = buildBuyTrade(bitcoin);
		
		Position position = buildPosition(bitcoin.getTicker());
		wallet.updatePosition(position);
		
		position.incAsset(buyBTC2);
		wallet.updatePosition(position);
		
		position.incAsset(buyBTC3);
		wallet.updatePosition(position);

		return wallet;

	}		
		
	public static Wallet buildWallet() {
		return new Wallet(UUID.randomUUID(), buildUser());
	}

	private static User buildUser() {
		return new User("Mário Romeu", "marioromeu");
	}

	public static Trade buildSellTrade(Asset bitcoin) {
		return new Trade(UUID.randomUUID(), "Venda de Ativo", LocalDateTime.now(), new BigDecimal(-0.1), new BigDecimal(1), bitcoin);
	}

}
