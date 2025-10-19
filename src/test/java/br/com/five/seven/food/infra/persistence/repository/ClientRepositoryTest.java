package br.com.five.seven.food.infra.persistence.repository;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryTest {
/*
    @Mock
    private DynamoDbTemplate dynamoDbTemplate;

    @Mock
    private PageIterable<ClientEntity> pageIterable;

    @Mock
    private SdkIterable<ClientEntity> sdkIterable;

    @InjectMocks
    private ClientDynamoDBRepository clientRepository;

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
        ClientEntity result = clientRepository.save(validClientEntity);

        assertNotNull(result);
        assertEquals(validClientEntity, result);
        verify(dynamoDbTemplate).save(validClientEntity);
    }

    @Test
    void findByIdWithValidIdShouldReturnClient() {
        when(dynamoDbTemplate.load(any(Key.class), eq(ClientEntity.class)))
                .thenReturn(validClientEntity);

        Optional<ClientEntity> result = clientRepository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("John Doe", result.get().getName());
        verify(dynamoDbTemplate).load(any(Key.class), eq(ClientEntity.class));
    }

    @Test
    void findByIdWithInvalidIdShouldReturnEmpty() {
        when(dynamoDbTemplate.load(any(Key.class), eq(ClientEntity.class)))
                .thenReturn(null);

        Optional<ClientEntity> result = clientRepository.findById("invalid");

        assertFalse(result.isPresent());
        verify(dynamoDbTemplate).load(any(Key.class), eq(ClientEntity.class));
    }

    @Test
    void findAllShouldReturnAllClients() {
        ClientEntity client2 = new ClientEntity();
        client2.setId("2");
        client2.setName("Jane Doe");

        List<ClientEntity> clients = List.of(validClientEntity, client2);

        // Mock SdkIterable behavior
        when(sdkIterable.stream()).thenReturn(clients.stream());
        when(pageIterable.items()).thenReturn(sdkIterable);
        when(dynamoDbTemplate.scanAll(ClientEntity.class)).thenReturn(pageIterable);

        List<ClientEntity> result = clientRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(dynamoDbTemplate).scanAll(ClientEntity.class);
    }

    @Test
    void findByCpfWithValidCpfShouldReturnClient() {
        List<ClientEntity> queryResults = List.of(validClientEntity);

        // Mock SdkIterable behavior for query results
        when(sdkIterable.stream()).thenReturn(queryResults.stream());
        when(pageIterable.items()).thenReturn(sdkIterable);
        when(dynamoDbTemplate.query(any(QueryEnhancedRequest.class), eq(ClientEntity.class), eq("cpf-index")))
                .thenReturn(pageIterable);

        Optional<ClientEntity> result = clientRepository.findByCpf("12345678901");

        assertTrue(result.isPresent());
        assertEquals("12345678901", result.get().getCpf());
        verify(dynamoDbTemplate).query(any(QueryEnhancedRequest.class), eq(ClientEntity.class), eq("cpf-index"));
    }

    @Test
    void findByCpfWithInvalidCpfShouldReturnEmpty() {
        // Mock empty SdkIterable
        when(sdkIterable.stream()).thenReturn(Stream.empty());
        when(pageIterable.items()).thenReturn(sdkIterable);
        when(dynamoDbTemplate.query(any(QueryEnhancedRequest.class), eq(ClientEntity.class), eq("cpf-index")))
                .thenReturn(pageIterable);

        Optional<ClientEntity> result = clientRepository.findByCpf("invalid");

        assertFalse(result.isPresent());
        verify(dynamoDbTemplate).query(any(QueryEnhancedRequest.class), eq(ClientEntity.class), eq("cpf-index"));
    }

    @Test
    void deleteShouldCallDynamoDbTemplateDelete() {
        clientRepository.delete(validClientEntity);

        verify(dynamoDbTemplate).delete(validClientEntity);
    }

    @Test
    void deleteByIdWithExistingIdShouldDeleteClient() {
        when(dynamoDbTemplate.load(any(Key.class), eq(ClientEntity.class)))
                .thenReturn(validClientEntity);

        clientRepository.deleteById("1");

        verify(dynamoDbTemplate).load(any(Key.class), eq(ClientEntity.class));
        verify(dynamoDbTemplate).delete(validClientEntity);
    }

    @Test
    void deleteByIdWithNonExistingIdShouldNotDelete() {
        when(dynamoDbTemplate.load(any(Key.class), eq(ClientEntity.class)))
                .thenReturn(null);

        clientRepository.deleteById("invalid");

        verify(dynamoDbTemplate).load(any(Key.class), eq(ClientEntity.class));
        verify(dynamoDbTemplate, never()).delete(any(ClientEntity.class));
    }

    @Test
    void existsByIdWithExistingIdShouldReturnTrue() {
        when(dynamoDbTemplate.load(any(Key.class), eq(ClientEntity.class)))
                .thenReturn(validClientEntity);

        boolean result = clientRepository.existsById("1");

        assertTrue(result);
        verify(dynamoDbTemplate).load(any(Key.class), eq(ClientEntity.class));
    }

    @Test
    void existsByIdWithNonExistingIdShouldReturnFalse() {
        when(dynamoDbTemplate.load(any(Key.class), eq(ClientEntity.class)))
                .thenReturn(null);

        boolean result = clientRepository.existsById("invalid");

        assertFalse(result);
        verify(dynamoDbTemplate).load(any(Key.class), eq(ClientEntity.class));
    }

 */
}


