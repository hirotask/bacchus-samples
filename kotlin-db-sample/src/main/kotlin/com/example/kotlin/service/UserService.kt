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

    fun createUser(name: String, email: String, age: Int, nickname: String? = null, phoneNumber: String? = null, address: String? = null): UserDto {
        val user = User(
            id = null,  // 新規作成時はidをnullに設定
            name = name,
            email = email,
            age = age,
            nickname = nickname,
            phoneNumber = phoneNumber,
            address = address
        )
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
     *
     * Null安全性:
     * - nullableなフィールドもそのまま代入できる
     * - 型システムがnull安全を保証するため、明示的なnullチェック不要
     */
    private fun User.toDto() = UserDto(
        id = id,
        name = name,
        email = email,
        age = age,
        nickname = nickname,          // String? 型をそのまま代入
        phoneNumber = phoneNumber,    // String? 型をそのまま代入
        address = address             // String? 型をそのまま代入
    )

    /**
     * 拡張関数: DTOをEntityに変換
     * Java版より簡潔（setterの連続呼び出しが不要）
     *
     * Null安全性:
     * - nullableなフィールドもそのまま代入できる
     * - 型システムがnull安全を保証するため、明示的なnullチェック不要
     */
    private fun UserDto.toEntity() = User(
        id = id,
        name = name,
        email = email,
        age = age,
        nickname = nickname,          // String? 型をそのまま代入
        phoneNumber = phoneNumber,    // String? 型をそのまま代入
        address = address             // String? 型をそのまま代入
    )

    /**
     * ユーザーの表示名を取得する例
     * Kotlinでは Elvis演算子 (?:) を使って1行で書ける
     */
    fun getDisplayName(user: UserDto): String {
        // nicknameがnullの場合はnameを返す
        return user.nickname ?: user.name
    }

    /**
     * 電話番号の文字数を取得する例
     * Kotlinでは Safe call演算子 (?.) と Elvis演算子 (?:) で安全に処理
     */
    fun getPhoneNumberLength(user: UserDto): Int {
        // phoneNumberがnullの場合は0を返す
        return user.phoneNumber?.length ?: 0
    }

    /**
     * 住所が登録されているか確認する例
     * Kotlinでは Safe call演算子 (?.) とisEmpty()を組み合わせて簡潔に
     */
    fun hasAddress(user: UserDto): Boolean {
        // addressがnullでなく、かつ空文字でない場合にtrue
        return !user.address.isNullOrEmpty()
    }
}
