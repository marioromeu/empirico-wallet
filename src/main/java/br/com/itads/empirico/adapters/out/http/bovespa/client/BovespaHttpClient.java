package br.com.itads.empirico.adapters.out.http.bovespa.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;

import br.com.itads.empirico.adapters.out.http.bovespa.response.MarketDataResponse;
import br.com.itads.empirico.adapters.out.http.bovespa.response.StockResult;
import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;

public class BovespaHttpClient {

	private HttpClient httpClient;
	private String token;
	private BigDecimal lastQuote;

	public BovespaHttpClient() {
		httpClient = HttpClient.newHttpClient();
		token = FilePathConfig.getFilePath("brapi-token");
		lastQuote = BigDecimal.ZERO;
	}

	public BigDecimal requestQuote(String ticker) {
		String url = "https://brapi.dev/api/quote/" + ticker;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.header("Authorization", token)
				.build();
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			MarketDataResponse marketDataResponse = 
					MarketDataResponse.fromJson(response.body());

			return getLastQuoteFrom(marketDataResponse);

		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Failed to request quote", e);
		}
	}

	private BigDecimal getLastQuoteFrom(MarketDataResponse marketDataResponse) {		
		marketDataResponse.getResults()
						.stream()
						.sorted(Comparator.comparing( StockResult::getRegularMarketTime).reversed())
						.findFirst()
						.ifPresentOrElse(
								result -> {
									lastQuote = new BigDecimal(String.valueOf(result.getRegularMarketPrice()));
								}, 
								() -> {
									throw new RuntimeException("No market data found for the ticker");
								});		
		return lastQuote;		
	}
}
