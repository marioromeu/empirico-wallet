package br.com.itads.empirico.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.application.domain.Wallet;
import br.com.itads.empirico.application.repository.WalletRepository;

public class WalletRepositoryImpl implements WalletRepository {

	private Map<UUID, Wallet> internalMap = new HashMap<>();
	
	public static final WalletRepositoryImpl INSTANCE = new WalletRepositoryImpl();
		
	private WalletRepositoryImpl() {}
	
	@Override
	public Wallet saveOrUpdate(Wallet wallet) {
		return internalMap.put(wallet.getUuid(), wallet);
		
	}

	@Override
	public Wallet getBy(UUID uuid) {
		return internalMap.get( uuid );
	}

}
