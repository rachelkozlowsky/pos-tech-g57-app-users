package br.com.five.seven.food.infra.persistence.repository;

import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClientEntity, String> {

    ClientEntity findByCpf(String cpf);
}
