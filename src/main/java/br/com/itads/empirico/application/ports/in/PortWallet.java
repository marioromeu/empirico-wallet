package br.com.itads.empirico.application.ports.in;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardDTO;
import br.com.itads.empirico.adapters.dto.WalletDTO;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.User;

public interface PortWallet {

	public void consolidate(WalletDTO wallet);
	public BigDecimal getTotalValueOf(UUID uuid, User user);
	public Map<String, Position> getTotalValueByAssetClassOf(UUID uuid, User user);	
	public DashboardDTO dashboard();
	
}
