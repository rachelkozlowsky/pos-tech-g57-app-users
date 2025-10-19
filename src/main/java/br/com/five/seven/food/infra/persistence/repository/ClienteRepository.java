package br.com.five.seven.food.infra.persistence.repository;

import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClientEntity, String> {

    Optional<ClientEntity> findByCpf(String cpf);
}
