package com.example.java.repository;

import com.example.java.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Java版のRepositoryクラス
 * DBアクセスロジックを実装
 */
public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL, " +
                "age INT NOT NULL" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public User save(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        }
        return user;
    }

    public Optional<User> findById(Long id) throws SQLException {
        String sql = "SELECT id, name, email, age FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        return Optional.empty();
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, age FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.setLong(4, user.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        );
    }
}
