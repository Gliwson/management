package pl.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pl.management.map.service.schedul.json.ImportSheetsGoogleJson;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private final ImportSheetsGoogleJson importSheetsGoogleJson;

    public SchedulerConfig(ImportSheetsGoogleJson importSheetsGoogleJson) {
        this.importSheetsGoogleJson = importSheetsGoogleJson;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(importSheetsGoogleJson::getJson2, "0 0/10 6-22 * * *");

    }
}
