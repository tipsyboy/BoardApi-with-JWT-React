import React, { useState } from "react";
import useAuthorizedApiCall from "../../components/auth/useAuthorizedApiCall";
import { useNavigate } from "react-router-dom";
import "./PostsForm.css";

const PostsForm = () => {
  const { authPost } = useAuthorizedApiCall();
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("free");

  const handleWrite = () => {
    const requestData = {
      title: title,
      content: content,
      category: category,
    };

    authPost("/api/posts", requestData)
      .then((response) => {
        const postsId = response.data;
        navigate(`/posts/${postsId}`);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div className="posts-container">
      <h2 className="posts-title">글 쓰기</h2>

      <div className="posts-form-units">
        <label className="posts-label">카테고리</label>
        <select
          className="posts-select-form"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        >
          <option value="free" className="posts-select-option">
            자유 게시판
          </option>
          <option value="game" className="posts-select-option">
            게임 게시판
          </option>
          {/* 다른 카테고리 옵션들 추가 */}
        </select>
      </div>

      <div className="posts-form-units">
        <label className="posts-label">제목</label>
        <input
          className="posts-input-form"
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </div>

      <div className="posts-form-units">
        <label className="posts-label">내용</label>
        <textarea
          className="posts-input-form"
          rows="15"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        ></textarea>
      </div>

      <div className="posts-btn-container">
        <button className="posts-btn" onClick={handleWrite}>
          Write
        </button>
      </div>
    </div>
  );
};

export default PostsForm;
