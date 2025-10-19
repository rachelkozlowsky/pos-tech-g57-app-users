package br.com.five.seven.food.infra.persistence.repository;

import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.domain.repository.IClientRepository;
import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ClientRepositoryAdapterImpl implements IClientRepository {

    private final ClienteRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client save(Client client) {
        if (client.getId() == null || client.getId().isEmpty()) {
            client.setId(UUID.randomUUID().toString());
        }

        ClientEntity clientEntity = new ClientEntity(client);
        ClientEntity savedEntity = clientRepository.save(clientEntity);
        return clientMapper.entityToDomain(savedEntity);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Client findByCpf(String cpf) {
        Optional<ClientEntity> entity = clientRepository.findByCpf(cpf);
        return entity.map(clientMapper::entityToDomain).orElse(null);
    }

    @Override
    public void delete(String cpf) {
        Optional<ClientEntity> entity = clientRepository.findByCpf(cpf);
        entity.ifPresent(clientRepository::delete);
    }

    @Override
    public Client findById(String id) {
        Optional<ClientEntity> entity = clientRepository.findById(id);
        return entity.map(clientMapper::entityToDomain).orElse(null);
    }
}
