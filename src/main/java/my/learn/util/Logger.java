package my.learn.util;

import my.learn.config.AppConfig;

public class Logger {

    private final boolean isEnabled = AppConfig.LOGGER_IS_ENABLED;
    private static Logger LOGGER;

    public static void log(String msg) {
        if (LOGGER == null) {
            LOGGER = new Logger();
        }

        if (LOGGER.isEnabled) {
            System.err.println("=========LOG=========: " + msg);
        }
    }

    public static void error(String msg) {
        if (LOGGER == null) {
            LOGGER = new Logger();
        }

        if (LOGGER.isEnabled) {
            System.err.println("=========ERROR=========: " + msg);
        }
    }

}
