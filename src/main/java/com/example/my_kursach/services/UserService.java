package com.example.my_kursach.services;

import com.example.my_kursach.models.Roles;
import com.example.my_kursach.models.User;
import com.example.my_kursach.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser (User user)
    {
        if(userRepo.findByLogin(user.getLogin())!=null) return false;
        user.setActive(true);
        user.getRoles().add(Roles.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }
    public boolean createSupplier (User user)
    {
        if(userRepo.findByLogin(user.getLogin())!=null) return false;
        user.setActive(true);
        user.getRoles().add(Roles.SUPPLIER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }
    public void userSavenewRole(User user, String selectedRole) {

        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.valueOf(selectedRole));
        user.setRoles(roles);
        userRepo.save(user);
    }

    public User findById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);


    }

    public void updateResetPasswordToken(String token, String email)
    {
        User user=userRepo.findByEmail(email);
        if(user!=null)
        {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        }
        else {
            System.out.println("Такого нет!");/// сделать обработку
        }

    }
    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        userRepo.save(customer);
    }

}
