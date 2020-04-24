package pl.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pl.management.map.schedul.factory.ImportFactoryService;
import pl.management.map.schedul.model.Format;


@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private final ImportFactoryService importFactoryService;

    public SchedulerConfig(ImportFactoryService importFactoryService) {
        this.importFactoryService = importFactoryService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(() -> importFactoryService.findStrategy(Format.JSON),
                "0 0/1 * * * *");
    }
}
