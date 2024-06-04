package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.payloads.UserRegisterResponsePayloadDTO;
import it.epicode.energiapp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    // loadByUsername(String email) overriding

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    // GET all

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // GET id
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    // POST id
    @Transactional
    public User createUser(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepository.save(user);
    }


    // PUT
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setAvatar(userDetails.getAvatar());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    // DELETE
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // findByEmail(String email)
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Item with " + email + " not found."));
    }

}