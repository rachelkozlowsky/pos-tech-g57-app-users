package br.com.five.seven.food.application.service;

import br.com.five.seven.food.application.ports.in.ClientUseCase;
import br.com.five.seven.food.application.utils.ValidationUtil;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.domain.repository.IClientRepository;
import br.com.five.seven.food.infra.utils.FoodUtils;
import jakarta.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ClientService implements ClientUseCase {

    private final IClientRepository repository;

    @Override
    public Client createClient(Client client) throws ValidationException {
        validateClient(client);
        var clientSearched = findByCpf(client.getCpf());
        if (clientSearched == null) {
            return repository.save(client);
        }
        client.setId(clientSearched.getId());
        return update(client.getCpf(), client);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client findByCpf(String cpf) {
        return repository.findByCpf(FoodUtils.limparString(cpf));
    }

    @Override
    public Client update(String cpf, Client client) throws ValidationException {
        validateClient(client);
        client.setCpf(FoodUtils.limparString(cpf));
        return repository.save(client);
    }

    @Override
    public void delete(String cpf) {
        repository.delete(cpf);
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
