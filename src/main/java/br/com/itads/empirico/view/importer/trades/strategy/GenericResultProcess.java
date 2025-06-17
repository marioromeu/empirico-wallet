package br.com.itads.empirico.view.importer.trades.strategy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.ResultTypeEnum;
import br.com.itads.empirico.view.importer.dto.LineDTO;

public abstract class GenericResultProcess implements TradeProcess {

	TradeAdapter tradeAdapter;	
	WalletAdapter walletAdapter;

	protected GenericResultProcess(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		this.tradeAdapter  = tradeAdapter;
		this.walletAdapter = walletAdapter;
	}

	@Override
	public void process(Wallet wallet, LineDTO lineDTO, String categoria) {
		Position position = tradeAdapter.processResult( parseResult(lineDTO, categoria), lineDTO.papel() );			
		walletAdapter.updatePosition(wallet.getUuid(), position);
	}	

	private Result parseResult(LineDTO lineDTO, String categoria) {
		return new Result(
				lineDTO.papel(),
				parseData(lineDTO), 
				lineDTO.proventos(), 
				lineDTO.descricao(), 
				ResultTypeEnum.getResultCategory(categoria)
		);
	}

	private LocalDateTime parseData(LineDTO lineDTO) {
		return LocalDateTime.parse(lineDTO.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}			

}
