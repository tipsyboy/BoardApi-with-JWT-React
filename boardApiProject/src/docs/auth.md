## 회원가입
### Request
```HTTP
POST /api/auth/signup
Content-Type: application/json
```
```json
{
  "email": "test@example.com",
  "password": "test1234",
  "nickname": "tester"
}
```
### Response
```
{
  ... Member Id ...
}
```

## 로그인
### Request
```HTTP
POST /api/auth/login
Content-Type: application/json
```
```json
{
  "email": "test@example.com",
  "password": "test1234"
}
```
### Response
```json
{
  "accessToken": "...accessToken...",
  "refreshToken": "...refreshToken..."
}
```

## 토큰 재발급
### Request
```HTTP
POST /api/auth/reissue
Content-Type: application/json
```
```json
{
  "accessToken": "...Expired_Access_Token...",
  "refreshToken": "...refreshToken..."
}
```
### Response
```json
{
  "accessToken": "...New_Access_Token...",
  "refreshToken": "...New_Refresh_Token..."
}
```