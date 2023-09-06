import React from "react";

const ReplyLikes = ({ currentUser, authPost, reply }) => {
  const handleLikes = () => {
    if (!currentUser) {
      window.alert("로그인 후 추천해주세요.");
      return;
    }

    const confirmed = window.confirm("정말 추천하시겠습니까?");
    if (confirmed) {
      if (currentUser.sub === reply.email) {
        window.alert("자신의 댓글은 추천할 수 없습니다.");
        return;
      }
    }

    const requestData = {
      replyId: reply.replyId,
    };
    authPost(`/api/likes/reply`, requestData)
      .then((response) => {
        window.alert("댓글을 추천하였습니다.");
        window.location.reload();
      })
      .catch((error) => {
        window.alert(error.response.data.message);
      });
  };

  return (
    <div className="reply-likes-btn">
      <button className="like-button" onClick={handleLikes}>
        추천 ({reply.likes})
      </button>
    </div>
  );
};

export default ReplyLikes;
