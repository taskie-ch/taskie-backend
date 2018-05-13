package com.taskie.db;

import com.taskie.api.FlatService;
import com.taskie.core.*;
import org.eclipse.jetty.util.security.Credential;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Data access object for {@link Flat} storing data in memory.
 * <p>
 * Use {@link InMemoryFlatDao#create()} to create a new DAO.
 */
public class InMemoryFlatDao implements FlatService {

    private final Map<Long, Flat> flats = new HashMap<>();

    private InMemoryFlatDao(List<Flat> flats) {
        flats.forEach(this::save);
    }

    /**
     * Creates a new instance of {@link InMemoryFlatDao}
     * and initialises it with "Taskie Home" flat (for the prototype).
     *
     * @return initialised in-memory flat DAO
     */
    public static InMemoryFlatDao create() {
        Flat flat = Flat.create(1, "Taskie Home");
        Set<Flatmate> users = new HashSet<>(3);
        users.add(Flatmate.create(principal("Tom"), new Email("tom@students.zhaw.ch"), new Score(5)));
        users.add(Flatmate.create(principal("Jane"), new Email("jane@students.zhaw.ch"), new Score(6)));
        users.add(Flatmate.create(principal("Joe"), new Email("joe@students.zhaw.ch"), new Score(4)));
        flat.addAllFlatmates(users);
        return new InMemoryFlatDao(Collections.singletonList(flat));
    }

    /**
     * Generates a unique id for a user name using MD5 hashing
     *
     * @param name user name
     * @return MD5 hash of user name
     */
    public static String generateUserId(String name) {
        return Credential.MD5.digest(name).substring(4);
    }

    private static UserPrincipal principal(String name) {
        return new UserPrincipal(generateUserId(name), name);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Flat findById(long id) {
        return flats.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Flatmate> findUsers(long flatId, List<String> ids) {

        Flat flat = findById(flatId);
        final List<Flatmate> users = new ArrayList<>();

        for (String id : ids) {
            flat.findUser(id).ifPresent(users::add);
        }
        return users;
    }

    private void save(Flat flat) {
        flats.put(flat.getId(), flat);
    }
}
