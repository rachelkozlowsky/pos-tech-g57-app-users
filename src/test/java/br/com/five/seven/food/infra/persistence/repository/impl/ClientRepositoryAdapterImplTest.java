package br.com.five.seven.food.infra.persistence.repository.impl;

import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import br.com.five.seven.food.infra.persistence.repository.ClientRepositoryAdapterImpl;
import br.com.five.seven.food.infra.persistence.repository.ClienteRepository;
import br.com.five.seven.food.rest.mapper.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryAdapterImplTest {

    @Mock
    private ClienteRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientRepositoryAdapterImpl clientRepositoryAdapter;

    private Client validClient;
    private ClientEntity validClientEntity;

    @BeforeEach
    void setUp() {
        validClient = new Client();
        validClient.setId("1");
        validClient.setName("John Doe");
        validClient.setCpf("12345678901");
        validClient.setEmail("john@example.com");

        validClientEntity = new ClientEntity();
        validClientEntity.setId("1");
        validClientEntity.setName("John Doe");
        validClientEntity.setCpf("12345678901");
        validClientEntity.setEmail("john@example.com");
    }

    @Test
    void saveWithNewClientShouldGenerateIdAndReturnSavedClient() {
        Client clientWithoutId = new Client();
        clientWithoutId.setName("Jane Doe");
        clientWithoutId.setCpf("98765432100");
        clientWithoutId.setEmail("jane@example.com");

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(validClientEntity);
        when(clientMapper.entityToDomain(validClientEntity)).thenReturn(validClient);

        Client result = clientRepositoryAdapter.save(clientWithoutId);

        assertNotNull(result);
        assertNotNull(clientWithoutId.getId());
        verify(clientRepository).save(any(ClientEntity.class));
        verify(clientMapper).entityToDomain(validClientEntity);
    }

    @Test
    void saveWithExistingClientShouldKeepIdAndReturnSavedClient() {
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(validClientEntity);
        when(clientMapper.entityToDomain(validClientEntity)).thenReturn(validClient);

        Client result = clientRepositoryAdapter.save(validClient);

        assertNotNull(result);
        assertEquals("1", validClient.getId());
        verify(clientRepository).save(any(ClientEntity.class));
        verify(clientMapper).entityToDomain(validClientEntity);
    }

    @Test
    void findAllShouldReturnAllClients() {
        ClientEntity client2Entity = new ClientEntity();
        client2Entity.setId("2");
        client2Entity.setName("Jane Doe");

        Client client2 = new Client();
        client2.setId("2");
        client2.setName("Jane Doe");

        List<ClientEntity> entityList = List.of(validClientEntity, client2Entity);
        when(clientRepository.findAll()).thenReturn(entityList);
        when(clientMapper.entityToDomain(validClientEntity)).thenReturn(validClient);
        when(clientMapper.entityToDomain(client2Entity)).thenReturn(client2);

        List<Client> result = clientRepositoryAdapter.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clientRepository).findAll();
        verify(clientMapper, times(2)).entityToDomain(any(ClientEntity.class));
    }

    @Test
    void findByCpfWithValidCpfShouldReturnClient() {
        when(clientRepository.findByCpf("12345678901")).thenReturn(Optional.of(validClientEntity));
        when(clientMapper.entityToDomain(validClientEntity)).thenReturn(validClient);

        Client result = clientRepositoryAdapter.findByCpf("12345678901");

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        verify(clientRepository).findByCpf("12345678901");
        verify(clientMapper).entityToDomain(validClientEntity);
    }

    @Test
    void findByCpfWithInvalidCpfShouldReturnNull() {
        when(clientRepository.findByCpf("invalid")).thenReturn(Optional.empty());

        Client result = clientRepositoryAdapter.findByCpf("invalid");

        assertNull(result);
        verify(clientRepository).findByCpf("invalid");
        verify(clientMapper, never()).entityToDomain(any());
    }

    @Test
    void deleteWithExistingCpfShouldDeleteClient() {
        when(clientRepository.findByCpf("12345678901")).thenReturn(Optional.of(validClientEntity));

        clientRepositoryAdapter.delete("12345678901");

        verify(clientRepository).findByCpf("12345678901");
        verify(clientRepository).delete(validClientEntity);
    }

    @Test
    void deleteWithNonExistingCpfShouldNotDeleteAnything() {
        when(clientRepository.findByCpf("invalid")).thenReturn(Optional.empty());

        clientRepositoryAdapter.delete("invalid");

        verify(clientRepository).findByCpf("invalid");
        verify(clientRepository, never()).delete(any());
    }

    @Test
    void findByIdWithValidIdShouldReturnClient() {
        when(clientRepository.findById("1")).thenReturn(Optional.of(validClientEntity));
        when(clientMapper.entityToDomain(validClientEntity)).thenReturn(validClient);

        Client result = clientRepositoryAdapter.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(clientRepository).findById("1");
        verify(clientMapper).entityToDomain(validClientEntity);
    }

    @Test
    void findByIdWithInvalidIdShouldReturnNull() {
        when(clientRepository.findById("invalid")).thenReturn(Optional.empty());

        Client result = clientRepositoryAdapter.findById("invalid");

        assertNull(result);
        verify(clientRepository).findById("invalid");
        verify(clientMapper, never()).entityToDomain(any());
    }
}
