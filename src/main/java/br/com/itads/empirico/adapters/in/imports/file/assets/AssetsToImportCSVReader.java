package br.com.itads.empirico.adapters.in.imports.file.assets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.itads.empirico.adapters.in.console.AssetAdapter;
import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;
import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.util.AdapterBuilder;

public class AssetsToImportCSVReader {

	String assetsFileName = FilePathConfig.getFilePath("assetsToImport");
	AssetAdapter adapter = AdapterBuilder.buildAssetAdapter();

	public List<Asset> readCSVFile(String filepath) throws IOException {
		List<Asset> assetList = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line;
		boolean isHeader = true;

		while ((line = br.readLine()) != null) {
			if (isHeader) {
				isHeader = false;
				continue;
			}

			String[] valores = line.split(";");
			Asset asset = new Asset(valores[0], valores[1], AssetClassEnum.valueOf(valores[2]));

			assetList.add(asset);
		}

		br.close();
		return assetList;
	}

	public void read() {
		try {
			List<Asset> assetList = readCSVFile(assetsFileName);
			for (Asset asset : assetList) {
				System.out.println(asset.getTicker() + " " + asset.getDescription() + " " + asset.getType());
				adapter.saveOrUpdate(asset);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AssetsToImportCSVReader leitor = new AssetsToImportCSVReader();
		leitor.read();
	}
}
