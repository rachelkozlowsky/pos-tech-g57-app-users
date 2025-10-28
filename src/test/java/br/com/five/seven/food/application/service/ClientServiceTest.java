package br.com.five.seven.food.application.service;

import br.com.five.seven.food.application.utils.ValidationUtil;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.domain.repository.IClientRepository;
import br.com.five.seven.food.infra.utils.FoodUtils;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private IClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client validClient;

    @BeforeEach
    void setUp() {
        validClient = new Client();
        validClient.setId("1");
        validClient.setName("John Doe");
        validClient.setCpf("12345678901");
        validClient.setEmail("john@example.com");
    }

    @Test
    void createClientWithValidNewClientShouldReturnSavedClient() throws ValidationException {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class);
             MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {

            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(true);
            foodUtils.when(() -> FoodUtils.limparString(anyString())).thenReturn("12345678901");

            when(clientRepository.findByCpf("12345678901")).thenReturn(null);
            when(clientRepository.save(any(Client.class))).thenReturn(validClient);

            Client result = clientService.createClient(validClient);

            assertNotNull(result);
            assertEquals("John Doe", result.getName());
            verify(clientRepository).findByCpf("12345678901");
            verify(clientRepository).save(validClient);
        }
    }

    @Test
    void withExistingClientShouldUpdateClient() throws ValidationException {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class);
             MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {

            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(true);
            foodUtils.when(() -> FoodUtils.limparString(anyString())).thenReturn("12345678901");

            Client existingClient = new Client();
            existingClient.setId("1");
            existingClient.setCpf("12345678901");

            when(clientRepository.findByCpf("12345678901")).thenReturn(existingClient);
            when(clientRepository.save(any(Client.class))).thenReturn(validClient);

            Client result = clientService.update("12345678901", validClient);
            assertNotNull(result);
            assertEquals("1", validClient.getId());
            verify(clientRepository).findByCpf("12345678901");
            verify(clientRepository).save(any(Client.class));
        }
    }

    @Test
    void createClientWithNullNameShouldThrowValidationException() {
        Client clientWithNullName = new Client();
        clientWithNullName.setName(null);
        clientWithNullName.setCpf("12345678901");
        clientWithNullName.setEmail("john@example.com");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> clientService.createClient(clientWithNullName));

        assertEquals("Client name cannot be empty", exception.getMessage());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void createClientWithEmptyNameShouldThrowValidationException() {
        Client clientWithEmptyName = new Client();
        clientWithEmptyName.setName("");
        clientWithEmptyName.setCpf("12345678901");
        clientWithEmptyName.setEmail("john@example.com");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> clientService.createClient(clientWithEmptyName));

        assertEquals("Client name cannot be empty", exception.getMessage());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void createClientWithInvalidCPFShouldThrowValidationException() {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class)) {
            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(false);

            ValidationException exception = assertThrows(ValidationException.class,
                    () -> clientService.createClient(validClient));

            assertEquals("Client cpf cannot be valid", exception.getMessage());
            verify(clientRepository, never()).save(any(Client.class));
        }
    }

    @Test
    void createClientWithNullEmailShouldThrowValidationException() {
        Client clientWithNullEmail = new Client();
        clientWithNullEmail.setName("John Doe");
        clientWithNullEmail.setCpf("12345678901");
        clientWithNullEmail.setEmail(null);

        assertThrows(ValidationException.class, () -> clientService.createClient(clientWithNullEmail));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void createClientWithInvalidEmailShouldThrowValidationException() {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class)) {
            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(false);

            ValidationException exception = assertThrows(ValidationException.class,
                    () -> clientService.createClient(validClient));

            assertEquals("Client email cannot be valid", exception.getMessage());
            verify(clientRepository, never()).save(any(Client.class));
        }
    }

    @Test
    void findAllShouldReturnAllClients() {
        Client client2 = new Client();
        client2.setId("2");
        client2.setName("Jane Doe");
        List<Client> clients = List.of(validClient, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(clientRepository).findAll();
    }

    @Test
    void findByCpfWithValidCpfShouldReturnClient() {
        try (MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {
            foodUtils.when(() -> FoodUtils.limparString("12345678901")).thenReturn("12345678901");
            when(clientRepository.findByCpf("12345678901")).thenReturn(validClient);

            Client result = clientService.findByCpf("12345678901");

            assertNotNull(result);
            assertEquals("John Doe", result.getName());
            verify(clientRepository).findByCpf("12345678901");
        }
    }

    @Test
    void updateWithValidDataShouldReturnUpdatedClient() throws ValidationException {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class);
             MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {

            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(true);
            foodUtils.when(() -> FoodUtils.limparString(anyString())).thenReturn("12345678901");

            when(clientRepository.save(any(Client.class))).thenReturn(validClient);

            when(clientRepository.findByCpf("12345678901")).thenReturn(validClient);

            Client result = clientService.update("12345678901", validClient);

            assertNotNull(result);
            assertEquals("12345678901", validClient.getCpf());
            verify(clientRepository).save(validClient);
        }
    }

    @Test
    void deleteWithValidCpfShouldDeleteClient() {
        clientService.delete("12345678901");

        verify(clientRepository).delete("12345678901");
    }

    @Test
    void createClientWhenClientNotExistsShouldSaveNewClient() throws ValidationException {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class);
             MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {

            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(true);
            foodUtils.when(() -> FoodUtils.limparString(anyString())).thenReturn("12345678901");

            when(clientRepository.findByCpf("12345678901")).thenReturn(null);
            when(clientRepository.save(any(Client.class))).thenReturn(validClient);

            Client result = clientService.createClient(validClient);

            assertNotNull(result);
            verify(clientRepository).save(any(Client.class));
        }
    }

    @Test
    void createClientWhenClientExistsShouldCallUpdate() throws ValidationException {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class);
             MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {

            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(true);
            foodUtils.when(() -> FoodUtils.limparString(anyString())).thenReturn("12345678901");

            Client existingClient = new Client();
            existingClient.setId("1");
            existingClient.setCpf("12345678901");
            existingClient.setCreatedAt(LocalDateTime.now().minusDays(1));

            when(clientRepository.findByCpf("12345678901")).thenReturn(existingClient);
            when(clientRepository.save(any(Client.class))).thenReturn(validClient);

            Client result = clientService.createClient(validClient);

            assertNotNull(result);
            verify(clientRepository, times(1)).save(any(Client.class));
        }
    }

    @Test
    void updateWhenClientNotFoundShouldThrowValidationException() {
        when(clientRepository.findByCpf("12345678901")).thenReturn(null);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> clientService.update("12345678901", validClient));

        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void validateClientShouldThrowWhenCpfIsInvalid() {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class)) {
            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(false);

            Client invalidClient = new Client();
            invalidClient.setName("John");
            invalidClient.setCpf("12345678901");
            invalidClient.setEmail("john@example.com");

            ValidationException exception = assertThrows(ValidationException.class,
                    () -> clientService.createClient(invalidClient));

            assertEquals("Client cpf cannot be valid", exception.getMessage());
        }
    }

    @Test
    void validateClientShouldThrowWhenEmailIsInvalid() {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class)) {
            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(false);

            Client invalidEmailClient = new Client();
            invalidEmailClient.setName("John");
            invalidEmailClient.setCpf("12345678901");
            invalidEmailClient.setEmail("invalid");

            ValidationException exception = assertThrows(ValidationException.class,
                    () -> clientService.createClient(invalidEmailClient));

            assertEquals("Client email cannot be valid", exception.getMessage());
        }
    }

    @Test
    void validateClientShouldThrowWhenNameIsNull() {
        Client client = new Client();
        client.setName(null);
        client.setCpf("12345678901");
        client.setEmail("john@example.com");

        ValidationException ex = assertThrows(ValidationException.class,
                () -> clientService.createClient(client));

        assertEquals("Client name cannot be empty", ex.getMessage());
    }

    @Test
    void validateClientShouldThrowWhenNameIsEmpty() {
        Client client = new Client();
        client.setName("");
        client.setCpf("12345678901");
        client.setEmail("john@example.com");

        ValidationException ex = assertThrows(ValidationException.class,
                () -> clientService.createClient(client));

        assertEquals("Client name cannot be empty", ex.getMessage());
    }

    @Test
    void validateClientShouldThrowWhenEmailIsNull() {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class)) {
            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);

            Client client = new Client();
            client.setName("John Doe");
            client.setCpf("12345678901");
            client.setEmail(null);

            ValidationException ex = assertThrows(ValidationException.class,
                    () -> clientService.createClient(client));

            assertEquals("Client cpf cannot be empty", ex.getMessage());
        }
    }

    @Test
    void validateClientShouldPassWhenAllFieldsAreValid() throws ValidationException {
        try (MockedStatic<ValidationUtil> validationUtil = mockStatic(ValidationUtil.class);
             MockedStatic<FoodUtils> foodUtils = mockStatic(FoodUtils.class)) {

            validationUtil.when(() -> ValidationUtil.validarCPF(anyString())).thenReturn(true);
            validationUtil.when(() -> ValidationUtil.validarEmail(anyString())).thenReturn(true);
            foodUtils.when(() -> FoodUtils.limparString(anyString())).thenReturn("12345678901");

            when(clientRepository.findByCpf("12345678901")).thenReturn(null);
            when(clientRepository.save(any(Client.class))).thenReturn(validClient);

            Client result = clientService.createClient(validClient);

            assertNotNull(result);
            verify(clientRepository).save(any(Client.class));
        }
    }

}
