package pl.management.map.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigProperties {

    private final Environment environment;

    @Value("${databaseLogin}")
    private String databaseLogin;
    @Value("${databasePassword}")
    private String databasePassword;
    @Value("${urlDisk}")
    private String urlDisk;
    @Value("${urlLocation}")
    private String urlLocation;

    public ConfigProperties(Environment environment) {
        this.environment = environment;
    }

}
