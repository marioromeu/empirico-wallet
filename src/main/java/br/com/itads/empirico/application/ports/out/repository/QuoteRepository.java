package br.com.itads.empirico.application.ports.out.repository;

import java.util.List;

import br.com.itads.empirico.application.core.domain.Quote;

public interface QuoteRepository extends CRUDRepository<Quote, String>{
	
	List<Quote> getListOfQuotes(String symbol);
	
}
