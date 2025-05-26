package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.ports.out.repository.AssetRepository;

public class AssetRepositoryImpl extends FileRepository<Map<String, Asset>> implements AssetRepository {

	private Map<String, Asset> internalMap;	
	public static final AssetRepositoryImpl INSTANCE = new AssetRepositoryImpl();
		
	private AssetRepositoryImpl() {
		super("Asset");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}
	
	@Override
	public Asset saveOrUpdate(Asset Asset) {
		internalMap.put(Asset.getTicker(), Asset);
		write(internalMap);
		return Asset;
	}

	@Override
	public Asset getBy(String ticker) {
		return internalMap.get( ticker );
	}

}
