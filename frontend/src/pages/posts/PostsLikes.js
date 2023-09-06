import React from "react";
import "./PostsLikes.css";

const PostsLikes = ({ currentUser, authPost, posts }) => {
  const handleLikes = () => {
    if (!currentUser) {
      window.alert("로그인 후 추천해주세요.");
      return;
    }

    const confirmed = window.confirm("정말 추천하시겠습니까?");
    if (confirmed) {
      if (currentUser.sub === posts.email) {
        window.alert("자신의 글은 추천할 수 없습니다.");
        return;
      }

      const requestData = {
        postsId: posts.id,
      };
      authPost(`/api/likes/post`, requestData)
        .then((response) => {
          window.alert("게시글을 추천하였습니다.");
          window.location.reload();
        })
        .catch((error) => {
          window.alert(error.response.data.message);
        });
    }
  };

  return (
    <div>
      <button className="like-button" onClick={handleLikes}>
        추천 ({posts.likes})
      </button>
    </div>
  );
};

export default PostsLikes;
