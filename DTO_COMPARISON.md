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

### 1. コード量の違い（nullableフィールドを含む）

#### Java版のDTO (約130行)
```java
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    // Nullable fields (DBでNULLが許可されているカラム)
    private String nickname;      // ニックネーム（任意）
    private String phoneNumber;   // 電話番号（任意）
    private String address;       // 住所（任意）

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, int age, String nickname, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // 各フィールドのgetter/setter (約70行)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    // ... 他のgetter/setter

    // equals, hashCode, toString (約40行)
    @Override
    public boolean equals(Object o) { /* ... */ }

    @Override
    public int hashCode() { /* ... */ }

    @Override
    public String toString() { /* ... */ }
}
```

**問題点:**
- nullableなフィールド（nickname, phoneNumber, address）も型では区別できない
- 使用時に常にnullチェックが必要（忘れるとNullPointerException）

#### Kotlin版のDTO (約15行)
```kotlin
data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int,
    val nickname: String? = null,      // ニックネーム（任意）
    val phoneNumber: String? = null,   // 電話番号（任意）
    val address: String? = null        // 住所（任意）
)
```

**利点:**
- `String?` 型でnullableであることが明示されている
- 型システムがnull安全を保証するため、コンパイル時にnullチェックが強制される
- Safe call演算子（`?.`）、Elvis演算子（`?:`）で簡潔にnull処理が可能

**結果: Kotlin版は約1/9の行数で同等の機能を実現**

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

### 7. **NEW:** Null安全性の違い（DBのnull値の扱い）

KotlinのNull安全性により、DBにnullが入っていた場合のDTOの扱いがJavaよりも圧倒的に楽になります。

#### Java版：冗長なnullチェックが必要

```java
// ユーザーの表示名を取得
public String getDisplayName(UserDto user) {
    if (user.getNickname() != null) {
        return user.getNickname();
    } else {
        return user.getName();
    }
}

// 電話番号の文字数を取得
public int getPhoneNumberLength(UserDto user) {
    if (user.getPhoneNumber() != null) {
        return user.getPhoneNumber().length();
    } else {
        return 0;
    }
}

// 住所が登録されているか確認
public boolean hasAddress(UserDto user) {
    return user.getAddress() != null && !user.getAddress().isEmpty();
}
```

**問題点:**
- 各nullableフィールドに対して、if-elseで冗長なnullチェックが必要
- nullチェックを忘れると`NullPointerException`が発生
- コードが長くなり、可読性が低下

#### Kotlin版：Safe call演算子とElvis演算子で簡潔

```kotlin
// ユーザーの表示名を取得
fun getDisplayName(user: UserDto): String {
    return user.nickname ?: user.name
}

// 電話番号の文字数を取得
fun getPhoneNumberLength(user: UserDto): Int {
    return user.phoneNumber?.length ?: 0
}

// 住所が登録されているか確認
fun hasAddress(user: UserDto): Boolean {
    return !user.address.isNullOrEmpty()
}
```

**利点:**
- Safe call演算子（`?.`）でnullの場合は自動的にnullを返す
- Elvis演算子（`?:`）でデフォルト値を設定
- `isNullOrEmpty()`でnullチェックと空文字チェックを同時に実行
- 1行で安全に処理でき、可読性が高い

#### ResultSetからの変換でもnull安全

**Java版:**
```java
private User mapResultSetToUser(ResultSet rs) throws SQLException {
    return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getInt("age"),
            rs.getString("nickname"),       // nullの可能性があるが、型システムでは検出されない
            rs.getString("phone_number"),
            rs.getString("address")
    );
}
```

**問題点:**
- `getString()`はnullを返す可能性があるが、コンパイラは警告しない
- 実行時にnullが入っていても、使用するまでNPEは発生しない

**Kotlin版:**
```kotlin
private fun ResultSet.toUser() = User(
    id = getLong("id"),
    name = getString("name"),
    email = getString("email"),
    age = getInt("age"),
    nickname = getString("nickname"),          // DBからnullが返される可能性
    phoneNumber = getString("phone_number"),
    address = getString("address")
)
```

**利点:**
- `getString()`はKotlinでは`String?`型を返す
- `User`クラスの`nickname`、`phoneNumber`、`address`は`String?`型なので、そのまま代入可能
- 型システムが一致しているため、コンパイルエラーが発生しない

---

## 主な違いまとめ

| 項目 | Java | Kotlin |
|-----|------|--------|
| **コード量** | 約130行 | 約15行 |
| **ボイラープレート** | 多い | 非常に少ない |
| **イミュータブル** | 手動実装が必要 | `val`で簡単 |
| **Null安全性** | アノテーションで対応 | **言語レベルでサポート** ✨ |
| **Nullable型の表現** | 型では区別できない | **String? で明示** ✨ |
| **コンパイル時nullチェック** | なし | **あり** ✨ |
| **Null処理の冗長性** | if-elseが必要 | **?.、?:で簡潔** ✨ |
| **NullPointerException** | 実行時に発生するリスク | **コンパイル時に防げる** ✨ |
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
