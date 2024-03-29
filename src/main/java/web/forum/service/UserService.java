package web.forum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import web.forum.model.Authority;
import web.forum.model.User;
import web.forum.repository.AuthorityRepository;
import web.forum.repository.HbmRepository;
import web.forum.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service слой отвечает за работу с репозиториями Пользователи и РолиАвторизации
 */
@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepositoryStore;
    private AuthorityRepository authorityRepositoryStore;
    private HbmRepository hbmRepository;

    public UserService(UserRepository userRepositoryStore,
                       AuthorityRepository authorityRepositoryStore,
                       HbmRepository hbmRepository) {
        this.userRepositoryStore = userRepositoryStore;
        this.authorityRepositoryStore = authorityRepositoryStore;
        this.hbmRepository = hbmRepository;
    }

    /**
     * get all User from DB forum@localhost table users
     *
     * @return List<User> object
     */
    public Collection<User> getAll() {
        List<User> userList = new ArrayList<>();
        var iterAllUser = userRepositoryStore.findAll();
        while (iterAllUser.iterator().hasNext()) {
            userList.add(iterAllUser.iterator().next());
        }
        return userList;
    }

    /**
     * find User by id
     *
     * @param id
     * @return User
     */
    public User findUserById(int id) {
        return hbmRepository.findUserById(id);
    }

    public User findByNameUser(String user) {
        return hbmRepository.findByNameUser(user);
    }

    public Authority findAuthority(Authority authority) {
        var optionalAuthority = authorityRepositoryStore.findById(authority.getId());
        return optionalAuthority.orElse(null);
    }

    /**
     * method  automatically assigns a role to a user (ROLE_USER),
     * save User object in DB table users
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        var auth = authorityRepositoryStore.findById(1);
        user.addAuthorityToUser(auth.get());
        var rsl = userRepositoryStore.save(user);
        return rsl;
    }

    public User findUserByUsername(String userName) {
        return userRepositoryStore.findUserByUsername(userName);
    }

    /**
     * получение имени пользователя из контекста
     * @param user
     * @return
     */
    public String contextIn(Object user) {
        var userArr = user.toString().split(":");
        var arr1 = userArr[2].split(";");
        var name = arr1[0].substring(1);
        return name;
    }
}
