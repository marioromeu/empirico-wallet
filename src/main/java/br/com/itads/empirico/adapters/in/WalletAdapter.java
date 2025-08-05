package br.com.itads.empirico.adapters.in;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.WalletDTO;
import br.com.itads.empirico.adapters.dto.WalletDashboardAdapterDTO;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.application.core.service.WalletService;
import br.com.itads.empirico.application.ports.in.PortWallet;
import br.com.itads.empirico.view.console.SessionThreadLocal;

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

	public void updatePosition(UUID uuid, Position position) {
		walletService.updatePosition(uuid, position);		
	}

	public void consolidateWallet(UUID uuid) {
		Wallet wallet = getWallet(uuid, SessionThreadLocal.INSTANCE.get());
		if (Objects.nonNull(wallet)) {
			WalletDTO dto = new WalletDTO(uuid, SessionThreadLocal.INSTANCE.get());
			consolidate(dto);
		}
	}

	public Wallet getWallet(UUID uuid, User user) {
		return walletService.getWallet(uuid, user);
	}

	public Wallet doDashboard(UUID uuid) {
		return walletService.doDashboard(uuid);
	}

	public void dump(WalletDashboardAdapterDTO walletDashboardAdapterDTO) {
		walletService.dump(walletDashboardAdapterDTO);		
	}

}
