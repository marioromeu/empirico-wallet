package br.com.itads.empirico.mock;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.domain.Asset;
import br.com.itads.empirico.application.domain.Position;
import br.com.itads.empirico.application.domain.Result;
import br.com.itads.empirico.application.domain.Trade;
import br.com.itads.empirico.application.domain.Wallet;
import br.com.itads.empirico.application.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.domain.enums.ResultTypeEnum;

public class MockBuilderForTestsJunit {

	public static Trade buildBuyTrade(Asset asset) {		
		return new Trade(UUID.randomUUID(), "Compra de Ativo", LocalDateTime.now(), 1d, 10d, asset);		
	}

	public static Asset buildAssetBTC() {
		return new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
	}

	public static Position buildPosition(Trade trade) {
		return new Position(trade);
	}

	public static Result buildProfitResult() {
		return new Result(LocalDateTime.now(), 0.10d, "earning", ResultTypeEnum.RENDIMENTO);		
	}

	public static Wallet buildFullWallet() {
		Wallet wallet = buildWallet();
		Asset bitcoin = buildAssetBTC();

		Trade buyBTC1 = buildBuyTrade(bitcoin);
		Trade buyBTC2 = buildBuyTrade(bitcoin);
		Trade buyBTC3 = buildBuyTrade(bitcoin);
		
		Position position = buildPosition(buyBTC1);
		wallet.updatePosition(position);
		
		position.incAsset(buyBTC2);
		wallet.updatePosition(position);
		
		position.incAsset(buyBTC3);
		wallet.updatePosition(position);

		return wallet;

	}		
		
	public static Wallet buildWallet() {
		return new Wallet();
	}

}
