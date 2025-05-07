package br.com.itads.empirico.application.service;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.domain.Asset;
import br.com.itads.empirico.application.domain.Position;
import br.com.itads.empirico.application.domain.Trade;
import br.com.itads.empirico.application.domain.Wallet;
import br.com.itads.empirico.application.repository.WalletRepository;
import br.com.itads.empirico.mock.MockBuilderForTestsJunit;
import br.com.itads.empirico.repository.WalletRepositoryImpl;

public class WalletServiceTest {

	//WalletRepository repository = mock(WalletRepository.class);
	WalletRepository repository = WalletRepositoryImpl.INSTANCE;
	WalletService walletService;

	public WalletServiceTest() {
		walletService = new WalletService(repository);
	}

	/**
	 * Método para consolidar todos os valores das posições e atualizar o valor total da carteira. 
	 *  RF2- Consolidar o resultado global de posição dos ativos (qual o patrimônio do cliente)
	 * 
	 */
	@Test
	void RF02_consolidateWallet() {
		Wallet wallet = MockBuilderForTestsJunit.buildFullWallet();
		wallet.consolidate();		
		assertTrue( wallet.getConsolidatedValue() > 0);
	}

	/**
	 * Método para consolidar todos os valores das posições e atualizar o valor total da carteira. 
	 *  RF3- Consolidar o resultado global de posição dos ativos, corrigido pelo lucro por ativo (qual o patrmonio do cliente + proventos)
	 * 
	 */
	@Test
	void RF03_consolidateWallet() {		
		Wallet wallet = MockBuilderForTestsJunit.buildFullWallet();

		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade buyBTC = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);

		Position positionToTest = MockBuilderForTestsJunit.buildPosition(buyBTC);
		wallet.updatePosition(positionToTest);
		wallet.consolidate();

		Double consolidatedValue = wallet.getConsolidatedValue();		
		assertTrue( consolidatedValue > 0);

		positionToTest.addResult("BTC", MockBuilderForTestsJunit.buildProfitResult());
		wallet.updatePosition(positionToTest);
		wallet.consolidate();

		Double consolidatedValuePlusEarn = wallet.getConsolidatedValue();

		assertTrue( consolidatedValuePlusEarn > consolidatedValue);

	}

}
