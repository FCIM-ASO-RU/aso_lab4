package aso_lab4.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * Custom Logger for project
 */
public abstract class Log {
    private static final Logger my_log = Logger.getLogger("MyLogger");

    private Log() {
    }

    /**
     * Static initialization (add possibility to save logs in file mylog.log)
     */
    static {
        my_log.setUseParentHandlers(false);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("mylog.log");
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord logRecord) {
                    return logRecord.getLevel() + ":[" + ISO_INSTANT.format(logRecord.getInstant()) + "]" + logRecord.getMessage() + "\n";
                }
            });
        } catch (IOException e) {
            Logger.getLogger("Logger").info("FILE HANDLER CAN NOT BE CREATED");
            throw new RuntimeException(e);
        }
        my_log.addHandler(fileHandler);
    }

    /**
     * Log action in command prompt
     *
     * @param message Message, what should be logged
     */
    public static void info(final String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String outerClassName = stackTrace[2].getClassName();
        my_log.info(format("[ %s]: %s", outerClassName, message));
    }

    /**
     * Log string format action
     *
     * @param message Message, what should be logged
     * @param args    args for String.format message
     */
    public static void infoFormat(final String message, final String... args) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String outerClassName = stackTrace[2].getClassName();
        my_log.info(format("[ %s]: %s", outerClassName, format(message, args)));
    }
}
