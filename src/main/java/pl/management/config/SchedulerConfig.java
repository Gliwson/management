package pl.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pl.management.map.schedul.SavePointDto;


@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private final SavePointDto savePointDto;

    public SchedulerConfig(SavePointDto savePointDto) {
        this.savePointDto = savePointDto;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(savePointDto::saveTODatabase,
                "0 0/10 * * * *");
    }
}
