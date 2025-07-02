package br.com.itads.empirico.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import br.com.itads.empirico.view.importer.dto.LineDTO;

public class FileUtils {

	public static String getFilePath(String fileName) {

		ResourceBundle property = null;

		try {
			property = PropertyResourceBundle.getBundle("application");

			if (Objects.nonNull(property)) {
				return property.getString( fileName );
			}

		} catch (Exception e) {
			e.printStackTrace();
		}				

		return null;

	}	

	public static List<LineDTO> lerCsvsDoDiretorio(String caminhoDiretorio) throws IOException {
		List<LineDTO> lineDTOs = new ArrayList<>();		
		File diretorio = new File(caminhoDiretorio);
		if (!diretorio.exists() || !diretorio.isDirectory()) {
			System.out.println("Diretório inválido: " + caminhoDiretorio);
			return null;
		}
		File[] arquivos = diretorio.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".csv"));
		if (arquivos == null || arquivos.length == 0) {
			System.out.println("Nenhum arquivo CSV encontrado no diretório.");
			return null;
		}
		for (File arquivo : arquivos) {
			List<LineDTO> list = lerArquivoCSV(arquivo.getAbsolutePath());
			lineDTOs.addAll( list );
		}
		return lineDTOs;
	}

	public static List<LineDTO> lerArquivoCSV(String caminhoArquivo) throws IOException {
		List<LineDTO> lineDTOs = new ArrayList<>();
		
        FileInputStream fis = new FileInputStream(caminhoArquivo);
        InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);
		
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
					FixUtils.fix(valores[1]),
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
		List<LineDTO> myList = lineDTOs.stream()
			    .sorted(Comparator.comparing(t -> {
			    	LineDTO lineDTO = (LineDTO)	t;
			        String dataString = lineDTO.data();
			        LocalDateTime localDateTime = LocalDateTime.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			        return localDateTime;
			    }))			    
			    .toList();
		 
		 return myList;
				
	}	

	public static void validateFile(String fileName) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			} else {
				if (!f.canRead() || !f.canWrite()) {
					throw new RuntimeException(f.getAbsoluteFile() + "hasn't permission to read or write");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
