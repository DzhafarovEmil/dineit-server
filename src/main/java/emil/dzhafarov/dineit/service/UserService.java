package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.User;
import emil.dzhafarov.dineit.model.Role;
import emil.dzhafarov.dineit.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements RestContract<User> {

    @Autowired
    private UserRepository repository;

    public UserDetails findByUsername(String username) {
        User user = repository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Bad credentials");
        }

        return user;
    }

    public List<User> getAll() {
        List<User> users = new LinkedList<>();
        repository.findAll().forEach(users::add);

        return users;
    }

    public User findById(Long id) {
        return repository.findOne(id);
    }

    public boolean isExist(User user) {
        return repository.exists(user.getId());
    }

    public Long create(User user) {
        return repository.save(user).getId();
    }

    public void update(User user) {
        repository.save(user);
    }

    public void deleteById(Long id) {
        repository.delete(id);
    }
}
