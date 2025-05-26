package br.com.itads.empirico.adapters.out.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;

public abstract class FileRepository<T> {

	String filePath = FilePathConfig.getFilePath();
	String fileName;
	
	protected FileRepository(String fileNameParam) {
		this.fileName = filePath+fileNameParam.concat(".filedb");
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

	public T write(T tParam) {
		writeObjectToFile(tParam, fileName);
		return tParam;
	}

	public T read() {
		return (T) readObjectFromFile(fileName);
	}

    public void writeObjectToFile(Object obj, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(obj);
            System.out.println("Objeto gravado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readObjectFromFile(String fileName) {
        Object obj = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            obj = in.readObject();
            System.out.println("Objeto lido com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
        	System.out.println(fileName + " -> " + e.getMessage() );
        }
        return obj;
    }	
	
}
