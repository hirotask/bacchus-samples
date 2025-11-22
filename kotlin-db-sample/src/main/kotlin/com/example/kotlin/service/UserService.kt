package com.example.kotlin.service

import com.example.kotlin.dto.UserDto
import com.example.kotlin.entity.User
import com.example.kotlin.repository.UserRepository

/**
 * Kotlin版のServiceクラス
 * ビジネスロジックとEntity⇔DTOの変換を担当
 *
 * Kotlinの特徴:
 * - 拡張関数で変換ロジックを簡潔に記述
 * - map関数でリストの変換が簡単
 * - 名前付き引数で可読性が高い
 */
class UserService(private val repository: UserRepository) {

    fun createUser(name: String, email: String, age: Int): UserDto {
        val user = User(name = name, email = email, age = age)
        return repository.save(user).toDto()
    }

    fun getUserById(id: Long): UserDto? {
        return repository.findById(id)?.toDto()
    }

    fun getAllUsers(): List<UserDto> {
        return repository.findAll().map { it.toDto() }
    }

    fun updateUser(userDto: UserDto) {
        val user = userDto.toEntity()
        repository.update(user)
    }

    fun deleteUser(id: Long) {
        repository.deleteById(id)
    }

    /**
     * 拡張関数: EntityをDTOに変換
     * Java版より簡潔（setterの連続呼び出しが不要）
     */
    private fun User.toDto() = UserDto(
        id = id,
        name = name,
        email = email,
        age = age
    )

    /**
     * 拡張関数: DTOをEntityに変換
     * Java版より簡潔（setterの連続呼び出しが不要）
     */
    private fun UserDto.toEntity() = User(
        id = id,
        name = name,
        email = email,
        age = age
    )
}
