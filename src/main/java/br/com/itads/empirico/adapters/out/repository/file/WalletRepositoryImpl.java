package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.ports.out.repository.WalletRepository;

public class WalletRepositoryImpl extends FileRepository<Map<UUID, Wallet>> implements WalletRepository {

	private Map<UUID, Wallet> internalMap;	
	public static final WalletRepositoryImpl INSTANCE = new WalletRepositoryImpl();
		
	private WalletRepositoryImpl() {
		super("Wallet");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}
	
	@Override
	public Wallet saveOrUpdate(Wallet wallet) {
		internalMap.put(wallet.getUuid(), wallet);
		internalMap = write(internalMap);
		return wallet;
	}

	@Override
	public Wallet getBy(UUID uuid) {
		return internalMap.get( uuid );
	}

}
