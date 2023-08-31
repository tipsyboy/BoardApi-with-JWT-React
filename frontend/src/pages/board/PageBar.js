import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./PageBar.css";

const PageBar = ({ currentPage, category, totalPages, onPageChange }) => {
  const renderPageNumbers = () => {
    const pageNumbers = [];
    const start = Math.max(1, currentPage - 4);
    const end = Math.min(totalPages, start + 8);

    for (let i = start; i <= end; i++) {
      pageNumbers.push(
        <li
          className={`pagination-item ${
            currentPage === i ? "current-page" : ""
          }`}
          key={i}
        >
          <Link to={`/${category}?page=${i}`} onClick={() => onPageChange(i)}>
            {i}
          </Link>
        </li>
      );
    }

    return pageNumbers;
  };

  return (
    <div className="board-pagination-container">
      <ul className="pagination">
        <li
          className="pagination-item"
          key="prev"
          style={{ visibility: currentPage > 1 ? "visible" : "hidden" }}
        >
          <Link
            to={`/${category}?page=${currentPage - 1}`}
            onClick={() => onPageChange(currentPage - 1)}
          >
            Previous
          </Link>
        </li>
        {renderPageNumbers()}
        <li
          className="pagination-item"
          key="next"
          style={{
            visibility: currentPage < totalPages ? "visible" : "hidden",
          }}
        >
          <Link
            to={`/${category}?page=${currentPage + 1}`}
            onClick={() => onPageChange(currentPage + 1)}
          >
            Next
          </Link>
        </li>
      </ul>
    </div>
  );
};

export default PageBar;
