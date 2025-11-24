# bacchus-samples

## プロジェクト構成

- `java-db-sample`: Java版のDBアクセスアプリケーション
- `kotlin-db-sample`: Kotlin版のDBアクセスアプリケーション
- `buildSrc`: ビルド設定の共通化

## 実行方法

### Java版のDBアクセスアプリを実行
```bash
./gradlew :java-db-sample:run
```

### Kotlin版のDBアクセスアプリを実行
```bash
./gradlew :kotlin-db-sample:run
```

### ビルド
```bash
# 全モジュールのビルド
./gradlew build

# 特定モジュールのビルド
./gradlew :java-db-sample:build
./gradlew :kotlin-db-sample:build
```

### クリーン
```bash
./gradlew clean
```

## JavaとKotlinの比較

このプロジェクトは、JavaとKotlinでDBアクセスアプリを実装し、以下の違いを明確に比較するためのサンプルです：

1. **DTOの書き方の違い** - [DTO_COMPARISON.md](./DTO_COMPARISON.md)
2. **Null安全性の違い** - [NULL_SAFETY_COMPARISON.md](./NULL_SAFETY_COMPARISON.md)

### 主な違いのハイライト

| 項目 | Java | Kotlin |
|-----|------|--------|
| **コード量** | 約130行 | 約15行 |
| **ボイラープレート** | 多い | 非常に少ない |
| **イミュータブル** | 手動実装が必要 | `val`で簡単 |
| **Null安全性** | 型では区別できない | **言語レベルでサポート** |
| **NullPointerException** | 実行時に発生するリスク | **コンパイル時に防げる** |
| **copy機能** | なし | data classで自動生成 |

## Gradleについて

This project uses [Gradle](https://gradle.org/).

Note the usage of the Gradle Wrapper (`./gradlew`).
This is the suggested way to use Gradle in production projects.

[Learn more about the Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

[Learn more about Gradle tasks](https://docs.gradle.org/current/userguide/command_line_interface.html#common_tasks).

This project uses a version catalog (see `gradle/libs.versions.toml`) to declare and version dependencies
and both a build cache and a configuration cache (see `gradle.properties`).