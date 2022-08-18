package com.example.finalprojectstoreapp.mappers;

import com.example.finalprojectstoreapp.dtos.SignUpDto;
import com.example.finalprojectstoreapp.models.User;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public User convertToEntity(SignUpDto signupDto) {
        return modelMapper.map(signupDto, User.class);
    }

    public SignUpDto convertToDTO(User user) {
        return modelMapper.map(user, SignUpDto.class);
    }
}