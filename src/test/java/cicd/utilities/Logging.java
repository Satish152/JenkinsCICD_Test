package cicd.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {
	public Logger logger;
	public static Logger logManager(String logName) {
		return LogManager.getLogger(logName);
	}
}
