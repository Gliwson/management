package pl.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pl.management.map.schedul.SavePointDto;
import pl.management.map.schedul.factory.ImportFactoryService;


@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private final ImportFactoryService importFactoryService;
    private final SavePointDto savePointDto;

    public SchedulerConfig(ImportFactoryService importFactoryService, SavePointDto savePointDto) {
        this.importFactoryService = importFactoryService;
        this.savePointDto = savePointDto;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addCronTask(() -> importFactoryService.findStrategy(Format.JSON),
//                "0 0/1 * * * *");

        taskRegistrar.addCronTask(savePointDto::service,
                "0 0/1 * * * *");
    }
}
