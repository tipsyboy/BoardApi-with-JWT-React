## 게시글 등록
### Request
```HTTP
POST /api/posts
Content-Type: application/json
Authorization: Bearer ...Access_Token...
```
```json
{
  "title": "...TITLE...",
  "content": "...CONTENTS...",
  "category": "...CATEGORY..."
}
```
### Response
```
{
  ...POST ID...
}
```

## 게시글 아이디로 조회
### Request
```HTTP
GET /api/posts/{postsId}
Content-Type: application/json
```
### Response
```json
{
    "id": "...posts id...",
    "email": "...AUTHOR_EMAIL...",
    "nickname": "AUTHOR_NICKNAME...",
    "title": "...TITLE...",
    "content": "...CONTENTS...",
    "category": "...CATEGORY...",
    "likes": 0,
    "replyList": ["...REPLY_LIST..."],
    "createDate": "...CREATED_DATE..."
}
```

## 게시글 카테고리 조회
### Request
```HTTP
GET /api/posts/category/{category}
Content-Type: application/json
```
### Response
```json
{
  "content": [
    {
      "id": 301,
      "email": "test@example.com",
      "nickname": "테스트맨",
      "title": "sample title",
      "content": "sample contents",
      "category": "free",
      "likes": 0,
      "replyList": [],
      "createDate": "2023-09-13T16:20:07.593416"
    },
    {
      "id": 300,
      "email": "tester@test.com",
      "nickname": "테스터",
      "title": "테스트 제목 299",
      "content": "테스트 콘텐츠입니다. 299",
      "category": "free",
      "likes": 0,
      "replyList": [],
      "createDate": "2023-09-13T16:07:16.674393"
    },
    {
      "id": 299,
      "email": "tester@test.com",
      "nickname": "테스터",
      "title": "테스트 제목 298",
      "content": "테스트 콘텐츠입니다. 298",
      "category": "free",
      "likes": 0,
      "replyList": [],
      "createDate": "2023-09-13T16:07:16.671397"
    }, 
    ...
}
```
## 게시글 수정
### Request
```HTTP
POST /api/posts
Content-Type: application/json
Authorization: Bearer ...Access_Token...
```
```json
{
  "postsId": "...POSTS ID...",
  "title": "...EDITED TITLE...",
  "content": "...EDITED CONTENTS...",
  "category": "...EDITED CATEGORY..."
}
```