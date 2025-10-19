package br.com.five.seven.food.application.ports.in;

import br.com.five.seven.food.domain.model.Client;
import jakarta.xml.bind.ValidationException;

import java.util.List;
public interface ClientUseCase {

    Client createClient(Client client) throws ValidationException;
    List<Client> findAll();
    Client findByCpf(String cpf);
    Client update(String cpf, Client client) throws ValidationException;
    void delete(String cpf);
}
