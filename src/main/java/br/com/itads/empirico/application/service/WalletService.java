package br.com.itads.empirico.application.service;

import java.util.UUID;

import br.com.itads.empirico.application.domain.Wallet;
import br.com.itads.empirico.application.repository.WalletRepository;

public class WalletService {

	WalletRepository repository;
	
	public WalletService(WalletRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Método para consolidar todos os valores das posições e atualizar o valor total da carteira. 
	 *  RF2- Consolidar o resultado global de posição dos ativos (qual o patrimônio do cliente)
	 *  RF3- Consolidar o resultado global de posição dos ativos, corrigido pelo lucro por ativo (qual o patrmonio do cliente + proventos)
	 * 
	 */
	public void consolidateWallet(UUID uuid) {		
		Wallet wallet = repository.getBy(uuid);		
		wallet.consolidate();		
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
		return repository.getBy(uuid); 
	}
	
}
