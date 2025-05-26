package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.ports.out.repository.PositionRepository;

public class PositionRepositoryImpl extends FileRepository<Map<String, Position>> implements PositionRepository {

	private Map<String, Position> internalMap;	
	public static final PositionRepositoryImpl INSTANCE = new PositionRepositoryImpl();

	private PositionRepositoryImpl() {
		super("Position");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}

	@Override
	public Position saveOrUpdate(Position position) {
		internalMap.put(position.getAssetTicker(), position);
		write(internalMap);
		return position;
	}

	@Override
	public Position getBy(String ticker) {
		return internalMap.get( ticker );
	}

}
