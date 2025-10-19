package br.com.five.seven.food.interfaces.rest;

import br.com.five.seven.food.application.ports.in.ClientUseCase;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import br.com.five.seven.food.rest.request.ClientRequest;
import br.com.five.seven.food.rest.response.ClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientUseCase clientService;

    @MockitoBean
    private ClientMapper clientMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createClientShouldReturnCreatedClient() throws Exception {
        ClientRequest request = new ClientRequest();
        request.setCpf("50901793019");
        request.setName("John Doe");
        request.setEmail("john@example.com");

        Client client = new Client();
        client.setCpf("50901793019");
        client.setName("John Doe");
        client.setEmail("john@example.com");

        ClientResponse response = new ClientResponse();
        response.setCpf("50901793019");
        response.setName("John Doe");
        response.setEmail("john@example.com");

        when(clientMapper.requestToDomain(any())).thenReturn(client);
        when(clientService.createClient(any())).thenReturn(client);
        when(clientMapper.domainToResponse(any())).thenReturn(response);

        mockMvc.perform(post("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(clientService).createClient(any());
    }

    @Test
    void listAllClientsShouldReturnClientList() throws Exception {
        List<Client> clients = List.of(new Client());
        ClientResponse response = new ClientResponse();

        when(clientService.findAll()).thenReturn(clients);
        when(clientMapper.domainToResponse(any())).thenReturn(response);

        mockMvc.perform(get("/v1/clients"))
                .andExpect(status().isOk());

        verify(clientService).findAll();
    }

    @Test
    void findByCpfShouldReturnClient() throws Exception {
        Client client = new Client();
        client.setCpf("12345678901");
        ClientResponse response = new ClientResponse();
        response.setCpf("12345678901");

        when(clientService.findByCpf("12345678901")).thenReturn(client);
        when(clientMapper.domainToResponse(any())).thenReturn(response);

        mockMvc.perform(get("/v1/clients/12345678901"))
                .andExpect(status().isOk());

        verify(clientService).findByCpf("12345678901");
    }

    @Test
    void updateClientShouldReturnUpdatedClient() throws Exception {
        ClientRequest request = new ClientRequest();
        request.setCpf("12345678901");
        request.setName("Jane Doe");
        request.setEmail("jane@example.com");

        Client client = new Client();
        client.setCpf("12345678901");
        client.setName("Jane Doe");
        client.setEmail("jane@example.com");

        ClientResponse response = new ClientResponse();
        response.setCpf("12345678901");
        response.setName("Jane Doe");
        response.setEmail("jane@example.com");

        when(clientMapper.requestToDomain(any())).thenReturn(client);
        when(clientService.update(anyString(), any())).thenReturn(client);
        when(clientMapper.domainToResponse(any())).thenReturn(response);

        mockMvc.perform(put("/v1/clients/12345678901")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(clientService).update(anyString(), any());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    void deleteClientShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/v1/clients/12345678901"))
                .andExpect(status().isNoContent());

        verify(clientService).delete("12345678901");
    }
}
