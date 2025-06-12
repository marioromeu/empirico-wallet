package br.com.itads.empirico.application.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.adapters.out.repository.file.PositionRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.WalletRepositoryImpl;
import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.service.WalletService;
import br.com.itads.empirico.application.ports.out.repository.PositionRepository;
import br.com.itads.empirico.application.ports.out.repository.WalletRepository;
import br.com.itads.empirico.mock.MockBuilderForTestsJunit;

class WalletServiceTest {

	WalletRepository walletRepository = WalletRepositoryImpl.INSTANCE;
	PositionRepository positionRepository = PositionRepositoryImpl.INSTANCE;
	WalletService walletService;

	public WalletServiceTest() {
		walletService = new WalletService(walletRepository, positionRepository);
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
		assertTrue( wallet.getConsolidatedValue().compareTo(BigDecimal.ZERO) > 0);
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

		Position positionToTest = MockBuilderForTestsJunit.buildPosition(bitcoin.getTicker());
		wallet.updatePosition(positionToTest);
		wallet.consolidate();

		BigDecimal consolidatedValue = wallet.getConsolidatedValue();		
		assertTrue( consolidatedValue.compareTo(BigDecimal.ZERO) > 0);

		positionToTest.addResult( MockBuilderForTestsJunit.buildProfitResult() );
		wallet.updatePosition(positionToTest);
		wallet.consolidate();

		BigDecimal consolidatedValuePlusEarn = wallet.getConsolidatedValue();

		assertTrue( consolidatedValuePlusEarn.compareTo(consolidatedValue) > 0);

	}

}
