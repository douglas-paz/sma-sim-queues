package sma.io;

import java.util.Locale;

public class Logger {

    private static Logger instance;
    private final LOG_MODE mode;
    private final StringBuilder data;
    private final String filename;


    private Logger(LOG_MODE mode, String filename) {
        this.mode = mode;
        this.data = new StringBuilder();
        this.filename = filename;
    }

    public static Logger init(LOG_MODE mode) {
        return Logger.init(mode, "file");
    }

    private static Logger init(LOG_MODE mode, String filename) {
        if (instance == null) {
            instance = new Logger(mode, (filename + "_" + System.currentTimeMillis() + ".out"));
        }
        return instance;
    }

    public void log(String message) {
        if (LOG_MODE.CONSOLE.equals(this.mode)) System.out.println(message);
        this.data.append(message).append("\n");
    }

    public void persist() {
        if (LOG_MODE.FILE.equals(this.mode)) Loader.writeOnPath(filename, data.toString());
    }

    public void error(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    public enum LOG_MODE {
        CONSOLE, FILE
    }

}
