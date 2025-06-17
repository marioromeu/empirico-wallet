package br.com.itads.empirico.view.importer.trades;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.util.FileUtils;
import br.com.itads.empirico.view.importer.dto.LineDTO;
import br.com.itads.empirico.view.importer.trades.strategy.TradeProcess;
import br.com.itads.empirico.view.importer.trades.strategy.impl.AdjustTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.AmortizationBuyTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.BonificationTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.BuyTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.GroupingTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.IPOTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.IncorporationTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.InterestResultProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.ProfitsResultProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.RestitutionBuyTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.SellTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.SeparateTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.TransferTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.UnfoldingTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.UpdateTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.YieldResultProcessImpl;

public class TradesToImportCSVReader {

	String tradesFilesDirName;
	TradeAdapter tradeAdapter;
	WalletAdapter walletAdapter;
	Map<String, TradeProcess> tradeProcessMap;
	
	UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
	User user = new User("Mário Romeu", "marioromeu");

	public TradesToImportCSVReader(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		tradesFilesDirName = FilePathConfig.getFilePath("tradesToImportDir");
		this.tradeAdapter  = tradeAdapter;
		this.walletAdapter = walletAdapter;
		
		tradeProcessMap = fillHashMap(tradeAdapter, walletAdapter);

	}

	public void processFile() {		
		try {			
			Wallet wallet = walletAdapter.getWallet(uuid, user);
			for (LineDTO lineDTO : FileUtils.lerCsvsDoDiretorio( tradesFilesDirName ) ) {
				String category = lineDTO.descricao().split(" ")[0];
				processLine(wallet, lineDTO, category);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processLine(Wallet wallet, LineDTO lineDTO, String category) {		
		TradeProcess tradeProcess = tradeProcessMap.get(category);		
		if (tradeProcess != null) {
			tradeProcess.process(wallet, lineDTO, category);
		} else {
			System.out.println("FALTA IMPLEMENTAR ["+category+"]");
		}
	}

	public static void main(String[] args) {
		TradesToImportCSVReader leitor = new TradesToImportCSVReader(
				AdapterBuilder.buildTradeAdapter(),
				AdapterBuilder.buildWalletAdapter()
		);
		leitor.processFile();
	}

	private Map<String, TradeProcess> fillHashMap(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		tradeProcessMap = new HashMap<>();
		tradeProcessMap.put("Compra", 	 	 new BuyTradeProcessImpl(tradeAdapter, walletAdapter) 				);
		tradeProcessMap.put("Dividendo", 	 new ProfitsResultProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put("Juros", 	 	 new InterestResultProcessImpl(tradeAdapter, walletAdapter) 		);
		tradeProcessMap.put("Cisão", 	 	 new SeparateTradeProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put("Bonificação",   new BonificationTradeProcessImpl(tradeAdapter, walletAdapter) 		);
		tradeProcessMap.put("Rendimento", 	 new YieldResultProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put("Grupamento", 	 new GroupingTradeProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put("Incorporação",  new IncorporationTradeProcessImpl(tradeAdapter, walletAdapter) 	);
		tradeProcessMap.put("Atualização", 	 new UpdateTradeProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put("Desdobramento", new UnfoldingTradeProcessImpl(tradeAdapter, walletAdapter) 		);

		tradeProcessMap.put("Venda",         new SellTradeProcessImpl(tradeAdapter, walletAdapter) 				);
		tradeProcessMap.put("Restituição", 	 new RestitutionBuyTradeProcessImpl(tradeAdapter, walletAdapter)	);
		tradeProcessMap.put("Ajuste", 		 new AdjustTradeProcessImpl(tradeAdapter, walletAdapter)			);
		
		tradeProcessMap.put("Amortizacao",   new AmortizationBuyTradeProcessImpl(tradeAdapter, walletAdapter)	);
		tradeProcessMap.put("Amortização",   new AmortizationBuyTradeProcessImpl(tradeAdapter, walletAdapter)	);
		
		tradeProcessMap.put("Transferência", new TransferTradeProcessImpl(tradeAdapter, walletAdapter)			);
		tradeProcessMap.put("IPO", 			 new IPOTradeProcessImpl(tradeAdapter, walletAdapter)				);
		
		return tradeProcessMap;
	}	
	
}
