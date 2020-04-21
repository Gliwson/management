package pl.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pl.management.map.schedul.ImportPointDto;
import pl.management.map.schedul.format.MapperJsonToPointDto;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private final EnvironmentConfig environmentConfig;

    public SchedulerConfig(EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ImportPointDto pointDTO = new ImportPointDto();
        pointDTO.setTypeOfImport(new MapperJsonToPointDto(environmentConfig.getURL_JSON_SHEETS()));
        taskRegistrar.addCronTask(
                pointDTO::anImport
                , "0 0/1 * * * *");
    }
}
