package com.lera.catalog.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIntegrationTest {

    protected static final PostgreSQLContainer<?> PSQL_CONTAINER;
    protected static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(
            "apache/kafka:3.8.0")
    );

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    // Рандомный порт из спрингбуттест (порт поднятого тестового приложения - генерится рандомно)
    @LocalServerPort
    public int serverPort;

    // Поднимает тестовый контейнер бд
    static {
        PSQL_CONTAINER = new PostgreSQLContainer<>("postgres:16");
        PSQL_CONTAINER.start();
        KAFKA_CONTAINER.start();
    }

    // Проставляет порт, пароль, username поднятого тестового контейнера бд в конфиги тестового приложения
    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", PSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", PSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", PSQL_CONTAINER::getPassword);
        registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);  // возвращает localhost:PORT - переписывает локхост из application.yaml
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.port = serverPort;
    }

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("truncate table good cascade;");
        jdbcTemplate.execute("ALTER SEQUENCE good_id_seq RESTART WITH 1;");
    }
}
