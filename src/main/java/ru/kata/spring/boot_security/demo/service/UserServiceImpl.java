package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepositories userRepositories;

    @Autowired
    public UserServiceImpl(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> showAllUser() {
        return userRepositories.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        Optional<User> user = userRepositories.findById(id);
        return user.orElse(new User());
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User user) {
        Optional<User> userUpdate = userRepositories.findById(id);
        if (userUpdate.isPresent()) {
            User userFromRepo = userUpdate.get();
            userFromRepo.setId(id);
            userFromRepo.setFirstName(user.getFirstName());
            userFromRepo.setLastName(user.getLastName());
            userFromRepo.setAge(user.getAge());
            userFromRepo.setEmail(user.getEmail());
            userFromRepo.setRoles(user.getRoles());
            userFromRepo.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepositories.save(userFromRepo);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepositories.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepositories.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepositories.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
