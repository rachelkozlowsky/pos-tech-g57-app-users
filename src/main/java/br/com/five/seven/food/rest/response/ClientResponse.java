package br.com.five.seven.food.rest.response;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private String id;
    private String cpf;
    private String name;
    private String email;
    private String phone;


}
