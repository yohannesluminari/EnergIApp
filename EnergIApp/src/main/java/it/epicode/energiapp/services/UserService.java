package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.entities.enumEntities.Role;
import it.epicode.energiapp.exceptions.BadRequestException;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.payloads.UserRegisterRequestPayloadDTO;
import it.epicode.energiapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;


    @Autowired private JavaMailSenderImpl javaMailSender;

    private void sendMailRegistrazione(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Utente");
        message.setText("Registrazione Utente avvenuta con successo");
        javaMailSender.send(message);
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public String saveUser(UserRegisterRequestPayloadDTO userDto){

        Optional<User> existingUser = userRepository.findByEmail(userDto.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with this email already exists");
        }
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setRole(Role.USER);
        user.setPassword(bcrypt.encode(userDto.password()));
        sendMailRegistrazione(userDto.email());
        userRepository.save(user);

        return "User with id: " + user.getId() + " correctly saved";
    }

    @Transactional
    public User updateUser(long id, UserRegisterRequestPayloadDTO userDto){
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setFirstName(userDto.firstName());
            user.setLastName(userDto.lastName());
            user.setEmail(userDto.email());
            user.setPassword(bcrypt.encode(userDto.password()));

            return userRepository.save(user);
        }
        else {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }


    @Transactional
    public String deleteUser(Long id) {
        Optional<User> userOptional = getUserById(id);

        if(userOptional.isPresent()){
            userRepository.deleteById(id);
            return "User with id: " + id + " correctly deleted";
        }
        else{
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }


    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new NotFoundException("User with email: " + email + " not found");
        }
    }



}
