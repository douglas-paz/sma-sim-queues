package sma.io;

import java.util.Map;

public class Logger {

    private static Logger instance;
    private final LOG_MODE outputMode;
    private final boolean debugEnabled;
    private final StringBuilder data;
    private final String filePath;


    private Logger(String filePath, LOG_MODE outputMode, boolean debugEnabled) {
        this.outputMode = outputMode;
        this.data = new StringBuilder();
        this.filePath = filePath;
        this.debugEnabled = debugEnabled;
    }

    public static Logger init(String filename, LOG_MODE mode, boolean debugEnabled) {
        if (instance == null) {
            instance = new Logger(filename, mode, debugEnabled);
        }
        return instance;
    }

    public void debug(String message) {
        if (this.debugEnabled) log(message);
    }

    public void log(String message) {
        if (LOG_MODE.CONSOLE.equals(this.outputMode)) System.out.println(message);
        this.data.append(message).append("\n");
    }

    public void persist() {
        if (LOG_MODE.FILE.equals(this.outputMode)) Loader.writeOnPath(filePath, data.toString());
    }

    public void error(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    public enum LOG_MODE {
        CONSOLE, FILE
    }

    public static String formatMapLog(Map<?, ?> map) {
        StringBuilder line = new StringBuilder();
        map.forEach((key, value) -> line.append("  ").append(key).append(": ").append(value).append("\n"));
        return line.toString();
    }

}
