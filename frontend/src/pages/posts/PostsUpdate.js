import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import useUnauthorizedApiCall from "../../components/auth/useUnauthorizedApiCall";
import useAuthorizedApiCall from "../../components/auth/useAuthorizedApiCall";
import "./PostsForm.css";

const PostsUpdate = () => {
  const { unAuthGet } = useUnauthorizedApiCall();
  const { authPut } = useAuthorizedApiCall();
  const navigate = useNavigate();
  const pathParam = useParams();
  const postsId = pathParam.postsId;
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
    unAuthGet(`/api/posts/${postsId}`)
      .then((response) => {
        const posts = response.data;
        setCategory(posts.category);
        setTitle(posts.title);
        setContent(posts.content);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleUpdate = () => {
    const requestData = {
      postsId: postsId,
      category: category,
      title: title,
      content: content,
    };
    authPut(`/api/posts`, requestData)
      .then(() => {
        navigate(`/posts/${postsId}`);
      })
      .catch((error) => {
        console.log(error);
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
        <button className="posts-btn" onClick={handleUpdate}>
          수정
        </button>
      </div>
    </div>
  );
};

export default PostsUpdate;
