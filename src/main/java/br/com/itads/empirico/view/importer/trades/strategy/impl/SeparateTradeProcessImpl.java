package br.com.itads.empirico.view.importer.trades.strategy.impl;

import java.util.UUID;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.view.importer.dto.LineDTO;
import br.com.itads.empirico.view.importer.trades.strategy.GenericTradeProcess;

public class SeparateTradeProcessImpl extends GenericTradeProcess {

	public SeparateTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
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
				lineDTO.financeiroOperacao(), 
				lineDTO.papel(),
				lineDTO.papel(),
				AssetClassEnum.ACOES_BR.name()
				);
	}
	
}
