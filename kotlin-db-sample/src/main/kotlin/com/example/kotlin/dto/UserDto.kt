package com.example.kotlin.dto

/**
 * Kotlin版のDTO（Data Transfer Object）
 *
 * 特徴:
 * - data classを使用することで、equals(), hashCode(), toString(), copy()が自動生成される
 * - getter/setterは不要（プロパティアクセス）
 * - 1行で定義できる（ボイラープレートコードが激減）
 * - valを使うことで簡単にイミュータブルにできる
 * - デフォルト値を設定できる
 * - 名前付き引数でインスタンス生成が明確
 *
 * Null安全性:
 * - nickname, phoneNumber, addressは String? 型（nullableな型）
 * - 型システムレベルでnull安全が保証される
 * - Safe call演算子(?.)、Elvis演算子(?:)で安全にnullを扱える
 */
data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int,
    val nickname: String? = null,      // ニックネーム（任意）
    val phoneNumber: String? = null,   // 電話番号（任意）
    val address: String? = null        // 住所（任意）
)

/**
 * Java版と比較:
 * - Java版: 約130行（フィールド、コンストラクタ、getter/setter、equals、hashCode、toString）
 * - Kotlin版: 約15行（data classで全て自動生成）
 *
 * 使用例:
 * val user = UserDto(
 *     id = 1,
 *     name = "太郎",
 *     email = "taro@example.com",
 *     age = 30,
 *     nickname = "タロちゃん"  // nullableなフィールド
 * )
 * val copied = user.copy(age = 31)  // copyメソッドも自動生成
 *
 * Null安全な処理例:
 * val displayName = user.nickname ?: user.name  // nicknameがnullならnameを使用
 * val phoneLength = user.phoneNumber?.length ?: 0  // safe callでNullPointerExceptionを回避
 */
