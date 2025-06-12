package br.com.itads.empirico.adapters.in.imports.file.trades.strategy.generic;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.adapters.in.console.TradeAdapter;
import br.com.itads.empirico.adapters.in.console.WalletAdapter;
import br.com.itads.empirico.adapters.in.imports.file.dto.LineDTO;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.TradeProcess;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;

public abstract class GenericTradeProcess implements TradeProcess {

	TradeAdapter tradeAdapter;	
	WalletAdapter walletAdapter;

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

		BigDecimal valor = lineDTO.precoOperacao();

		/**
		 * Operação de Cisao
		 */
		if (lineDTO.financeiroOperacao().compareTo(BigDecimal.ZERO) < 0) {
			valor = lineDTO.financeiroOperacao(); 
		}

		return new TradeDTO(
				UUID.randomUUID(),
				lineDTO.descricao(),
				parseData(lineDTO),
				lineDTO.quantidade(),
				valor, 
				lineDTO.papel(),
				lineDTO.papel(),
				AssetClassEnum.ACOES_BR.name()
				);
	}

	private LocalDateTime parseData(LineDTO lineDTO) {
		return LocalDateTime.parse(lineDTO.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}			

}
