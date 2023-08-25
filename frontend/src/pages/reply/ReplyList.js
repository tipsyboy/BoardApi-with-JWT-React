import React from "react";
import ReplyItem from "./ReplyItem";
import "./Reply.css";

const ReplyList = ({ posts, setPosts, currentUser }) => {
  const handleUpdateReply = (replyId, updatedContent) => {
    const updatedReplyList = posts.replyList.map((reply) =>
      reply.replyId === replyId ? { ...reply, content: updatedContent } : reply
    );
    setPosts({ ...posts, replyList: updatedReplyList });
  };

  return (
    <div className="reply-container">
      <h4 className="reply-title">전체 댓글 {posts.replyList.length}개</h4>
      <ul className="reply-list">
        {posts.replyList.map((reply) => (
          <ReplyItem
            key={reply.replyId}
            reply={reply}
            currentUser={currentUser}
            postsId={posts.postsId}
            onUpdateReply={handleUpdateReply}
          />
        ))}
      </ul>
    </div>
  );
};

export default ReplyList;
