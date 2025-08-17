package ru.john;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOTest {

    @Container

    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17-alpine")
                    .withDatabaseName("userservicedb2")
                    .withUsername("postgres")
                    .withPassword("postgres");

    private UserDAO userDAO;

    @BeforeAll
    void init() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop");

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtil.setSessionFactory(sessionFactory);
        userDAO = new UserDAO();
    }

    @Test
    void testSave() {
        User user = new User("Пётр", "petia@example.com", 25);

        userDAO.save(user);

        assertNotNull(user.getId(), "ID устанавливается после сохранения");

        User fromDb = userDAO.getById(user.getId());
        assertNotNull(fromDb, "Пользователь сохранён в базе");
        assertEquals("Пётр", fromDb.getName());
        assertEquals("petia@example.com", fromDb.getEmail());
        assertEquals(25, fromDb.getAge());
    }

    @Test
    void testGetById() {

        User user = new User("Пётр", "petia@example.com", 25);

        userDAO.save(user);

        User fromDb = userDAO.getById(user.getId());

        assertNotNull(fromDb, "Пользователь есть в базе");
        assertEquals(user.getId(), fromDb.getId(), "ID должен совпадать");
        assertEquals("Пётр", fromDb.getName());
        assertEquals("petia@example.com", fromDb.getEmail());
        assertEquals(25, fromDb.getAge());
    }

    @Test
    void testGetAll() {

        User user = new User("Пётр", "petia@example.com", 25);
        userDAO.save(user);

        List<User> users = userDAO.getAll();

        assertNotNull(users, "Список пользователей не null");
        assertFalse(users.isEmpty(), "Список пользователей не пуст");

        User fromDb = users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(fromDb, "Сохраненный пользователь в списке всех пользователей");
        assertEquals("Пётр", fromDb.getName());
        assertEquals("petia@example.com", fromDb.getEmail());
        assertEquals(25, fromDb.getAge());
    }

    @Test
    void testUpdate() {

        User user = new User("Пётр", "petia@example.com", 25);
        userDAO.save(user);

        user.setName("Пётр Иванов");
        user.setEmail("petrov@example.com");
        user.setAge(26);

        userDAO.update(user);

        User fromDb = userDAO.getById(user.getId());

        assertNotNull(fromDb, "Пользователь обновлён");
        assertEquals("Пётр Иванов", fromDb.getName());
        assertEquals("petrov@example.com", fromDb.getEmail());
        assertEquals(26, fromDb.getAge());
    }

    @Test
    void testDelete() {

        User user = new User("Пётр", "petia@example.com", 25);
        userDAO.save(user);

        userDAO.delete(user);

        User fromDb = userDAO.getById(user.getId());

        assertNull(fromDb, "Пользователь удалён");
    }
}