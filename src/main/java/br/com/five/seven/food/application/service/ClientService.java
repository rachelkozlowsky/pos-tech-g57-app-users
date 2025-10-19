package br.com.five.seven.food.application.service;

import br.com.five.seven.food.application.ports.in.ClientUseCase;
import br.com.five.seven.food.application.utils.ValidationUtil;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.infra.persistence.repository.ClienteRepository;
import br.com.five.seven.food.infra.utils.FoodUtils;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ClientService implements ClientUseCase {

    private final ClienteRepository repository;

    private final ClientMapper mapper;

    @Transactional
    @Override
    public Client createClient(Client client) throws ValidationException {
        validateClient(client);
        var clientSearched = findByCpf(client.getCpf());
        if (clientSearched == null) {
            return mapper.entityToDomain(repository.save(mapper.domainToEntity(client)));
        }
        client.setId(clientSearched.getId());
        return update(client.getCpf(), client);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDomain).toList();
    }

    @Override
    public Client findByCpf(String cpf) {
        return mapper.entityToDomain(repository.findByCpf(FoodUtils.limparString(cpf)));
    }

    @Override
    public void delete(String cpf) {
        var client = repository.findByCpf(cpf);
        repository.delete(client);
    }

    @Override
    public Client update(String cpf, Client client) throws ValidationException {
        validateClient(client);
        client.setCpf(FoodUtils.limparString(cpf));
        return mapper.entityToDomain(repository.save(mapper.domainToEntity(client)));
    }

    private void validateClient(Client client) throws ValidationException {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new ValidationException("Client name cannot be empty");
        }

        if (client.getCpf() != null && !ValidationUtil.validarCPF(client.getCpf())) {
            throw new ValidationException("Client cpf cannot be valid");
        }

        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new ValidationException("Client cpf cannot be empty");
        }

        if (client.getEmail() != null && !ValidationUtil.validarEmail(client.getEmail())) {
            throw new ValidationException("Client email cannot be valid");
        }
    }

}
