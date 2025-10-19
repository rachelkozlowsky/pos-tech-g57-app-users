package br.com.five.seven.food.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    private String id;
    @CPF(message = "Cpf inválido")
    private String cpf;
    @NotEmpty(message = "Nome é obrigatório")
    @NotNull(message = "Nome é obrigatório")
    private String name;
    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    private String phone;
}
