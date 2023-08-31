import React, { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import useUnauthorizedApiCall from "../../components/auth/useUnauthorizedApiCall";
import PageBar from "./PageBar";
import "./Board.css";

const Board = (props) => {
  const [searchParams] = useSearchParams();
  const pageValue = searchParams.get("page");
  const [page, setPage] = useState(pageValue === null ? 1 : Number(pageValue));
  const [totalPages, setTotalPages] = useState(0);
  const requestUrl = `/api/posts/category/${props.category}`;
  const [postsList, setPostsList] = useState([]);
  const { unAuthGet } = useUnauthorizedApiCall();

  useEffect(() => {
    const urlWithPage = `${requestUrl}?page=${page}`;

    unAuthGet(urlWithPage)
      .then((response) => {
        setPostsList(response.data.content);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [requestUrl, page]);

  const handlePageChange = (newPage) => {
    console.log(newPage);
    setPage(newPage);
  };

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
              <td>
                {new Date(posts.createDate).toLocaleString("ko-KR", {
                  year: "numeric",
                  month: "numeric",
                  day: "numeric",
                  hour12: false,
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit",
                })}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <PageBar
        currentPage={page}
        category={props.category}
        totalPages={totalPages} // You need to calculate the total pages
        onPageChange={handlePageChange}
      />
      <a className="board-post-btn" href="/post">
        글쓰기
      </a>
    </div>
  );
};

export default Board;
