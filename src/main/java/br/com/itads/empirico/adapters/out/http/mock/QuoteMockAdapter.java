package br.com.itads.empirico.adapters.out.http.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Quote;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.ports.out.quotes.QuotesPort;

public class QuoteMockAdapter implements QuotesPort {

	Map<String, List<Quote>> map;

	@Override
	public List<Quote> getListOfQuotes(String symbol) {
		List<Quote> quotes = map.get(symbol);
		if (quotes == null) {
			quotes = new ArrayList<>();
			quotes.add(buildMockAsset(symbol));
		} else {
			if (quotes.stream().anyMatch(k -> k.getAsset().getTicker().equals(symbol))) {
				quotes = new ArrayList<>(quotes);
			} else {
				quotes.add(buildMockAsset(symbol));
			}
		}
		return quotes;
	}

	@Override
	public Optional<Quote> getLastQuote(String symbol) {
		return map.get(symbol).stream()
				.max((q1, q2) -> q1.getLocalDateTime().compareTo(q2.getLocalDateTime()));
	}

	@Override
	public void saveOrUpdate(Quote quote) {		
		map.get(quote.getAsset().getTicker()).add(quote);	
	}

	private Quote buildMockAsset(String symbol) {
		return new Quote(
			LocalDateTime.now(),
			BigDecimal.valueOf(10d),
			new Asset(symbol, symbol, AssetClassEnum.INDEFINIDA)
		);
	}

}
