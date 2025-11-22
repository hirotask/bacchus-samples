package com.example.java.service;

import com.example.java.dto.UserDto;
import com.example.java.entity.User;
import com.example.java.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Java版のServiceクラス
 * ビジネスロジックとEntity⇔DTOの変換を担当
 */
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto createUser(String name, String email, int age) throws SQLException {
        User user = new User(null, name, email, age);
        User savedUser = repository.save(user);
        return convertToDto(savedUser);
    }

    public Optional<UserDto> getUserById(Long id) throws SQLException {
        return repository.findById(id).map(this::convertToDto);
    }

    public List<UserDto> getAllUsers() throws SQLException {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void updateUser(UserDto userDto) throws SQLException {
        User user = convertToEntity(userDto);
        repository.update(user);
    }

    public void deleteUser(Long id) throws SQLException {
        repository.deleteById(id);
    }

    /**
     * EntityをDTOに変換
     * Javaでは手動で各フィールドを設定する必要がある
     */
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        return dto;
    }

    /**
     * DTOをEntityに変換
     * Javaでは手動で各フィールドを設定する必要がある
     */
    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        return user;
    }
}
