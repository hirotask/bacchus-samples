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

## JavaとKotlinのDTO比較

詳細な比較については [DTO_COMPARISON.md](./DTO_COMPARISON.md) をご覧ください。

### 主な違いのハイライト

| 項目 | Java | Kotlin |
|-----|------|--------|
| **コード量** | 約80行 | 約10行 |
| **ボイラープレート** | 多い | 非常に少ない |
| **イミュータブル** | 手動実装が必要 | `val`で簡単 |
| **copy機能** | なし | data classで自動生成 |

**Java版のDTO例:**
```java
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int age;

    // コンストラクタ、getter/setter、equals、hashCode、toStringが必要
    // 合計約80行のコード
}
```

**Kotlin版のDTO例:**
```kotlin
data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int
)
// わずか6行で同等の機能を実現
```

---

## Gradleについて

This project uses [Gradle](https://gradle.org/).

Note the usage of the Gradle Wrapper (`./gradlew`).
This is the suggested way to use Gradle in production projects.

[Learn more about the Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

[Learn more about Gradle tasks](https://docs.gradle.org/current/userguide/command_line_interface.html#common_tasks).

This project uses a version catalog (see `gradle/libs.versions.toml`) to declare and version dependencies
and both a build cache and a configuration cache (see `gradle.properties`).