package com.example.kotlin.entity

/**
 * Kotlin版のEntityクラス
 * DBテーブルと対応するドメインモデル
 *
 * varを使用してミュータブルなプロパティにしている
 * （DBから取得/更新する際に値を変更できるように）
 */
data class User(
    var id: Long? = null,
    var name: String,
    var email: String,
    var age: Int
)
