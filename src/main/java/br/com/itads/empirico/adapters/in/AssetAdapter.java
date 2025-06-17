package br.com.itads.empirico.adapters.in;

import br.com.itads.empirico.adapters.out.http.bovespa.QuoteBovespaAdapter;
import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Quote;
import br.com.itads.empirico.application.core.service.AssetService;
import br.com.itads.empirico.application.ports.in.PortAsset;

public class AssetAdapter implements PortAsset {

	private AssetService assetService;
	
	public AssetAdapter(AssetService assetService) {
		this.assetService = assetService;
	}

	@Override
	public Asset getAsset(String ticker) {
		return assetService.getAsset(ticker);
	}
	
	public void saveOrUpdate(Asset asset) {
		assetService.saveOrUpdate(asset);
	}
	
	public Quote getLastAssetQuote(String ticker) {
		return new QuoteBovespaAdapter().getLastQuote( ticker ).get();
	}

	public Quote getAssetQuotes(Asset asset) {
		return new QuoteBovespaAdapter().requestQuote(asset);
	}

	public void updateAssetQuotes(Asset asset) {
		QuoteBovespaAdapter quoteBovespaAdapter = new QuoteBovespaAdapter();
		Quote quote = quoteBovespaAdapter.requestQuote(asset);
		if (quote != null) {			
			quoteBovespaAdapter.saveOrUpdate(quote);
		}
	}
	
}
