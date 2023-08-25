import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useUnauthorizedApiCall from "../../components/auth/useUnauthorizedApiCall";
import "./Board.css";

const Board = (props) => {
  const requestUrl = `/api/posts/category/` + props.category;
  const [postsList, setPostsList] = useState([]);
  const { unAuthGet } = useUnauthorizedApiCall();

  useEffect(() => {
    unAuthGet(requestUrl)
      .then((response) => {
        setPostsList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [requestUrl]);

  return (
    <div className="board-container">
      <table>
        <thead>
          <tr>
            <th id="board-posts-title-head">제목</th>
            <th>작성자</th>
            <th>작성일시</th>
          </tr>
        </thead>

        <tbody>
          {postsList.map((posts) => (
            <tr key={posts.id}>
              <td id="posts-title-link">
                <span>
                  <Link to={`/posts/${posts.id}`}>
                    <span>{posts.title}</span>
                    <span>{posts.replyList.length}</span>
                  </Link>
                </span>
              </td>
              <td>{posts.nickname}</td>
              <td>{new Date(posts.createDate).toLocaleDateString()}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <a className="board-post-btn" href="/post">
        글쓰기
      </a>
    </div>
  );
};

export default Board;
