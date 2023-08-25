import React from "react";
import "./Reply.css";

const ReplyItem = ({ reply, currentUser }) => {
  return (
    <li className="reply-item" key={reply.replyId}>
      <span className="reply-author">{reply.nickname} </span>
      <span className="reply-content">{reply.content}</span>
      {currentUser && currentUser.sub === reply.email && (
        <div className="edit-delete-buttons">
          <button>수정</button>
          <button>삭제</button>
        </div>
      )}
    </li>
  );
};

export default ReplyItem;
