package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.ports.out.repository.ResultRepository;

public class ResultRepositoryImpl extends FileRepository<Map<String, Result>> implements ResultRepository {

	private Map<String, Result> internalMap;	
	public static final ResultRepositoryImpl INSTANCE = new ResultRepositoryImpl();

	private ResultRepositoryImpl() {
		super("Result");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}

	@Override
	public Result saveOrUpdate(Result result) {
		internalMap.put(result.assetTicker(), result);
		write(internalMap);
		return result;
	}

	@Override
	public Result getBy(String assetTicker) {
		return internalMap.get( assetTicker );
	}

}
