package br.com.itads.empirico.application.core.service;

import java.util.Objects;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.ports.out.repository.AssetRepository;

public class AssetService {

	private AssetRepository assetRepository;

	public AssetService(AssetRepository assetRepository) {
		this.assetRepository = assetRepository;
	}

	public void saveTrade(Asset asset) {
		assetRepository.saveOrUpdate(asset);
	}

	public Asset getAsset(String ticker) {
		Asset asset = assetRepository.getBy( ticker );
		if ( Objects.isNull(asset)) {
			asset  = new Asset(ticker, ticker, AssetClassEnum.INDEFINIDA);
			saveTrade(asset);
		}
		return asset; 
	}

}
