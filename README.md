# empirico-wallet
Consolidador de Carteiras

Requisitos :
1 – Cadastrar operações sobre um ativo :
	1.1-COMPRA
	1.2-VENDA
	1.3-LUCRO
	
2- Consolidar o resultado global de posição dos ativos (qual o patrimônio do cliente)

3- Consolidar o resultado global de posição dos ativos, corrigido pelo lucro por ativo (qual o patrmonio do cliente + proventos)

4 – Salvar data de compra dos ativos, para permitir calcular histórico de valorização pelo tempo de carrego do ativo.

5 – Apresentação de Dashboard contendo :
	- Ticker do ativo (IVVB11)
	- tamanho da posição (1100)
	- preço médio da posição (32,54 R$)
	- Preço atual do ativo (40,00 R$)

6 - Componentes estruturais necessário
	- API para consulta de preço atual dos ativos
	- Armazenamento de dados dos ativos em carteira