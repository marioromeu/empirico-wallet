package br.com.itads.empirico;

import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.view.importer.trades.TradesImportService;

public class MainTradeImport {

	public static void main(String[] args) {
		TradesImportService leitor = new TradesImportService(
				AdapterBuilder.buildTradeAdapter(),
				AdapterBuilder.buildWalletAdapter()
		);
		leitor.processFile();
		System.out.println("Importação finalizada com sucesso!");
	}
	
}
