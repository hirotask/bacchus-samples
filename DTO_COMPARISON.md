# JavaとKotlinのDTO比較サンプル

このプロジェクトは、JavaとKotlinでDBアクセスアプリを実装し、**DTOの書き方の違い**を明確に比較するためのサンプルです。

## プロジェクト構成

```
bacchus-samples/
├── java-db-sample/          # Java版DBアクセスアプリ
│   └── src/main/java/com/example/java/
│       ├── dto/             # Java版DTO
│       ├── entity/          # Entityクラス
│       ├── repository/      # Repositoryクラス
│       ├── service/         # Serviceクラス
│       └── Main.java        # メインクラス
│
└── kotlin-db-sample/        # Kotlin版DBアクセスアプリ
    └── src/main/kotlin/com/example/kotlin/
        ├── dto/             # Kotlin版DTO (data class)
        ├── entity/          # Entityクラス
        ├── repository/      # Repositoryクラス
        ├── service/         # Serviceクラス
        └── Main.kt          # メインクラス
```

## 実行方法

### Java版の実行
```bash
./gradlew :java-db-sample:run
```

### Kotlin版の実行
```bash
./gradlew :kotlin-db-sample:run
```

## DTOの違い: JavaとKotlinの比較

### 1. コード量の違い

#### Java版のDTO (約80行)
```java
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int age;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // 各フィールドのgetter/setter (約40行)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // ... 他のgetter/setter

    // equals, hashCode, toString (約30行)
    @Override
    public boolean equals(Object o) { /* ... */ }

    @Override
    public int hashCode() { /* ... */ }

    @Override
    public String toString() { /* ... */ }
}
```

#### Kotlin版のDTO (約10行)
```kotlin
data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int
)
```

**結果: Kotlin版は約1/8の行数で同等の機能を実現**

---

### 2. 自動生成されるメソッド

| メソッド | Java | Kotlin data class |
|---------|------|-------------------|
| getter/setter | 手動実装 | 自動生成（プロパティアクセス） |
| equals() | 手動実装 | 自動生成 |
| hashCode() | 手動実装 | 自動生成 |
| toString() | 手動実装 | 自動生成 |
| copy() | なし | 自動生成 ✨ |

---

### 3. インスタンス生成の違い

#### Java版
```java
// 全引数コンストラクタ
UserDto user = new UserDto(1L, "山田太郎", "yamada@example.com", 30);

// デフォルトコンストラクタ + setter
UserDto user = new UserDto();
user.setId(1L);
user.setName("山田太郎");
user.setEmail("yamada@example.com");
user.setAge(30);
```

#### Kotlin版
```kotlin
// 名前付き引数で明確
val user = UserDto(
    id = 1,
    name = "山田太郎",
    email = "yamada@example.com",
    age = 30
)

// デフォルト値があればスキップ可能
val user = UserDto(
    name = "山田太郎",
    email = "yamada@example.com",
    age = 30
)
```

---

### 4. イミュータブル（不変性）の実現

#### Java版
```java
// 全フィールドをfinalにし、setterを削除する必要がある
public class UserDto {
    private final Long id;
    private final String name;
    private final String email;
    private final int age;

    // コンストラクタのみ
    public UserDto(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // getterのみ実装
    public Long getId() { return id; }
    // ...
}
```

#### Kotlin版
```kotlin
// valを使うだけでイミュータブル
data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int
)
```

---

### 5. オブジェクトのコピー

#### Java版
```java
// 手動で全フィールドをコピー
UserDto original = new UserDto(1L, "山田太郎", "yamada@example.com", 30);
UserDto updated = new UserDto(
    original.getId(),
    original.getName(),
    "yamada.new@example.com",  // emailだけ変更
    original.getAge()
);
```

#### Kotlin版
```kotlin
// copy()メソッドで簡単
val original = UserDto(id = 1, name = "山田太郎", email = "yamada@example.com", age = 30)
val updated = original.copy(email = "yamada.new@example.com")  // emailだけ変更
```

---

### 6. Entity⇔DTO変換の違い

#### Java版
```java
// ServiceクラスでDTOとEntityの相互変換
private UserDto convertToDto(User user) {
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    dto.setAge(user.getAge());
    return dto;
}

private User convertToEntity(UserDto dto) {
    User user = new User();
    user.setId(dto.getId());
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setAge(dto.getAge());
    return user;
}
```

#### Kotlin版
```kotlin
// 拡張関数で簡潔に
private fun User.toDto() = UserDto(
    id = id,
    name = name,
    email = email,
    age = age
)

private fun UserDto.toEntity() = User(
    id = id,
    name = name,
    email = email,
    age = age
)
```

---

## 主な違いまとめ

| 項目 | Java | Kotlin |
|-----|------|--------|
| **コード量** | 約80行 | 約10行 |
| **ボイラープレート** | 多い | 非常に少ない |
| **イミュータブル** | 手動実装が必要 | `val`で簡単 |
| **Null安全性** | アノテーションで対応 | 言語レベルでサポート |
| **copy機能** | なし | data classで自動生成 |
| **可読性** | setter連鎖で読みにくい | 名前付き引数で明確 |
| **メンテナンス性** | フィールド追加時に多数の箇所を修正 | data classの定義のみ修正 |

---

## Kotlin data classのメリット

1. **生産性の向上**: 少ないコードで多くの機能を実現
2. **保守性の向上**: 変更箇所が少ない
3. **バグの削減**: equals/hashCodeの実装ミスがない
4. **可読性の向上**: 構造が一目でわかる
5. **Null安全**: NullPointerExceptionのリスク軽減
6. **関数型プログラミング**: イミュータブルなデータ構造として扱いやすい

---

## Kotlin data classの制約

1. 主コンストラクタに最低1つのパラメータが必要
2. すべての主コンストラクタパラメータは`val`または`var`でマークする必要がある
3. data classは`abstract`、`open`、`sealed`、`inner`にできない
4. （Kotlin 1.1以前）data classは他のクラスを継承できない（インターフェースの実装は可能）

---

## 結論

KotlinのData Classを使用することで、**JavaのDTOと比較して圧倒的にコード量が削減され、保守性と可読性が向上**します。特にマイクロサービスやREST APIのような、多数のDTOを扱うアプリケーションでは、その効果が顕著です。

このサンプルを実行して、実際にJavaとKotlinのコードを比較してみてください！
