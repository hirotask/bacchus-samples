package com.example.kotlin

import com.example.kotlin.dto.UserDto
import com.example.kotlin.repository.UserRepository
import com.example.kotlin.service.UserService
import java.sql.DriverManager

/**
 * Kotlin版のDBアクセスアプリのメインクラス
 */
fun main() {
    val jdbcUrl = "jdbc:h2:mem:testdb"
    val username = "sa"
    val password = ""

    DriverManager.getConnection(jdbcUrl, username, password).use { connection ->
        println("=== Kotlin版 DBアクセスサンプル ===\n")

        val repository = UserRepository(connection)
        val service = UserService(repository)

        repository.createTable()
        println("テーブルを作成しました\n")

        // ユーザーを作成
        println("--- ユーザーの作成 ---")
        val user1 = service.createUser("山田太郎", "yamada@example.com", 30)
        println("作成: $user1")

        val user2 = service.createUser("佐藤花子", "sato@example.com", 25)
        println("作成: $user2")

        val user3 = service.createUser("鈴木一郎", "suzuki@example.com", 35)
        println("作成: $user3\n")

        // 全ユーザーを取得
        println("--- 全ユーザーの取得 ---")
        val allUsers = service.getAllUsers()
        allUsers.forEach { println(it) }
        println()

        // IDでユーザーを取得
        println("--- IDでユーザーを取得 (ID=2) ---")
        service.getUserById(2)?.let { user ->
            println("取得: $user")
        }
        println()

        // ユーザーを更新
        println("--- ユーザーの更新 (ID=2) ---")
        val updateDto = UserDto(id = 2, name = "佐藤花子", email = "sato.updated@example.com", age = 26)
        service.updateUser(updateDto)
        service.getUserById(2)?.let { user ->
            println("更新後: $user")
        }
        println()

        // ユーザーを削除
        println("--- ユーザーの削除 (ID=1) ---")
        service.deleteUser(1)
        println("ID=1のユーザーを削除しました\n")

        // 削除後の全ユーザー
        println("--- 削除後の全ユーザー ---")
        val remainingUsers = service.getAllUsers()
        remainingUsers.forEach { println(it) }

        println("\n=== Kotlin版の特徴 ===")
        println("1. DTOはdata classで1行定義")
        println("2. equals/hashCode/toString/copyが自動生成")
        println("3. Entity⇔DTO変換は拡張関数で簡潔に")
        println("4. ボイラープレートコードが非常に少ない")
        println("5. Null安全性が言語レベルでサポート")
        println("6. 名前付き引数で可読性が高い")
    }
}
