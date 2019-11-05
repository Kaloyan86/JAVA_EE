package app.service;

import app.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void register(UserServiceModel user);

    void addFriend(UserServiceModel userServiceModel);

    UserServiceModel getById(String id);

    UserServiceModel getByUsernameAndPassword(String username, String password);

    List<UserServiceModel> getAll();

    void remove(String userId, String friendId);
}
