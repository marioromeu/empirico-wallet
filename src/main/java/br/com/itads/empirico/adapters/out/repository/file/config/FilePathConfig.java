package br.com.itads.empirico.adapters.out.repository.file.config;

import br.com.itads.empirico.util.FileUtils;

public class FilePathConfig {

	public static String getFilePath(String fileName) {
		return FileUtils.getFilePath( fileName );
	}
	
}
