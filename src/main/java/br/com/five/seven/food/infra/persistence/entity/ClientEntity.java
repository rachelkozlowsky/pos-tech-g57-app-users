package br.com.five.seven.food.infra.persistence.entity;

import br.com.five.seven.food.domain.model.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_client")
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cpf;
    private String name;
    private String email;
    private String phone;

    public ClientEntity(Client client) {
        this.id = client.getId();
        this.cpf = client.getCpf();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
    }

}
