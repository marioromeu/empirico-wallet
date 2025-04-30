package br.com.itads.empirico.application.repository;

import java.util.UUID;

import br.com.itads.empirico.application.domain.Wallet;

public interface WalletRepository extends CRUDRepository<Wallet, UUID> {
	
}
