import React, { useState } from "react";
import useAuthorizedApiCall from "../../components/auth/useAuthorizedApiCall";
import "./Reply.css";

const ReplyForm = ({ posts, setPosts }) => {
  const { authPost } = useAuthorizedApiCall();
  const [newReply, setNewReply] = useState(""); // 새로 작성한 댓글 내용

  const handleReplyKeyDown = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault(); // Prevent form submission
      handleReplySubmit(e); // Call your submit function
    }
  };

  const handleReplySubmit = (e) => {
    e.preventDefault();

    const requestData = {
      postsId: posts.id,
      content: newReply,
    };

    authPost("/api/replies", requestData)
      .then((response) => {
        // 새로운 댓글이 추가된 데이터로 갱신
        setPosts((prevPost) => ({
          ...prevPost,
          replyList: [...prevPost.replyList, response.data],
        }));
        setNewReply(""); // 댓글 작성 폼 초기화
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <form className="reply-form" onSubmit={handleReplySubmit}>
      <textarea
        className="reply-input"
        placeholder="댓글을 입력하세요."
        value={newReply}
        onChange={(e) => setNewReply(e.target.value)}
        onKeyDown={handleReplyKeyDown} // Add onKeyDown event
      ></textarea>
      <button type="submit" className="reply-btn">
        댓글 작성
      </button>
    </form>
  );
};

export default ReplyForm;
