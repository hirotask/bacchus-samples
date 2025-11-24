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
        User user = new User(null, name, email, age, null, null, null);
        User savedUser = repository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto createUser(String name, String email, int age, String nickname, String phoneNumber, String address) throws SQLException {
        User user = new User(null, name, email, age, nickname, phoneNumber, address);
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
     *
     * Nullable フィールドの処理:
     * - nullチェックを忘れるとNullPointerExceptionのリスク
     * - 各フィールドごとに冗長なnullチェックが必要
     */
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());

        // Nullable フィールドの処理（冗長なコード）
        dto.setNickname(user.getNickname());  // nullの可能性があるが、そのまま代入
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());

        return dto;
    }

    /**
     * DTOをEntityに変換
     * Javaでは手動で各フィールドを設定する必要がある
     *
     * Nullable フィールドの処理:
     * - nullチェックを忘れるとNullPointerExceptionのリスク
     * - 各フィールドごとに冗長なnullチェックが必要
     */
    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());

        // Nullable フィールドの処理（冗長なコード）
        user.setNickname(dto.getNickname());  // nullの可能性があるが、そのまま代入
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());

        return user;
    }

    /**
     * ユーザーの表示名を取得する例
     * Javaでは冗長なnullチェックが必要
     */
    public String getDisplayName(UserDto user) {
        // nicknameがnullでない場合はnicknameを、nullの場合はnameを返す
        if (user.getNickname() != null) {
            return user.getNickname();
        } else {
            return user.getName();
        }
    }

    /**
     * 電話番号の文字数を取得する例
     * Javaでは冗長なnullチェックが必要（nullチェックを忘れるとNPEが発生）
     */
    public int getPhoneNumberLength(UserDto user) {
        if (user.getPhoneNumber() != null) {
            return user.getPhoneNumber().length();
        } else {
            return 0;
        }
    }

    /**
     * 住所が登録されているか確認する例
     * Javaでは冗長なnullチェックと空文字チェックが必要
     */
    public boolean hasAddress(UserDto user) {
        return user.getAddress() != null && !user.getAddress().isEmpty();
    }
}
