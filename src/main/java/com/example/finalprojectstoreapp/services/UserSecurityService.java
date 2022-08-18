package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.SignInDto;
import com.example.finalprojectstoreapp.dtos.SignInResponseDto;
import com.example.finalprojectstoreapp.dtos.SignUpDto;
import com.example.finalprojectstoreapp.dtos.SignUpResponseDto;
import com.example.finalprojectstoreapp.exceptions.AuthenticationFailException;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.models.AuthenticationToken;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public SignUpResponseDto signUp(SignUpDto signupDto) {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("user already present");
        }
        String encryptedPassword;
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }

        User user = User.builder()
                .username(signupDto.getUsername())
                .email(signupDto.getEmail())
                .password(encryptedPassword).build();

        userRepository.save(user);

        AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);

        return new SignUpResponseDto("success", "user created succesfully");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase(Locale.ROOT);
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User is not valid");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken authenticationToken = authenticationService.getToken(user);

        if (Objects.isNull(authenticationToken)) {
            throw new CustomException("Token is not present!");
        }

        return new SignInResponseDto("sucess", authenticationToken.getToken());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User is not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}