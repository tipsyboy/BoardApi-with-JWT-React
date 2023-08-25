import React from "react";
import ReplyItem from "./ReplyItem";
import "./Reply.css";

const ReplyList = ({ posts, currentUser }) => {
  return (
    <div className="reply-container">
      <h4 className="reply-title">전체 댓글 {posts.replyList.length}개</h4>
      <ul className="reply-list">
        {posts.replyList.map((reply) => (
          <ReplyItem
            key={reply.replyId}
            reply={reply}
            currentUser={currentUser}
          />
        ))}
      </ul>
    </div>
  );
};

export default ReplyList;
