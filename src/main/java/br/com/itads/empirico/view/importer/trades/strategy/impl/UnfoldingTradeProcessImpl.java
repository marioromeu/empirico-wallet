package br.com.itads.empirico.view.importer.trades.strategy.impl;

import java.util.UUID;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.view.importer.dto.LineDTO;

public class UnfoldingTradeProcessImpl extends GroupingTradeProcessImpl {

	public UnfoldingTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}
	
	@Override
	public void process(Wallet wallet, LineDTO lineDTO, String categoria) {
		Position position = tradeAdapter.processTrade( parseTade(lineDTO) );
		walletAdapter.updatePosition(wallet.getUuid(), position);		
	}
 
	private TradeDTO parseTade(LineDTO lineDTO) {
		return new TradeDTO(
				UUID.randomUUID(), 				    //UUID
				lineDTO.descricao(),			    //description
				parseData(lineDTO),			        //datetime	
				lineDTO.quantidade(),		        //quantity
				lineDTO.precoOperacao(), 	        //price
				lineDTO.papel(),		            //assetTicker
				lineDTO.papel(),		            //assetDescription
				AssetClassEnum.ACOES_BR.name()		//assetClass
				);
	}	
	
}
