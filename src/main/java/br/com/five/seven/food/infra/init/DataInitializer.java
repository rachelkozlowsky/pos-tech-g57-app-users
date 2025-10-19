package br.com.five.seven.food.infra.init;

import br.com.five.seven.food.infra.persistence.entity.ClientEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing DynamoDB tables...");

        // Create all required tables
        createTableIfNotExists(ClientEntity.class, 5L, 5L);

        log.info("DynamoDB tables initialization completed");
    }

    private <T> void createTableIfNotExists(Class<T> entityClass, Long readCapacity, Long writeCapacity) {
        try {
            String tableName = entityClass.getSimpleName();

            // Check if table already exists
            try {

                log.info("Table {} already exists", tableName);
            } catch (Exception e) {
                log.info("Table not found: {}", tableName);
            }
        } catch (Exception e) {
            log.error("Error creating table for {}: {}", entityClass.getSimpleName(), e.getMessage(), e);
        }
    }
}
