package web.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> index() {
        List<User> userList = entityManager.createQuery("SELECT user from User user").getResultList();
        return userList;
    }


    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User show(int id) {
        Query query = entityManager.createQuery("SELECT user from User user where user.id=:id");
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public void update(int id, User user) {
        Query query = entityManager.createQuery(
                "UPDATE User user SET user.name=:name, user.email=:email, user.age=:age WHERE user.id=:id");
        query.setParameter("name", user.getName());
        query.setParameter("email", user.getEmail());
        query.setParameter("age", user.getAge());
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void delete(int id) {
        Query query = entityManager.createQuery(
                "DELETE FROM User user WHERE user.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
