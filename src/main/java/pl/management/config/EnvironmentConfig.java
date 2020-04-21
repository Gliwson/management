package pl.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvironmentConfig {

    private final Environment environment;

    @Value("${urlJson}")
    private String URL_JSON_SHEETS;

    @Value("${urlCSV}")
    private static String URL_CSV_SHEETS;

    public String getURL_JSON_SHEETS() {
        return URL_JSON_SHEETS;
    }

    public static String getUrlCsvSheets() {
        return URL_CSV_SHEETS;
    }

    public EnvironmentConfig(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

}
