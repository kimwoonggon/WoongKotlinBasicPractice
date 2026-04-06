# Ktor REST API 서버 사용법

## 서버 실행

`src/main/kotlin/` 디렉토리에서 아래 명령으로 서버를 실행합니다:

```bash
cd src/main/kotlin
kotlin KtorServer.main.kts
```

> 첫 실행 시 의존성 다운로드로 시간이 걸릴 수 있습니다.

서버가 시작되면 `http://localhost:8080` 에서 동작합니다.

---

## API 사용법

### 1. 전체 사용자 조회

```bash
curl http://localhost:8080/users
```

응답 예시:
```json
[
  { "id": 1, "name": "Alice" },
  { "id": 2, "name": "Bob" }
]
```

### 2. 특정 사용자 조회

```bash
curl http://localhost:8080/users/1
```

응답 예시:
```json
{ "id": 1, "name": "Alice" }
```

존재하지 않는 ID 요청 시 `404 Not Found`를 반환합니다.

### 3. 새 사용자 등록

```bash
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"id\": 3, \"name\": \"Charlie\"}"
```

응답: `201 Created`
```json
{ "id": 3, "name": "Charlie" }
```

### PowerShell에서 테스트

```powershell
# 전체 조회
Invoke-RestMethod http://localhost:8080/users

# 특정 사용자
Invoke-RestMethod http://localhost:8080/users/1

# 새 사용자 등록
Invoke-RestMethod -Method Post -Uri http://localhost:8080/users -ContentType "application/json" -Body '{"id":3,"name":"Charlie"}'
```

---

## 참고

- `.kts` 스크립트에서는 `kotlinx.serialization` 컴파일러 플러그인이 동작하지 않아 **Gson**을 사용합니다.
- 서버는 인메모리 DB를 사용하므로 재시작하면 데이터가 초기화됩니다.
- 서버 종료: `Ctrl + C`
