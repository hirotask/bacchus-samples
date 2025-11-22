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
 */
data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int
)

/**
 * Java版と比較:
 * - Java版: 約80行（フィールド、コンストラクタ、getter/setter、equals、hashCode、toString）
 * - Kotlin版: 約10行（data classで全て自動生成）
 *
 * 使用例:
 * val user = UserDto(id = 1, name = "太郎", email = "taro@example.com", age = 30)
 * val copied = user.copy(age = 31)  // copyメソッドも自動生成
 */
