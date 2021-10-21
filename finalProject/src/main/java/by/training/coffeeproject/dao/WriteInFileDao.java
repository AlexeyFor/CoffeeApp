package by.training.coffeeproject.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class WriteInFileDao {
	private static final Logger LOG = LogManager.getLogger(WriteInFileDao.class);

	private WriteInFileDao() {
	}

	private static WriteInFileDao instance = new WriteInFileDao();

	public static WriteInFileDao getInstance() {
		return instance;
	}

	public void writeInFile(String savingStr, String path) {

		try {
			File file = new File(path);
			FileWriter writer = null;
			writer = new FileWriter(file, true);
			writer.write(savingStr);
			writer.close();
		} catch (IOException ex) {
			LOG.warn("can't write data in file");
		}
	}
}
