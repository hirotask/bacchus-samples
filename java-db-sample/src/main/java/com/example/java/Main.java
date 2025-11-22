package com.example.java;

import com.example.java.dto.UserDto;
import com.example.java.repository.UserRepository;
import com.example.java.service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Java版のDBアクセスアプリのメインクラス
 */
public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:h2:mem:testdb";
        String username = "sa";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("=== Java版 DBアクセスサンプル ===\n");

            UserRepository repository = new UserRepository(connection);
            UserService service = new UserService(repository);

            repository.createTable();
            System.out.println("テーブルを作成しました\n");

            // ユーザーを作成
            System.out.println("--- ユーザーの作成 ---");
            UserDto user1 = service.createUser("山田太郎", "yamada@example.com", 30);
            System.out.println("作成: " + user1);

            UserDto user2 = service.createUser("佐藤花子", "sato@example.com", 25);
            System.out.println("作成: " + user2);

            UserDto user3 = service.createUser("鈴木一郎", "suzuki@example.com", 35);
            System.out.println("作成: " + user3 + "\n");

            // 全ユーザーを取得
            System.out.println("--- 全ユーザーの取得 ---");
            List<UserDto> allUsers = service.getAllUsers();
            allUsers.forEach(System.out::println);
            System.out.println();

            // IDでユーザーを取得
            System.out.println("--- IDでユーザーを取得 (ID=2) ---");
            service.getUserById(2L).ifPresent(user -> {
                System.out.println("取得: " + user);
            });
            System.out.println();

            // ユーザーを更新
            System.out.println("--- ユーザーの更新 (ID=2) ---");
            UserDto updateDto = new UserDto(2L, "佐藤花子", "sato.updated@example.com", 26);
            service.updateUser(updateDto);
            service.getUserById(2L).ifPresent(user -> {
                System.out.println("更新後: " + user);
            });
            System.out.println();

            // ユーザーを削除
            System.out.println("--- ユーザーの削除 (ID=1) ---");
            service.deleteUser(1L);
            System.out.println("ID=1のユーザーを削除しました\n");

            // 削除後の全ユーザー
            System.out.println("--- 削除後の全ユーザー ---");
            List<UserDto> remainingUsers = service.getAllUsers();
            remainingUsers.forEach(System.out::println);

            System.out.println("\n=== Java版の特徴 ===");
            System.out.println("1. DTOはPOJOスタイル（getter/setter）");
            System.out.println("2. equals/hashCode/toStringを手動実装");
            System.out.println("3. Entity⇔DTO変換は手動でフィールドをコピー");
            System.out.println("4. ボイラープレートコードが多い");

        } catch (SQLException e) {
            System.err.println("データベースエラー: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
