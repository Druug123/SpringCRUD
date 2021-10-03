package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    // К сожалению, если я для внедрения бина entityManager делаю конструктор и внедряю через @Autowired, то на сервере
    // вылетает ошибка при обращении к контроллеру. Если ставлю аннотацию над конструктором @PersistenceContext, то ее
    // подчеркивает IDEA красным шрифтом и просит удалить.
    // все работает лишь при сеттере.

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> index() {
        return entityManager.createQuery("SELECT user from User user").getResultList();
    }


    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User show(int id) {
        return (User) entityManager.createQuery("SELECT user from User user where user.id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void update(User user) {
        entityManager.createQuery(
                        "UPDATE User user SET user.name=:name, user.email=:email, user.age=:age WHERE user.id=:id")
            .setParameter("name", user.getName())
            .setParameter("email", user.getEmail())
            .setParameter("age", user.getAge())
            .setParameter("id", user.getId())
            .executeUpdate();
    }

    @Override
    public void delete(int id) {
        entityManager.createQuery(
            "DELETE FROM User user WHERE user.id=:id")
        .setParameter("id", id)
        .executeUpdate();
    }

}
