package br.com.itads.empirico.adapters.out.repository.file;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.itads.empirico.application.core.domain.Quote;
import br.com.itads.empirico.application.ports.out.repository.QuoteRepository;

public class QuoteRepositoryImpl extends FileRepository<List<Quote>> implements QuoteRepository {

	private List<Quote> internalList = new ArrayList<>();	
	public static final QuoteRepositoryImpl INSTANCE = new QuoteRepositoryImpl();
	private Quote returnedQuote;

	private QuoteRepositoryImpl() {
		super("Quote");
		internalList = Objects.isNull(read()) ? new ArrayList<>() : read();
	}

	@Override
	public Quote saveOrUpdate(Quote quote) {
		internalList.add( quote );
		write(internalList);
		return quote;
	}

	@Override
	public Quote getBy(String ticker) {		
		internalList.stream().forEach( quote -> {
			if ( ticker.equals( quote.getAsset().getTicker() ) ) {
				returnedQuote = quote;
			}
		});
		return returnedQuote;
	}

	public Quote getYesterdayQuoteBy(String ticker) {
		for (Quote quote : internalList) {
			if (quote.getLocalDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()-1) {
				return quote ;
			}
		}
		return null;
	}

	@Override
	public List<Quote> getListOfQuotes(String symbol) {
		return internalList.stream().filter( ticker -> ticker.getAsset().getTicker().equals(symbol)).toList();
	}	
	
}
