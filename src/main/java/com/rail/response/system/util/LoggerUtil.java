package com.rail.response.system.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    public static Logger getLogger() {
        // Obtém o nome da classe que chamou o logger automaticamente
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        return LogManager.getLogger(caller.getClassName());
    }
}
