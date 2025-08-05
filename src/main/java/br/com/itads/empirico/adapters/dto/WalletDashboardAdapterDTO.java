package br.com.itads.empirico.adapters.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record WalletDashboardAdapterDTO(UUID walletUuid, List<DashboardAdapterDTO> dto) implements Serializable {
}