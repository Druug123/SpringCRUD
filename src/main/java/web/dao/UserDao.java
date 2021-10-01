package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    List<User> index();

    void create(User user);

    User show(int id);

    void update(int id, User user);

    void delete(int id);
}
