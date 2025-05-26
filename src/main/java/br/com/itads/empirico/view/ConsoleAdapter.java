package br.com.itads.empirico.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.adapters.out.repository.file.PositionRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.TradeRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.WalletRepositoryImpl;
import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.core.domain.enums.ResultTypeEnum;
import br.com.itads.empirico.application.core.service.TradeService;
import br.com.itads.empirico.application.core.service.WalletService;

public class ConsoleAdapter {
	
	private static TradeService tradeService = new TradeService(
			PositionRepositoryImpl.INSTANCE,
			TradeRepositoryImpl.INSTANCE
    );
	
	private static WalletService walletService = new WalletService(WalletRepositoryImpl.INSTANCE, PositionRepositoryImpl.INSTANCE);
	
	public static void main(String[] args) {

		UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
		User user = new User("Mário Romeu", "marioromeu");
		
		Wallet wallet = walletService.getWallet(uuid, user);
		
		processTrades( wallet );		
		walletService.saveWallet(wallet);
		
		wallet.consolidate();
		
		wallet.print();

	}

	private static void processTrades(Wallet wallet ) {
		
		Asset btc = new Asset("BTC", "Bitcoin", AssetClassEnum.CRIPTO);
		Asset petr4 = new Asset("PETR4", "Acoes Petrobrás", AssetClassEnum.RENDA_VARIAVEL);
		
		Trade buyBTC1 = buy(btc, new BigDecimal(0.150), new BigDecimal(0.150));
		Position position = tradeService.processTrade(buyBTC1, buyBTC1.getAsset().getTicker());
		wallet.updatePosition(position);
		
		Trade buyBTC2 = buy(btc, new BigDecimal(0.250), new BigDecimal(0.250));
		position = tradeService.processTrade(buyBTC2, buyBTC2.getAsset().getTicker());		
		wallet.updatePosition(position);
		
		Trade sellBTC1 = sell(btc, new BigDecimal(0.100), new BigDecimal(0.50));//50% desconto
		position = tradeService.processTrade(sellBTC1, sellBTC1.getAsset().getTicker());
		wallet.updatePosition(position);
		
		Trade buyPETR4_1 = buy(petr4, new BigDecimal(100), new BigDecimal(20));
		Position positionPert = tradeService.processTrade(buyPETR4_1, buyPETR4_1.getAsset().getTicker());
		wallet.updatePosition(positionPert);
		
		Trade buyPETR4_2 = buy(petr4, new BigDecimal(100), new BigDecimal(15));
		positionPert = tradeService.processTrade(buyPETR4_2, buyPETR4_2.getAsset().getTicker());
		wallet.updatePosition(positionPert);

		Result profitPetr4 = earn(petr4);
		positionPert = tradeService.processResult(profitPetr4, petr4.getTicker());
		wallet.updatePosition(positionPert);
		
	}

	private static Trade sell(Asset btc, BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		return buy(btc, bigDecimal.multiply( new BigDecimal(-1)), bigDecimal2);
	}

	private static Result earn(Asset petr4) {
		return new Result(LocalDateTime.now(), new BigDecimal(8), "Dividendo", ResultTypeEnum.DIVIDENDO);
	}

	private static Trade buy(Asset asset, BigDecimal quantity, BigDecimal price) {
		return new Trade(
				UUID.randomUUID(), 
				"Trade of "+asset.getTicker(), 
				LocalDateTime.now(), 
				quantity,
				price,
				asset);
	}

}
