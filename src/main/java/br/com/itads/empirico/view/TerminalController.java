package br.com.itads.empirico.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.util.ServiceBuilder;

public class TerminalController {

    private boolean running = true;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            handleChoice(choice);
        }

        scanner.close();
    }

    private void showMenu() {
    	System.out.println();
    	System.out.println();
    	System.out.println();
        System.out.println("Menu:");
        System.out.println("1 - Cadastrar Trade");
        System.out.println("2 - Consolidar Carteira");
        System.out.println("3 - Consultar valor total da carteira");
        System.out.println("4 - Consultar posição consolidada por ativo");
        System.out.println("5 - Dashboard");
        System.out.println("0 - Sair");
        System.out.println();        
        System.out.print("Escolha uma opção: ");
    }

    private void handleChoice(int choice) {
    	
    	Scanner scanner = new Scanner(System.in);

		UUID uuid = UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
		User user = new User("Mário Romeu", "marioromeu");

        switch (choice) {

            case 1:
            	
            	//TODO problema de skip das demais opcoes
            	//uuid = readWalletUUID(scanner, uuid);
            	
            	System.out.println("Digite os dados do Trade :");
            	System.out.println("Descrição [Ação Petrobrás] : ");
            	String description = scanner.nextLine();
            	
            	System.out.println("Quantidade [100] : ");
            	String quantityStr = scanner.next();
            	
            	System.out.println("Preço Unitário [24.80] : ");
            	String priceStr = scanner.next();
            	
            	System.out.println("Ticker do ativo [PETR4] : ");
            	String assetTicker = scanner.next();
            	
            	BigDecimal quantity = new BigDecimal(quantityStr);
            	BigDecimal price = new BigDecimal(priceStr);
            	
            	Asset asset = ServiceBuilder.buildAssetService().getAsset(assetTicker);
            	
            	Trade trade = new Trade(UUID.randomUUID(), description, LocalDateTime.now(), quantity, price, asset);
            	
                cadastrarTrade(uuid, trade);

                break;

            case 2:
            	uuid = readWalletUUID(scanner, uuid);
            	consolidarCarteira(uuid);
                break;

            case 3:
            	uuid = readWalletUUID(scanner, uuid);
                consultarValorTotalCarteira(uuid, user);
                break;

            case 4:
            	uuid = readWalletUUID(scanner, uuid);
                consultarPosicaoConsolidadaPorAtivo(uuid, user);
                break;

            case 5:            	
            	uuid = readWalletUUID(scanner, uuid);
                dashboard(uuid);
                break;

            case 0:
                sair();
                break;

            default:
                System.out.println("Opção inválida. Tente novamente.");

        }
        
    }  
    

    private void cadastrarTrade(UUID uuid, Trade trade) {
    	Position position = ServiceBuilder.buildTradeService().processTrade(trade, trade.getAsset().getTicker());
    	ServiceBuilder.buildWalletService().updatePosition(uuid, position);
    }

    private void consolidarCarteira(UUID uuid) {
    	long initTime = System.currentTimeMillis();
    	ServiceBuilder.buildWalletService().consolidateWallet(uuid);
    	long endTime = System.currentTimeMillis();
    	System.out.println("Processamento de consolidação da Carteira concluído em [" + (endTime - initTime) + "ms]");
    }

    private void consultarValorTotalCarteira(UUID uuid, User user) {
    	Wallet wallet = ServiceBuilder.buildWalletService().getWallet(uuid, user);
    	if (Objects.nonNull(wallet)) {
    		System.out.println("Valor total da carteira = " + wallet.getConsolidatedValue());
    	} else {
    		System.out.println("Carteira não encontrada");
    	}
    }

    private void consultarPosicaoConsolidadaPorAtivo(UUID uuid, User user) {
    	Wallet wallet = ServiceBuilder.buildWalletService().getWallet(uuid, user);
    	if (Objects.nonNull(wallet)) {
    		Map<String, Position> map = wallet.getPositionByAssetClass();
    		
    		map.entrySet().forEach( entry -> {
    			System.out.println(
    					" [ Ativo       = " + entry.getKey() + " ] " + 
    					" [ Preço Médio = " + entry.getValue().getPositionAveragePrice() + " ] " +
    					" [ Posição     = " + entry.getValue().getPositionTotalPrice() + " ] "
    			);
    			System.out.println();
    		});
    		
    	} else {
    		System.out.println("Carteira não encontrada");
    	}
    }

    private void dashboard(UUID uuid) {
    	Wallet wallet = ServiceBuilder.buildWalletService().doDashboard(uuid);
        System.out.println("Dashboard = " + wallet.toString());
    }

    private void sair() {
        System.out.println("Saindo...");
        running = false;
    }

    public static void main(String[] args) {
        TerminalController controller = new TerminalController();
        controller.start();
    }
    
	private UUID readWalletUUID(Scanner scanner, UUID uuid) {
		System.out.println("Digite o UUID da carteira [0 para usar default] :");
		String walletUUID;
		walletUUID = scanner.next();
		if (Objects.nonNull(walletUUID) && !"0".equals(walletUUID)) {
			uuid = UUID.fromString(walletUUID);
		}
		return uuid;
	}      
    
}

