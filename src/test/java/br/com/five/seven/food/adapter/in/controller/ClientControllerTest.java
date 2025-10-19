package br.com.five.seven.food.adapter.in.controller;

import br.com.five.seven.food.application.ports.in.ClientUseCase;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.rest.ClientController;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import br.com.five.seven.food.rest.request.ClientRequest;
import br.com.five.seven.food.rest.response.ClientResponse;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientUseCase clientService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    @Test
    void shouldGetAllClients() {
        Client client = createTestClient();
        ClientResponse clientResponse = createTestClientResponse();

        when(clientService.findAll()).thenReturn(List.of(client));
        when(clientMapper.domainToResponse(client)).thenReturn(clientResponse);

        ResponseEntity<List<ClientResponse>> response = clientController.listAllClients();

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldGetClientByCpf() {
        String cpf = "test";
        Client client = createTestClient();
        ClientResponse clientResponse = createTestClientResponse();

        when(clientService.findByCpf(cpf)).thenReturn(client);
        when(clientMapper.domainToResponse(client)).thenReturn(clientResponse);

        ResponseEntity<ClientResponse> response = clientController.findByCpf(cpf);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(clientResponse, response.getBody());
    }

    @Test
    void shouldCreateClient() throws ValidationException {
        ClientRequest createRequest = createClientRequest();
        Client client = createTestClient();

        when(clientMapper.requestToDomain(createRequest)).thenReturn(client);
        when(clientService.createClient(client)).thenReturn(client);

        ResponseEntity<ClientResponse> response = clientController.createClient(createRequest);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void shouldUpdateClient() throws ValidationException {
        String cpf = "test";
        ClientRequest updateRequest = createClientRequest();
        Client client = createTestClient();
        ClientResponse clientResponse = createTestClientResponse();

        when(clientMapper.requestToDomain(updateRequest)).thenReturn(client);
        when(clientService.update(cpf, client)).thenReturn(client);
        when(clientMapper.domainToResponse(client)).thenReturn(clientResponse);

        ResponseEntity<ClientResponse> response = clientController.update(cpf, updateRequest);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(clientResponse, response.getBody());
    }

    @Test
    void shouldDeleteClient() {
        String cpf = "test";

        doNothing().when(clientService).delete(cpf);

        ResponseEntity<Void> response = clientController.delete(cpf);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    private Client createTestClient() {
        return new Client("id", "cpf", "name", "email", "phone");
    }

    private ClientResponse createTestClientResponse() {
        return new ClientResponse("id", "cpf", "name", "email", "phone");
    }

    private ClientRequest createClientRequest() {
        return new ClientRequest("45455454554", "cpf", "name", "email", "phone");
    }
}
