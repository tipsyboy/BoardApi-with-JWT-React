import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // useParams 추가
import { useNavigate } from "react-router-dom";
import useUnauthorizedApiCall from "../../components/auth/useUnauthorizedApiCall";
import useAuthorizedApiCall from "../../components/auth/useAuthorizedApiCall";
import useCurrentUser from "../../components/auth/useCurrentUser";
import ReplyList from "../reply/ReplyList";
import ReplyForm from "../reply/ReplyForm";
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
  const navigate = useNavigate();
  const postsId = pathParam.postsId;
  const [posts, setPosts] = useState(null);
  const { unAuthGet } = useUnauthorizedApiCall();
  const { authDelete } = useAuthorizedApiCall();
  const currentUser = useCurrentUser();

  useEffect(() => {
    unAuthGet(`/api/posts/${postsId}`)
      .then((response) => {
        setPosts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [postsId]);

  const handleEditClick = () => {
    navigate(`/posts/edit/${postsId}`);
  };

  const handleDelete = () => {
    const confirmed = window.confirm("게시글을 삭제하시겠습니까?");
    if (confirmed) {
      authDelete(`/api/posts?postsId=${postsId}`)
        .then(() => {
          alert("게시글이 삭제되었습니다.");
          navigate(`/${posts.category}`);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

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

      {currentUser && currentUser.sub === posts.email && (
        <div className="edit-delete-buttons">
          <button onClick={handleEditClick}>수정</button>
          <button onClick={handleDelete}>삭제</button>
        </div>
      )}

      <ReplyList posts={posts} setPosts={setPosts} currentUser={currentUser} />
      <ReplyForm posts={posts} setPosts={setPosts} postsId={postsId} />
    </div>
  );
};

export default PostsDetail;
