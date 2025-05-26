package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.ports.out.repository.TradeRepository;

public class TradeRepositoryImpl extends FileRepository<Map<String, Trade>> implements TradeRepository {

	private Map<String, Trade> internalMap;	
	public static final TradeRepositoryImpl INSTANCE = new TradeRepositoryImpl();

	private TradeRepositoryImpl() {
		super("Trade");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}

	@Override
	public Trade saveOrUpdate(Trade trade) {
		internalMap.put(trade.getAsset().getTicker(), trade);
		write(internalMap);
		return trade;
	}

	@Override
	public Trade getBy(UUID uuid) {
		return internalMap.get( uuid );
	}

}
