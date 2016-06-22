package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    public final List<User> USER_LIST = Arrays.asList(
            new User(counter.incrementAndGet(), "Vasia", "vasia@gmail.com", "123", Role.ROLE_USER, Role.values()),
            new User(counter.incrementAndGet(), "Admin", "admin@gmail.com", "123", Role.ROLE_ADMIN, Role.values())
    );

    {
        USER_LIST.forEach(this::save);
    }



    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id, repository.get(id));
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.containsKey(id) ? repository.get(id) : null;
    }

    @Override
    public Collection<User> getAll() {
        LOG.info("getAll");
        return repository.values()
                .stream()
                .sorted((n1, n2) -> n1.getName().compareToIgnoreCase(n2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findAny().orElse(null);
    }
}
