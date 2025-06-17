package br.com.itads.empirico.application.core.service;

import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.ports.out.repository.PositionRepository;
import br.com.itads.empirico.application.ports.out.repository.WalletRepository;
import br.com.itads.empirico.view.console.SessionThreadLocal;

public class WalletService {

	WalletRepository walletRepository;
	PositionRepository positionRepository;
	
	public WalletService(WalletRepository repository, PositionRepository positionRepository) {
		this.walletRepository = repository;
		this.positionRepository = positionRepository;
	}

	public void updatePosition(UUID uuid, Position position) {		
		Wallet wallet = walletRepository.getBy(uuid);
		if (Objects.isNull(wallet)) {
			wallet = new Wallet(uuid, SessionThreadLocal.INSTANCE.get());
		}
		wallet.updatePosition(position);
		positionRepository.saveOrUpdate(position);
		walletRepository.saveOrUpdate(wallet);
	}	
	
	/**
	 * Método para consolidar todos os valores das posições e atualizar o valor total da carteira. 
	 *  RF2- Consolidar o resultado global de posição dos ativos (qual o patrimônio do cliente)
	 *  RF3- Consolidar o resultado global de posição dos ativos, corrigido pelo lucro por ativo (qual o patrmonio do cliente + proventos)
	 * 
	 */
	public void consolidateWallet(UUID uuid) {		
		Wallet wallet = walletRepository.getBy(uuid);		
		wallet.consolidate();
		walletRepository.saveOrUpdate(wallet);
	}

	
	/**
	 * RF5 – Apresentação de Dashboard contendo :
	 *	- Ticker do ativo (IVVB11)
	 *	- tamanho da posição (1100)
	 *	- preço médio da posição (32,54 R$)
	 *	- Preço atual do ativo (40,00 R$)
	 */
	public Wallet doDashboard(UUID uuid) {
		/**
		 * TODO converter para um DTO especifico ?
		 * OU devolver o dominio (responsabilidade do service) e o cliente converte (acho que eh isso)?
		 */
		return walletRepository.getBy(uuid); 
	}

	public Wallet getWallet(UUID uuid, User user) {		
		Wallet wallet = walletRepository.getBy(uuid);
		if ( Objects.isNull(wallet)) {
			wallet = new Wallet(uuid, user);
			walletRepository.saveOrUpdate(wallet);
		}
		return wallet;
	}

	public Wallet saveWallet(Wallet wallet) {		
		return walletRepository.saveOrUpdate(wallet);
	}	
	
}
