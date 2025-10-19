package br.com.five.seven.food.infra.persistence.dynamodb.repository;

import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import br.com.five.seven.food.infra.persistence.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryTest {

    @Mock
    private ClienteRepository clientRepository;

    private ClientEntity validClientEntity;

    @BeforeEach
    void setUp() {
        validClientEntity = new ClientEntity();
        validClientEntity.setId("1");
        validClientEntity.setName("John Doe");
        validClientEntity.setCpf("12345678901");
        validClientEntity.setEmail("john@example.com");
    }

    @Test
    void saveShouldReturnSavedClient() {
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(validClientEntity);

        ClientEntity result = clientRepository.save(validClientEntity);

        assertNotNull(result);
        assertEquals(validClientEntity, result);
        verify(clientRepository).save(validClientEntity);
    }

    @Test
    void findByIdWithValidIdShouldReturnClient() {
        when(clientRepository.findById("1")).thenReturn(Optional.of(validClientEntity));

        Optional<ClientEntity> result = clientRepository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("John Doe", result.get().getName());
        verify(clientRepository).findById("1");
    }

    @Test
    void findByIdWithInvalidIdShouldReturnEmpty() {
        when(clientRepository.findById("invalid")).thenReturn(Optional.empty());

        Optional<ClientEntity> result = clientRepository.findById("invalid");

        assertFalse(result.isPresent());
        verify(clientRepository).findById("invalid");
    }

    @Test
    void findAllShouldReturnAllClients() {
        ClientEntity client2 = new ClientEntity();
        client2.setId("2");
        client2.setName("Jane Doe");
        List<ClientEntity> clients = List.of(validClientEntity, client2);
        when(clientRepository.findAll()).thenReturn(clients);

        List<ClientEntity> result = clientRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void findByCpfWithValidCpfShouldReturnClient() {
        when(clientRepository.findByCpf("12345678901")).thenReturn(validClientEntity);

        ClientEntity result = clientRepository.findByCpf("12345678901");

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        verify(clientRepository).findByCpf("12345678901");
    }

    @Test
    void findByCpfWithInvalidCpfShouldReturnEmpty() {
        when(clientRepository.findByCpf("invalid")).thenReturn(null);

        ClientEntity result = clientRepository.findByCpf("invalid");

        assertNull(result);
        verify(clientRepository).findByCpf("invalid");
    }

    @Test
    void deleteShouldCallRepositoryDelete() {
        clientRepository.delete(validClientEntity);

        verify(clientRepository).delete(validClientEntity);
    }

    @Test
    void deleteByIdWithExistingIdShouldDeleteClient() {
        when(clientRepository.findById("1")).thenReturn(Optional.of(validClientEntity));

        clientRepository.deleteById("1");

        verify(clientRepository).findById("1");
        verify(clientRepository).deleteById("1");
    }

    @Test
    void deleteByIdWithNonExistingIdShouldNotDelete() {
        when(clientRepository.findById("invalid")).thenReturn(Optional.empty());

        clientRepository.deleteById("invalid");

        verify(clientRepository).findById("invalid");
        verify(clientRepository, never()).deleteById("invalid");
    }

    @Test
    void existsByIdWithExistingIdShouldReturnTrue() {
        when(clientRepository.existsById("1")).thenReturn(true);

        boolean result = clientRepository.existsById("1");

        assertTrue(result);
        verify(clientRepository).existsById("1");
    }

    @Test
    void existsByIdWithNonExistingIdShouldReturnFalse() {
        when(clientRepository.existsById("invalid")).thenReturn(false);

        boolean result = clientRepository.existsById("invalid");

        assertFalse(result);
        verify(clientRepository).existsById("invalid");
    }
}
