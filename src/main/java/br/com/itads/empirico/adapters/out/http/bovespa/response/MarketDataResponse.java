package br.com.itads.empirico.adapters.out.http.bovespa.response;

import java.util.List;

import com.google.gson.Gson;

public class MarketDataResponse {
	List<StockResult> results; 
	String requestedAt; 
	String took;

	public MarketDataResponse(List<StockResult> results, String requestedAt, String took) {
		super();
		this.results = results;
		this.requestedAt = requestedAt;
		this.took = took;
	}

	public List<StockResult> getResults() {
		return results;
	}

	public void setResults(List<StockResult> results) {
		this.results = results;
	}

	public String getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(String requestedAt) {
		this.requestedAt = requestedAt;
	}

	public String getTook() {
		return took;
	}

	public void setTook(String took) {
		this.took = took;
	}

	public static MarketDataResponse fromJson(String body) {
		return new Gson().fromJson(body, MarketDataResponse.class);
	}

}
