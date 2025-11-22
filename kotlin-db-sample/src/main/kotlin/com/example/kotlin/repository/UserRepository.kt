package com.example.kotlin.repository

import com.example.kotlin.entity.User
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

/**
 * Kotlin版のRepositoryクラス
 * DBアクセスロジックを実装
 *
 * Kotlinの特徴:
 * - use関数でリソースの自動クローズ
 * - 拡張関数でResultSetからUserへの変換を簡潔に
 * - letやrunなどのスコープ関数で処理をチェーン
 * - nullableな型とエルビス演算子でNull安全
 */
class UserRepository(private val connection: Connection) {

    fun createTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                age INT NOT NULL
            )
        """.trimIndent()
        connection.createStatement().use { it.execute(sql) }
    }

    fun save(user: User): User {
        val sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)"
        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { pstmt ->
            pstmt.setString(1, user.name)
            pstmt.setString(2, user.email)
            pstmt.setInt(3, user.age)
            pstmt.executeUpdate()

            pstmt.generatedKeys.use { rs ->
                if (rs.next()) {
                    user.id = rs.getLong(1)
                }
            }
        }
        return user
    }

    fun findById(id: Long): User? {
        val sql = "SELECT id, name, email, age FROM users WHERE id = ?"
        return connection.prepareStatement(sql).use { pstmt ->
            pstmt.setLong(1, id)
            pstmt.executeQuery().use { rs ->
                if (rs.next()) rs.toUser() else null
            }
        }
    }

    fun findAll(): List<User> {
        val sql = "SELECT id, name, email, age FROM users"
        return connection.createStatement().use { stmt ->
            stmt.executeQuery(sql).use { rs ->
                buildList {
                    while (rs.next()) {
                        add(rs.toUser())
                    }
                }
            }
        }
    }

    fun update(user: User) {
        val sql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?"
        connection.prepareStatement(sql).use { pstmt ->
            pstmt.setString(1, user.name)
            pstmt.setString(2, user.email)
            pstmt.setInt(3, user.age)
            pstmt.setLong(4, user.id ?: throw IllegalArgumentException("User ID must not be null"))
            pstmt.executeUpdate()
        }
    }

    fun deleteById(id: Long) {
        val sql = "DELETE FROM users WHERE id = ?"
        connection.prepareStatement(sql).use { pstmt ->
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        }
    }

    /**
     * 拡張関数: ResultSetからUserオブジェクトへの変換
     * Java版より簡潔に書ける
     */
    private fun ResultSet.toUser() = User(
        id = getLong("id"),
        name = getString("name"),
        email = getString("email"),
        age = getInt("age")
    )
}
