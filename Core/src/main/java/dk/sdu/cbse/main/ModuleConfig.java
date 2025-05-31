package dk.sdu.cbse.main;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.ServiceLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dk.sdu.cbse.common.services.IEntityPostProcessingService;
import dk.sdu.cbse.common.services.IEntityProcessingService;

@Configuration
public class ModuleConfig {

    public ModuleConfig() {
    }

    @Bean
    public Game game(){
        return new Game(entityProcessingServiceList(), postEntityProcessingServices());
    }

    @Bean
    public Collection<? extends IEntityProcessingService> entityProcessingServiceList(){
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    @Bean
    public Collection<? extends IEntityPostProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load(IEntityPostProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}