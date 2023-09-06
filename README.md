# BoardApi-with-JWT-React
## BoardApi Project with JWT-React

## 📈 기능 설계
- Auth
    - [x] 회원 가입 - 사용자는 회원 가입을 할 수 있다.
    - [x] 로그인 - 사용자는 로그인을 할 수 있다.
    - [x] 로그아웃 - 사용자는 로그아웃을 할 수 있다.
- Posts
    - [x] 등록된 글을 읽을 수 있다. (Read) - 권한 X
    - [x] 사용자는 글을 쓸 수 있다. (Crud) - 회원 권한
    - [x] 글의 작성자는 글을 수정할 수 있다. (Update) - 작성자 권한
    - [x] 글의 작성자는 글을 삭제할 수 있다. (Delete) - 작성자 권한
    - [x] 글에는 추천 기능이 있어 추천할 수 있다.
    - [ ] 파일 업로드가 가능해 이미지를 올릴 수 있다.
- Reply
    - [x] 사용자는 댓글을 읽을 수 있다. - 권한 X
    - [x] 사용자는 글에 댓글을 달 수 있다. - 회원 권한
    - [x] 댓글 작성자는 댓글을 수정할 수 있다. - 작성자 권한
    - [x] 댓글 작성자는 댓글을 삭제할 수 있다. - 작성자 권한
    - [x] 댓글에는 추천 기능이 있어 추천할 수 있다.
- Board
    - [x] 게시판은 카테고리를 사용해서 여러 게시판으로 나뉜다.
---