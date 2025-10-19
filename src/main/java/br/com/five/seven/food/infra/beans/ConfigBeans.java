package br.com.five.seven.food.infra.beans;

import br.com.five.seven.food.application.service.ClientService;
import br.com.five.seven.food.domain.repository.IClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigBeans {


    @Bean
    public ClientService clientService(IClientRepository iClientRepository) {
        return new ClientService(iClientRepository);
    }

}
