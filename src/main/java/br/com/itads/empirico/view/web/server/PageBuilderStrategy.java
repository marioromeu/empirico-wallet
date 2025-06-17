package br.com.itads.empirico.view.web.server;

import java.util.HashMap;
import java.util.Map;

import br.com.itads.empirico.view.web.server.strategy.HtmlPage;
import br.com.itads.empirico.view.web.server.strategy.impl.ConsolidateHtmlPage;
import br.com.itads.empirico.view.web.server.strategy.impl.DashboardHtmlPage;
import br.com.itads.empirico.view.web.server.strategy.impl.HomeHtmlPage;
import br.com.itads.empirico.view.web.server.strategy.impl.IndexHtmlPage;

public class PageBuilderStrategy {

	public static PageBuilderStrategy INSTANCE = new PageBuilderStrategy();
	
	private Map<String, HtmlPage> strategy = new HashMap<>();
	
	private PageBuilderStrategy() {
		strategy.put("dashboard", new DashboardHtmlPage());
		strategy.put("home", new HomeHtmlPage());
		strategy.put("index", new IndexHtmlPage());
		strategy.put("consolidate", new ConsolidateHtmlPage());
	}

	public Map<String, HtmlPage> getStrategies() {
		return strategy;
	}
	
	public HtmlPage getStrategyBy(String key) {
		return strategy.get(key);
	}

}
