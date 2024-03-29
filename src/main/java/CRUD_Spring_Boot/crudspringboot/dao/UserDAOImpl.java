package CRUD_Spring_Boot.crudspringboot.dao;

import CRUD_Spring_Boot.crudspringboot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(getUser(id));
    }

    @Override
    public User loadUserByUsername(String name) {
        return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList().stream().findAny().orElse(null);
    }

}
