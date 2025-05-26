package br.com.itads.empirico.adapters.out.repository.file.config;

import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class FilePathConfig {

	public static String getFilePath() {

		ResourceBundle property = null;

		try {
			property = PropertyResourceBundle.getBundle("application");

			if (Objects.nonNull(property)) {
				return property.getString("filepath");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}				

		return null;

	}
	
}
