package br.com.itads.empirico.adapters.out.http.bovespa.response;

public class StockResult {

	String currency; 
	long marketCap;
	String shortName; 
	String longName; 
	double regularMarketChange;
	double regularMarketChangePercent; 
	String regularMarketTime;
	double regularMarketPrice;
	double regularMarketDayHigh;
	String regularMarketDayRange; 
	double regularMarketDayLow;
	long regularMarketVolume; 
	double regularMarketPreviousClose; 
	double regularMarketOpen;
	String fiftyTwoWeekRange; 
	double fiftyTwoWeekLow;
	double fiftyTwoWeekHigh;
	String symbol; 
	String logourl;
	double priceEarnings; 
	double earningsPerShare;
	
	
	
	public StockResult(
			String currency, 
			long marketCap,
			String shortName,
			String longName, 
			double regularMarketChange,
			double regularMarketChangePercent, 
			String regularMarketTime, 
			double regularMarketPrice,
			double regularMarketDayHigh, 
			String regularMarketDayRange, 
			double regularMarketDayLow,
			long regularMarketVolume, 
			double regularMarketPreviousClose, 
			double regularMarketOpen,
			String fiftyTwoWeekRange, 
			double fiftyTwoWeekLow,
			double fiftyTwoWeekHigh, 
			String symbol, 
			String logourl,
			double priceEarnings, 
			double earningsPerShare) {
		
		this.currency = currency;
		this.marketCap = marketCap;
		this.shortName = shortName;
		this.longName = longName;
		this.regularMarketChange = regularMarketChange;
		this.regularMarketChangePercent = regularMarketChangePercent;
		this.regularMarketTime = regularMarketTime;
		this.regularMarketPrice = regularMarketPrice;
		this.regularMarketDayHigh = regularMarketDayHigh;
		this.regularMarketDayRange = regularMarketDayRange;
		this.regularMarketDayLow = regularMarketDayLow;
		this.regularMarketVolume = regularMarketVolume;
		this.regularMarketPreviousClose = regularMarketPreviousClose;
		this.regularMarketOpen = regularMarketOpen;
		this.fiftyTwoWeekRange = fiftyTwoWeekRange;
		this.fiftyTwoWeekLow = fiftyTwoWeekLow;
		this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
		this.symbol = symbol;
		this.logourl = logourl;
		this.priceEarnings = priceEarnings;
		this.earningsPerShare = earningsPerShare;

	}

	
	
	public String getCurrency() {
		return currency;
	}
	public long getMarketCap() {
		return marketCap;
	}
	public String getShortName() {
		return shortName;
	}
	public String getLongName() {
		return longName;
	}
	public double getRegularMarketChange() {
		return regularMarketChange;
	}
	public double getRegularMarketChangePercent() {
		return regularMarketChangePercent;
	}
	public String getRegularMarketTime() {
		return regularMarketTime;
	}
	public double getRegularMarketPrice() {
		return regularMarketPrice;
	}
	public double getRegularMarketDayHigh() {
		return regularMarketDayHigh;
	}
	public String getRegularMarketDayRange() {
		return regularMarketDayRange;
	}
	public double getRegularMarketDayLow() {
		return regularMarketDayLow;
	}
	public long getRegularMarketVolume() {
		return regularMarketVolume;
	}
	public double getRegularMarketPreviousClose() {
		return regularMarketPreviousClose;
	}
	public double getRegularMarketOpen() {
		return regularMarketOpen;
	}
	public String getFiftyTwoWeekRange() {
		return fiftyTwoWeekRange;
	}
	public double getFiftyTwoWeekLow() {
		return fiftyTwoWeekLow;
	}
	public double getFiftyTwoWeekHigh() {
		return fiftyTwoWeekHigh;
	}
	public String getSymbol() {
		return symbol;
	}
	public String getLogourl() {
		return logourl;
	}
	public double getPriceEarnings() {
		return priceEarnings;
	}
	public double getEarningsPerShare() {
		return earningsPerShare;
	}

}