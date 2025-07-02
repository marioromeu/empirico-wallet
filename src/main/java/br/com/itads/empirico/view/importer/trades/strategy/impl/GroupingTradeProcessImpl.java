package br.com.itads.empirico.view.importer.trades.strategy.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.view.importer.dto.LineDTO;
import br.com.itads.empirico.view.importer.trades.strategy.GenericTradeProcess;

public class GroupingTradeProcessImpl extends GenericTradeProcess {

	public GroupingTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
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

				//calcPrice(lineDTO), 		        //price
				lineDTO.financeiroOperacao(),       //price
				
				lineDTO.papel(),		            //assetTicker
				lineDTO.papel(),		            //assetDescription
				AssetClassEnum.ACOES_BR.name()		//assetClass
				);
	}

	private BigDecimal calcPrice(LineDTO lineDTO) {

		if ((lineDTO.quantidade().compareTo(BigDecimal.ZERO) == 0)
				&&
				(lineDTO.financeiroOperacao().compareTo(BigDecimal.ZERO) == 0) ){
			return BigDecimal.ZERO;
		}
		
		BigDecimal quantity = lineDTO.quantidade().compareTo(new BigDecimal("0")) >= 0 ? lineDTO.quantidade() : lineDTO.quantidade().multiply(new BigDecimal("-1"));
		return lineDTO.saldoOperacao().divide(quantity, RoundingMode.UP);
	}	
	
}
