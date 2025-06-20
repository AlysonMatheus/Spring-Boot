package br.com.Alyson.integrationtests.testcontainers;


import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {


    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");


        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();// esta interando nos parametros e executa esse join
        }

        private static Map<String, String> createConnectionConfigutation() {
            return Map.of(

                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();// esta obtendo as variaveis de ambiente do applicationContext
            MapPropertySource testcontainers = new MapPropertySource("testContainers",
                    (Map) createConnectionConfigutation());
            environment.getPropertySources().addFirst(testcontainers);

        }
    }
}
