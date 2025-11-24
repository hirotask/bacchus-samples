package com.example.kotlin.entity

/**
 * Kotlin版のEntityクラス
 * DBテーブルと対応するドメインモデル
 *
 * varを使用してミュータブルなプロパティにしている
 * （DBから取得/更新する際に値を変更できるように）
 *
 * Entityの特徴:
 * - idは必須（デフォルト値なし） - DBから取得したデータを表すため
 * - nullableなフィールドはデフォルト値なし - 明示的にnullを渡す必要がある
 *
 * Null安全性:
 * - nickname, phoneNumber, addressは String? 型（nullableな型）
 * - 型システムがnullを明示的に扱うため、コンパイル時にnullチェックが強制される
 */
data class User(
    var id: Long?,
    var name: String,
    var email: String,
    var age: Int,
    var nickname: String?,      // ニックネーム（任意）
    var phoneNumber: String?,   // 電話番号（任意）
    var address: String?        // 住所（任意）
)
