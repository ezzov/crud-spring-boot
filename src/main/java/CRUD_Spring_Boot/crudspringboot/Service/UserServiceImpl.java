package CRUD_Spring_Boot.crudspringboot.Service;

import CRUD_Spring_Boot.crudspringboot.DAO.UserDAO;
import CRUD_Spring_Boot.crudspringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private RoleService roleService;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public void save(User user, String[] roles) {
        user.setRoles(roleService.makeSet(roles));
        userDAO.save(user);
    }

    @Override
    @Transactional
    public void update(User user, String[] roles) {
        user.setRoles(roleService.makeSet(roles));
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDAO.delete(id);
    }

    @Override
    @Transactional
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.loadUserByUsername(s);
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
