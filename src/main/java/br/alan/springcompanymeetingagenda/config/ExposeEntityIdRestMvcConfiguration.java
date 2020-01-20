package br.alan.springcompanymeetingagenda.config;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * ExposeEntityIdRestMvcConfiguration
 */
@Component
public class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Meeting.class, ResourceType.class, Resource.class);
    }
}
