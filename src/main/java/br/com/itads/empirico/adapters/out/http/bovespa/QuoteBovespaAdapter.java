package br.com.itads.empirico.adapters.out.http.bovespa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.tinylog.Logger;

import br.com.itads.empirico.adapters.out.http.bovespa.client.BovespaHttpClient;
import br.com.itads.empirico.adapters.out.repository.file.QuoteRepositoryImpl;
import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Quote;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.ports.out.quotes.QuotesPort;
import br.com.itads.empirico.application.ports.out.repository.QuoteRepository;

public class QuoteBovespaAdapter implements QuotesPort {

	QuoteRepository quotesRepository;
	BovespaHttpClient bovespaClient = new BovespaHttpClient();
	
	public QuoteBovespaAdapter() {
		quotesRepository = QuoteRepositoryImpl.INSTANCE;
	}
	
	@Override
	public List<Quote> getListOfQuotes(String symbol) {		
		return quotesRepository.getListOfQuotes(symbol);
	}

	@Override
	public Optional<Quote> getLastQuote(String symbol) {
		Optional<Quote> optionalQuote = quotesRepository.getListOfQuotes(symbol)
				.stream()
				.filter( quoteParam -> quoteParam.getLocalDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear())
				.max((q1, q2) -> q1.getLocalDateTime().compareTo(q2.getLocalDateTime()));
		 
		if (!optionalQuote.isPresent()) {
			optionalQuote = Optional.of(requestQuote(new Asset(symbol, symbol,AssetClassEnum.INDEFINIDA)));
			saveOrUpdate(optionalQuote.get());
		};
		
		 return optionalQuote;
		 
	}

	@Override
	public void saveOrUpdate(Quote quote) {	
		quotesRepository.saveOrUpdate(quote);		
	}
	
	
	public Quote requestQuote(Asset asset) {
		BigDecimal quote = BigDecimal.ZERO;

		try {
			quote = bovespaClient.requestQuote(asset.getTicker());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.error("Error requesting quote for asset: " + asset.getTicker(), e);
		}

		return new Quote(
				LocalDateTime.now(),
				quote, 
				asset
		);
	}

}
