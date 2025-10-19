package br.com.five.seven.food.rest.mapper;

import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import br.com.five.seven.food.rest.request.ClientRequest;
import br.com.five.seven.food.rest.response.ClientResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client requestToDomain(ClientRequest request);
    Client entityToDomain(ClientEntity client);
    ClientEntity domainToEntity(Client cliente);
    ClientResponse domainToResponse(Client client);
}
