package org.java.financial.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global logger utility for the financial application.
 * <p>
 * This class provides a single instance of the logger for use across the entire project.
 * It helps maintain consistency in logging throughout different components.
 * </p>
 */
public class GlobalLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger("org.java.financial");

    private GlobalLogger() {
        // Private constructor to prevent instantiation
    }
}
