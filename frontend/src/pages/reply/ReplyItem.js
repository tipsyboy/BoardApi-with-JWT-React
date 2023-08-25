import React, { useState } from "react";
import useAuthorizedApiCall from "../../components/auth/useAuthorizedApiCall";
import "./Reply.css";

const ReplyItem = ({ reply, currentUser, onUpdateReply, postsId }) => {
  const [isEditing, setIsEditing] = useState(false);
  const { authPut, authDelete } = useAuthorizedApiCall();
  const [editedContent, setEditedContent] = useState(reply.content);

  const handleEdit = () => {
    const requestData = {
      replyId: reply.replyId,
      content: editedContent,
    };

    authPut(`/api/replies`, requestData)
      .then((response) => {
        onUpdateReply(reply.replyId, editedContent);
      })
      .catch((error) => {
        console.log(error);
      });

    setIsEditing(false); // 수정 완료 후 상태 변경
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleEdit(); // 엔터 키를 누르면 변경 적용
    }
  };

  const handleDelete = () => {
    const confirmed = window.confirm("정말로 삭제하시겠습니까?");

    if (confirmed) {
      authDelete(`/api/replies?replyId=${reply.replyId}`)
        .then(() => {
          alert("삭제되었습니다.");
          window.location.reload();
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  return (
    <li className="reply-item" key={reply.replyId}>
      <span className="reply-author">{reply.nickname} </span>
      {isEditing ? (
        <input
          className="reply-edit-input"
          type="text"
          value={editedContent}
          onChange={(e) => setEditedContent(e.target.value)}
          onKeyDown={handleKeyDown}
        />
      ) : (
        <span className="reply-content">{reply.content}</span>
      )}
      {currentUser && currentUser.sub === reply.email && (
        <div className="edit-delete-buttons">
          {isEditing ? (
            <button onClick={handleEdit}>완료</button>
          ) : (
            <>
              <button onClick={() => setIsEditing(true)}>수정</button>
              <button onClick={handleDelete}>삭제</button>
            </>
          )}
        </div>
      )}
    </li>
  );
};

export default ReplyItem;
