package app;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CallLogger {

	//to record a simple text file log.
	
	static private FileHandler logFile;
	static private SimpleFormatter logFormatter;
	
	static public void setup() throws IOException {
		
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
//		logger.setLevel(Level.INFO);
		logFile = new FileHandler("logFile.txt", true);
		
		logFormatter = new SimpleFormatter();
		logFile.setFormatter(logFormatter);
		logFile.setLevel(Level.INFO);
		logger.addHandler(logFile);
		
	}
	
}
