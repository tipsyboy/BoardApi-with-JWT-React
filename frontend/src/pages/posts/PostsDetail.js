import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // useParams 추가
import useUnauthorizedApiCall from "../../components/apiCall/useUnauthorizedApiCall";
import "./PostsDetail.css";

const formatDateString = (dateString) => {
  const createDate = new Date(dateString);
  return `${createDate.getFullYear()}.${(createDate.getMonth() + 1)
    .toString()
    .padStart(2, "0")}.${createDate
    .getDate()
    .toString()
    .padStart(2, "0")} ${createDate
    .getHours()
    .toString()
    .padStart(2, "0")}:${createDate
    .getMinutes()
    .toString()
    .padStart(2, "0")}:${createDate.getSeconds().toString().padStart(2, "0")}`;
};

const PostsDetail = () => {
  const pathParam = useParams();
  const postsId = pathParam.postsId;
  const [posts, setPosts] = useState(null);
  const [newReply, setNewReply] = useState(""); // 새로 작성한 댓글 내용
  const { unAuthGet } = useUnauthorizedApiCall();

  useEffect(() => {
    unAuthGet(`/api/posts/${postsId}`)
      .then((response) => {
        setPosts(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [postsId]);

  // TODO: Authorized Call 관련
  const handleReplyKeyDown = () => {};
  const handleReplySubmit = () => {};

  if (!posts) {
    return <p>Loading..</p>;
  }

  return (
    <div className="posts-detail-container">
      <h2 className="posts-detail-title">{posts.title}</h2>
      <div className="posts-detail-box">
        <span className="posts-detail-author">{posts.nickname} </span>
        <span className="posts-detail-date">
          | {formatDateString(posts.createDate)}
        </span>
      </div>
      <p className="posts-detail-content">{posts.content}</p>

      <div className="reply-container">
        <h4 className="reply-title">전체 댓글 {posts.replyList.length}개</h4>
        <ul className="reply-list">
          {posts.replyList.map((reply) => (
            <li className="reply-item" key={reply.replyId}>
              <span className="reply-author">{reply.nickname} </span>
              <span className="reply-content">{reply.content}</span>
            </li>
          ))}
        </ul>
      </div>

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
    </div>
  );
};

export default PostsDetail;
