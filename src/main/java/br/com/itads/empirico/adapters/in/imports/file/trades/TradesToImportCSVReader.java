package br.com.itads.empirico.adapters.in.imports.file.trades;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.adapters.in.console.TradeAdapter;
import br.com.itads.empirico.adapters.in.console.WalletAdapter;
import br.com.itads.empirico.adapters.in.imports.file.dto.LineDTO;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.BonificationTradeProcessImpl;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.BuyTradeProcessImpl;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.InterestResultProcessImpl;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.ProfitsResultProcessImpl;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.SeparateTradeProcessImpl;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.TradeProcess;
import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.util.FileUtils;

public class TradesToImportCSVReader {

	String tradesFileName;
	TradeAdapter tradeAdapter;
	WalletAdapter walletAdapter;
	Map<String, TradeProcess> tradeProcessMap;
	
	UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
	User user = new User("Mário Romeu", "marioromeu");

	public TradesToImportCSVReader(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		tradesFileName     = FilePathConfig.getFilePath("tradesToImport");
		this.tradeAdapter  = tradeAdapter;
		this.walletAdapter = walletAdapter;
		
		tradeProcessMap = Map.of(
				"Compra", new BuyTradeProcessImpl(tradeAdapter, walletAdapter),
				"Dividendo", new ProfitsResultProcessImpl(tradeAdapter, walletAdapter),
				"Juros", new InterestResultProcessImpl(tradeAdapter, walletAdapter),
				"Cisao", new SeparateTradeProcessImpl(tradeAdapter, walletAdapter),
				"Bonificacao", new BonificationTradeProcessImpl(tradeAdapter, walletAdapter)
		);		
		
	}
	
	public void processFile() {		
		try {			
			Wallet wallet = walletAdapter.getWallet(uuid, user);
			for (LineDTO lineDTO : FileUtils.lerArquivoCSV( tradesFileName ) ) {
				String categoria = lineDTO.descricao().split(" ")[0];
				processLine(wallet, lineDTO, categoria);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processLine(Wallet wallet, LineDTO lineDTO, String categoria) {		
		TradeProcess tradeProcess = tradeProcessMap.get(categoria);		
		if (tradeProcess != null) {
			tradeProcess.process(wallet, lineDTO, categoria);
		}
	}

	public static void main(String[] args) {
		TradesToImportCSVReader leitor = new TradesToImportCSVReader(
				AdapterBuilder.buildTradeAdapter(),
				AdapterBuilder.buildWalletAdapter()
		);
		leitor.processFile();
	}

}
