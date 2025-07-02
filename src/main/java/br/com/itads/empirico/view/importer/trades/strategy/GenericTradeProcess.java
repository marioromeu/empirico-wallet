package br.com.itads.empirico.view.importer.trades.strategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.view.importer.dto.LineDTO;

public abstract class GenericTradeProcess implements TradeProcess {

	protected TradeAdapter tradeAdapter;	
	protected WalletAdapter walletAdapter;
	
	protected GenericTradeProcess(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		this.tradeAdapter  = tradeAdapter;
		this.walletAdapter = walletAdapter;
	}

	@Override
	public void process(Wallet wallet, LineDTO lineDTO, String categoria) {
		Position position = tradeAdapter.processTrade( parseTade(lineDTO) );
		walletAdapter.updatePosition(wallet.getUuid(), position);		
	}

	private TradeDTO parseTade(LineDTO lineDTO) {

		return new TradeDTO(
				UUID.randomUUID(),
				lineDTO.descricao(),
				parseData(lineDTO),
				lineDTO.quantidade(),
				lineDTO.precoOperacao(), 
				lineDTO.papel(),
				lineDTO.papel(),
				AssetClassEnum.ACOES_BR.name()
				);
	}

	protected LocalDateTime parseData(LineDTO lineDTO) {
		return LocalDateTime.parse(lineDTO.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}			

}
