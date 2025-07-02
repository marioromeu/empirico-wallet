package br.com.itads.empirico.view.importer.trades;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.FileUtils;
import br.com.itads.empirico.view.importer.dto.LineDTO;
import br.com.itads.empirico.view.importer.trades.strategy.TradeProcess;
import br.com.itads.empirico.view.importer.trades.strategy.enums.TradeProcessEnum;
import br.com.itads.empirico.view.importer.trades.strategy.impl.AdjustTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.AmortizationBuyTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.BonificationTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.BuyTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.GroupingTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.IPOTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.IncorporationTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.InterestResultProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.ProfitsResultProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.RestitutionTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.SellTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.SeparateTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.TransferTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.UnfoldingTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.UpdateTradeProcessImpl;
import br.com.itads.empirico.view.importer.trades.strategy.impl.YieldResultProcessImpl;

public class TradesImportService {

	String tradesFilesDirName;
	TradeAdapter tradeAdapter;
	WalletAdapter walletAdapter;
	Map<String, TradeProcess> tradeProcessMap;
	
	UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
	User user = new User("Mário Romeu", "marioromeu");

	public TradesImportService(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		tradesFilesDirName = FilePathConfig.getFilePath("tradesToImportDir");
		this.tradeAdapter  = tradeAdapter;
		this.walletAdapter = walletAdapter;
		
		tradeProcessMap = fillHashMap(tradeAdapter, walletAdapter);

	}

	public void processFile() {

		try {
			Wallet wallet = walletAdapter.getWallet(uuid, user);
			List<LineDTO> tradesList = FileUtils.lerCsvsDoDiretorio( tradesFilesDirName );

			for (LineDTO lineDTO : tradesList) {

				String category = getCategory(lineDTO);

				System.out.println("Processando linha de " + category + " da " + lineDTO.papel() );
				processLine(wallet, lineDTO, category);
				System.out.println("Linha de " + category + " da " + lineDTO.papel() + " OK ! ");
				
				addProfitToUpdate(wallet, lineDTO, category);
				
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

	private Map<String, TradeProcess> fillHashMap(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {

		tradeProcessMap = new HashMap<>();

		tradeProcessMap.put(TradeProcessEnum.COMPRA.name(), 	   new BuyTradeProcessImpl(tradeAdapter, walletAdapter) 				);
		tradeProcessMap.put(TradeProcessEnum.DIVIDENDO.name(), 	   new ProfitsResultProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put(TradeProcessEnum.JUROS.name(), 	 	   new InterestResultProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put(TradeProcessEnum.CISAO.name(), 	 	   new SeparateTradeProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put(TradeProcessEnum.BONIFICACAO.name(),   new BonificationTradeProcessImpl(tradeAdapter, walletAdapter) 		);
		tradeProcessMap.put(TradeProcessEnum.RENDIMENTO.name(),    new YieldResultProcessImpl(tradeAdapter, walletAdapter) 				);
		tradeProcessMap.put(TradeProcessEnum.GRUPAMENTO.name(),    new GroupingTradeProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put(TradeProcessEnum.INCORPORACAO.name(),  new IncorporationTradeProcessImpl(tradeAdapter, walletAdapter) 		);
		tradeProcessMap.put(TradeProcessEnum.ATUALIZACAO.name(),   new UpdateTradeProcessImpl(tradeAdapter, walletAdapter) 				);
		tradeProcessMap.put(TradeProcessEnum.DESDOBRAMENTO.name(), new UnfoldingTradeProcessImpl(tradeAdapter, walletAdapter) 			);
		tradeProcessMap.put(TradeProcessEnum.VENDA.name(),         new SellTradeProcessImpl(tradeAdapter, walletAdapter) 				);
		tradeProcessMap.put(TradeProcessEnum.RESTITUICAO.name(),   new RestitutionTradeProcessImpl(tradeAdapter, walletAdapter)		);
		tradeProcessMap.put(TradeProcessEnum.AJUSTE.name(), 	   new AdjustTradeProcessImpl(tradeAdapter, walletAdapter)				);
		tradeProcessMap.put(TradeProcessEnum.AMORTIZACAO.name(),   new AmortizationBuyTradeProcessImpl(tradeAdapter, walletAdapter)		);
		tradeProcessMap.put(TradeProcessEnum.TRANSFERENCIA.name(), new TransferTradeProcessImpl(tradeAdapter, walletAdapter)			);
		tradeProcessMap.put(TradeProcessEnum.IPO.name(), 		   new IPOTradeProcessImpl(tradeAdapter, walletAdapter)					);
		
		return tradeProcessMap;

	}	
	
	private void addProfitToUpdate(Wallet wallet, LineDTO lineDTO, String category) {
		if (category.equals(TradeProcessEnum.ATUALIZACAO.name()) || category.equals(TradeProcessEnum.AJUSTE.name())) {
			System.out.println("Processando linha de " + category + " da " + lineDTO.papel() );
			processLine(wallet, lineDTO, TradeProcessEnum.RENDIMENTO.name());
			System.out.println("Linha de " + category + " da " + lineDTO.papel() + " OK ! ");
		}
	}

	private String getCategory(LineDTO lineDTO) {
		String category = lineDTO.descricao().split(" ")[0];
		return TradeProcessEnum.clean(category);
	}	
	
}
