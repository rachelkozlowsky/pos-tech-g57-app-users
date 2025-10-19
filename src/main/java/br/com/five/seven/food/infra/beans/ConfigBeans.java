package br.com.five.seven.food.infra.beans;

import br.com.five.seven.food.application.service.ClientService;
import br.com.five.seven.food.infra.persistence.repository.ClienteRepository;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigBeans {


    @Bean
    public ClientService clientService(ClienteRepository orderRepository, ClientMapper clientMapper) {
        return new ClientService(orderRepository, clientMapper);
    }

}
