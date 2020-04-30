package pl.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pl.management.map.schedul.SavePointDto;

import javax.annotation.PostConstruct;


@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Value("cron")
    private String cron;

    private final SavePointDto savePointDto;

    public SchedulerConfig(SavePointDto savePointDto) {
        this.savePointDto = savePointDto;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(savePointDto::saveTODatabase,
                cron);
    }

    @PostConstruct
    private void load() {
        cron = "0 0/10 * * * *";
    }
}
