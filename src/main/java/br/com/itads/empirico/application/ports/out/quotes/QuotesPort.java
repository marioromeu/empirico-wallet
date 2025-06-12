package br.com.itads.empirico.application.ports.out.quotes;

import java.util.List;
import java.util.Optional;

import br.com.itads.empirico.application.core.domain.Quote;

public interface QuotesPort {

	List<Quote> getListOfQuotes(String symbol);
	
	Optional<Quote> getLastQuote(String symbol);
	
	void saveOrUpdate(Quote quote);
	
}
