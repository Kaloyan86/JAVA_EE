package app.repository;

import app.domain.entities.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    void update(User user);

    User findById(String id);

    User findByUsernameAndPassword(String username, String password);

    List<User> findAll();
}
