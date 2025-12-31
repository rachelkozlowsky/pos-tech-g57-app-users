package br.com.five.seven.food.rest;

import br.com.five.seven.food.application.ports.in.ClientUseCase;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import br.com.five.seven.food.rest.request.ClientRequest;
import br.com.five.seven.food.rest.response.ClientResponse;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Client Controller BDD Tests")
class ClientControllerTest {

    @Mock
    private ClientUseCase clientService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    // CREATE CLIENT TESTS
    @Test
    @DisplayName("Scenario: Successfully create a new client")
    void givenValidClientRequest_whenCreateClient_thenReturnCreatedClient() throws ValidationException {
        // Given: A valid client request
        ClientRequest request = createClientRequest("12345678901", "John Doe", "john@example.com", "123456789");
        Client client = createClient("12345678901", "John Doe", "john@example.com", "123456789");
        ClientResponse response = createClientResponse("12345678901", "John Doe", "john@example.com", "123456789");

        when(clientMapper.requestToDomain(request)).thenReturn(client);
        when(clientService.createClient(client)).thenReturn(client);
        when(clientMapper.domainToResponse(client)).thenReturn(response);

        // When: Creating the client
        ResponseEntity<ClientResponse> result = clientController.createClient(request);

        // Then: Return OK status with the created client
        assertEquals(OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
        verify(clientService).createClient(client);
    }

    @Test
    @DisplayName("Scenario: Fail to create client with invalid data")
    void givenInvalidClientRequest_whenCreateClient_thenThrowValidationException() throws ValidationException {
        // Given: An invalid client request (invalid CPF)
        ClientRequest request = createClientRequest("invalid", "John Doe", "john@example.com", "123456789");
        Client client = createClient("invalid", "John Doe", "john@example.com", "123456789");

        when(clientMapper.requestToDomain(request)).thenReturn(client);
        when(clientService.createClient(client)).thenThrow(new ValidationException("Invalid CPF"));

        // When & Then: Creating the client throws ValidationException
        assertThrows(ValidationException.class, () -> clientController.createClient(request));
        verify(clientService).createClient(client);
    }

    @Test
    @DisplayName("Scenario: Create client with optional phone field")
    void givenClientRequestWithoutPhone_whenCreateClient_thenReturnCreatedClient() throws ValidationException {
        // Given: A client request without phone
        ClientRequest request = createClientRequest("12345678901", "John Doe", "john@example.com", null);
        Client client = createClient("12345678901", "John Doe", "john@example.com", null);
        ClientResponse response = createClientResponse("12345678901", "John Doe", "john@example.com", null);

        when(clientMapper.requestToDomain(request)).thenReturn(client);
        when(clientService.createClient(client)).thenReturn(client);
        when(clientMapper.domainToResponse(client)).thenReturn(response);

        // When: Creating the client
        ResponseEntity<ClientResponse> result = clientController.createClient(request);

        // Then: Return OK status with the created client
        assertEquals(OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
        verify(clientService).createClient(client);
    }

    // RETRIEVE CLIENT TESTS
    @Test
    @DisplayName("Scenario: Successfully retrieve client by CPF")
    void givenExistingCpf_whenFindByCpf_thenReturnClient() {
        // Given: An existing CPF
        String cpf = "12345678901";
        Client client = createClient(cpf, "John Doe", "john@example.com", "123456789");
        ClientResponse response = createClientResponse(cpf, "John Doe", "john@example.com", "123456789");

        when(clientService.findByCpf(cpf)).thenReturn(client);
        when(clientMapper.domainToResponse(client)).thenReturn(response);

        // When: Finding the client by CPF
        ResponseEntity<ClientResponse> result = clientController.findByCpf(cpf);

        // Then: Return OK status with the client
        assertEquals(OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
        verify(clientService).findByCpf(cpf);
    }

    @Test
    @DisplayName("Scenario: Fail to retrieve non-existing client by CPF")
    void givenNonExistingCpf_whenFindByCpf_thenThrowException() {
        // Given: A non-existing CPF
        String cpf = "99999999999";

        when(clientService.findByCpf(cpf)).thenThrow(new RuntimeException("Client not found"));

        // When & Then: Finding the client throws exception
        assertThrows(RuntimeException.class, () -> clientController.findByCpf(cpf));
        verify(clientService).findByCpf(cpf);
    }

    @Test
    @DisplayName("Scenario: Successfully retrieve all clients")
    void givenClientsExist_whenListAllClients_thenReturnClientList() {
        // Given: Existing clients
        Client client = createClient("12345678901", "John Doe", "john@example.com", "123456789");
        ClientResponse response = createClientResponse("12345678901", "John Doe", "john@example.com", "123456789");
        List<Client> clients = List.of(client);

        when(clientService.findAll()).thenReturn(clients);
        when(clientMapper.domainToResponse(client)).thenReturn(response);

        // When: Listing all clients
        ResponseEntity<List<ClientResponse>> result = clientController.listAllClients();

        // Then: Return OK status with the client list
        assertEquals(OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(response, result.getBody().get(0));
        verify(clientService).findAll();
    }

    @Test
    @DisplayName("Scenario: Retrieve empty client list")
    void givenNoClientsExist_whenListAllClients_thenReturnEmptyList() {
        // Given: No clients exist
        when(clientService.findAll()).thenReturn(Collections.emptyList());

        // When: Listing all clients
        ResponseEntity<List<ClientResponse>> result = clientController.listAllClients();

        // Then: Return OK status with empty list
        assertEquals(OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(clientService).findAll();
    }

    // UPDATE CLIENT TESTS
    @Test
    @DisplayName("Scenario: Successfully update existing client")
    void givenValidUpdateRequest_whenUpdateClient_thenReturnUpdatedClient() throws ValidationException {
        // Given: A valid update request for existing client
        String cpf = "12345678901";
        ClientRequest request = createClientRequest(cpf, "Jane Doe", "jane@example.com", "987654321");
        Client client = createClient(cpf, "Jane Doe", "jane@example.com", "987654321");
        ClientResponse response = createClientResponse(cpf, "Jane Doe", "jane@example.com", "987654321");

        when(clientMapper.requestToDomain(request)).thenReturn(client);
        when(clientService.update(cpf, client)).thenReturn(client);
        when(clientMapper.domainToResponse(client)).thenReturn(response);

        // When: Updating the client
        ResponseEntity<ClientResponse> result = clientController.update(cpf, request);

        // Then: Return OK status with the updated client
        assertEquals(OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
        verify(clientService).update(cpf, client);
    }

    @Test
    @DisplayName("Scenario: Fail to update non-existing client")
    void givenNonExistingCpf_whenUpdateClient_thenThrowException() throws ValidationException {
        // Given: Update request for non-existing client
        String cpf = "99999999999";
        ClientRequest request = createClientRequest(cpf, "Jane Doe", "jane@example.com", "987654321");
        Client client = createClient(cpf, "Jane Doe", "jane@example.com", "987654321");

        when(clientMapper.requestToDomain(request)).thenReturn(client);
        when(clientService.update(cpf, client)).thenThrow(new RuntimeException("Client not found"));

        // When & Then: Updating the client throws exception
        assertThrows(RuntimeException.class, () -> clientController.update(cpf, request));
        verify(clientService).update(cpf, client);
    }

    // DELETE CLIENT TESTS
    @Test
    @DisplayName("Scenario: Successfully delete existing client")
    void givenExistingCpf_whenDeleteClient_thenReturnNoContent() {
        // Given: An existing CPF
        String cpf = "12345678901";

        doNothing().when(clientService).delete(cpf);

        // When: Deleting the client
        ResponseEntity<Void> result = clientController.delete(cpf);

        // Then: Return NO_CONTENT status
        assertEquals(NO_CONTENT, result.getStatusCode());
        verify(clientService).delete(cpf);
    }

    @Test
    @DisplayName("Scenario: Fail to delete non-existing client")
    void givenNonExistingCpf_whenDeleteClient_thenThrowException() {
        // Given: A non-existing CPF
        String cpf = "99999999999";

        doThrow(new RuntimeException("Client not found")).when(clientService).delete(cpf);

        // When & Then: Deleting the client throws exception
        assertThrows(RuntimeException.class, () -> clientController.delete(cpf));
        verify(clientService).delete(cpf);
    }

    // Helper methods
    private Client createClient(String cpf, String name, String email, String phone) {
        Client client = new Client();
        client.setCpf(cpf);
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        return client;
    }

    private ClientResponse createClientResponse(String cpf, String name, String email, String phone) {
        return new ClientResponse(null, cpf, name, email, phone);
    }

    private ClientRequest createClientRequest(String cpf, String name, String email, String phone) {
        ClientRequest request = new ClientRequest();
        request.setCpf(cpf);
        request.setName(name);
        request.setEmail(email);
        request.setPhone(phone);
        return request;
    }
}