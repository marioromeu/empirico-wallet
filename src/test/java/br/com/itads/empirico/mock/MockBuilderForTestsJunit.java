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
		return new Trade("Compra de Ativo", LocalDateTime.now(), 1d, 10d, asset);		
	}

	public static Asset buildAssetBTC() {
		return new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
	}

	public static Position buildPosition() {
		return new Position();		
	}

	public static Result buildProfitResult() {
		return new Result(LocalDateTime.now(), 0.10d, "earning", ResultTypeEnum.RENDIMENTO);		
	}

	public static Wallet buildFullWallet() {
		Wallet wallet = buildWallet();		
		Position position = buildPosition();		
		Asset bitcoin = buildAssetBTC();

		Trade buyBTC1 = buildBuyTrade(bitcoin);
		Trade buyBTC2 = buildBuyTrade(bitcoin);
		Trade buyBTC3 = buildBuyTrade(bitcoin);
		
		position.incAsset(UUID.randomUUID(), buyBTC1);
		wallet.updatePosition(position);
		
		position.incAsset(UUID.randomUUID(), buyBTC2);
		wallet.updatePosition(position);
		
		position.incAsset(UUID.randomUUID(), buyBTC3);
		wallet.updatePosition(position);

		return wallet;

	}		
		
	public static Wallet buildWallet() {
		return new Wallet();
	}

}
