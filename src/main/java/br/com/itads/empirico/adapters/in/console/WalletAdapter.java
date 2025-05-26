package br.com.itads.empirico.adapters.in.console;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardDTO;
import br.com.itads.empirico.adapters.dto.WalletDTO;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.service.WalletService;
import br.com.itads.empirico.application.ports.in.PortWallet;

public class WalletAdapter implements PortWallet {

	WalletService walletService;
	
	public WalletAdapter(WalletService walletService) {
		this.walletService = walletService;
	}
	
	@Override
	public void consolidate(WalletDTO wallet) {
		walletService.consolidateWallet(wallet.getUuid());
	}

	@Override
	public BigDecimal getTotalValueOf(UUID uuid, User user) {
		return walletService.getWallet(uuid, user).getConsolidatedValue();
		
	}

	@Override
	public Map<String, Position> getTotalValueByAssetClassOf(UUID uuid, User user) {
		return walletService.getWallet(uuid,  user).getPositionByAssetClass();
		
	}

	@Override
	public DashboardDTO dashboard() {
		return new DashboardDTO();
	}

}
