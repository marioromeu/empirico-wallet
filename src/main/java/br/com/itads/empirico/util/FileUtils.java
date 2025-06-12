package br.com.itads.empirico.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import br.com.itads.empirico.adapters.in.imports.file.dto.LineDTO;

public class FileUtils {

	public static String getFilePath(String fileName) {

		ResourceBundle property = null;

		try {
			property = PropertyResourceBundle.getBundle("application");

			if (Objects.nonNull(property)) {
				return property.getString( fileName );
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}				

		return null;

	}	

	public static List<LineDTO> lerArquivoCSV(String caminhoArquivo) throws IOException {
		List<LineDTO> lineDTOs = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
		String linha;
		boolean isHeader = true;

		while ((linha = br.readLine()) != null) {
			if (isHeader) {
				isHeader = false; // Ignorar cabeçalho
				continue;
			}

			String[] valores = linha.split(";");
			LineDTO lineDTO = new LineDTO(
					valores[0],
					valores[1],
					valores[2],
					new BigDecimal(valores[3]),
					new BigDecimal(valores[4].replace(".", "").replace(",", ".")),
					new BigDecimal(valores[5].replace(".", "").replace(",", ".")),
					new BigDecimal(valores[6].replace(".", "").replace(",", ".")),
					new BigDecimal(valores[7].replace(".", "").replace(",", ".")),
					new BigDecimal(valores[8].replace(".", "").replace(",", ".")),
					new BigDecimal(valores[9].replace(".", "").replace(",", ".")),
					new BigDecimal(valores[10].replace(".", "").replace(",", "."))
					);

			lineDTOs.add(lineDTO);
		}

		br.close();
		return lineDTOs;
	}	
	
	public static void validateFile(String fileName) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			} else {
				if (f.canRead() && f.canWrite()) {
					System.out.println( f.getAbsoluteFile() + " ok to process ");
				} else {
					throw new RuntimeException(f.getAbsoluteFile() + "hasn't permission to read or write");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
