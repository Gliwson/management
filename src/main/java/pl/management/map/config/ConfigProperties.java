package pl.management.map.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigProperties {

    private final Environment environment;

//    @Value("${databaseLogin}")
//    private String databaseLogin;
//    @Value("${databasePassword}")
//    private String databasePassword;
//    @Value("${urlCSV}")
//    private String urlCSV;
@Value("${urlJson}")
private String urlJson;

    public ConfigProperties(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

//    public String getDatabaseLogin() {
//        return databaseLogin;
//    }
//
//    public void setDatabaseLogin(String databaseLogin) {
//        this.databaseLogin = databaseLogin;
//    }
//
//    public String getDatabasePassword() {
//        return databasePassword;
//    }
//
//    public void setDatabasePassword(String databasePassword) {
//        this.databasePassword = databasePassword;
//    }
//
//    public String getUrlCSV() {
//        return urlCSV;
//    }
//
//    public void setUrlCSV(String urlCSV) {
//        this.urlCSV = urlCSV;
//    }

    public String getUrlJson() {
        return urlJson;
    }

    public void setUrlJson(String urlJson) {
        this.urlJson = urlJson;
    }
}
