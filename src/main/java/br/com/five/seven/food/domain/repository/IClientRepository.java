package br.com.five.seven.food.domain.repository;

import br.com.five.seven.food.domain.model.Client;

import java.util.List;

public interface IClientRepository {

    Client save(Client client);

    List<Client> findAll();

    Client findByCpf(String cpf);

    void delete(String cpf);

    Client findById(String id);
}
