import React, { useEffect, useState } from "react";
import useAuthorizedApiCall from "../../components/auth/useAuthorizedApiCall";
import { Link } from "react-router-dom";
import "./Navbar.css";

const Navbar = () => {
  const { authPost } = useAuthorizedApiCall();
  const [accessToken, setAccessToken] = useState(
    localStorage.getItem("accessToken")
  );

  const handleLogout = () => {
    const requestData = {
      refreshToken: JSON.parse(localStorage.getItem("refreshToken")),
    };
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    authPost(`/api/auth/logout`, requestData)
      .then(() => {
        window.location.href = "/";
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    const storedAccessToken = localStorage.getItem("accessToken");
    setAccessToken(storedAccessToken);
  }, []);

  return (
    <nav className="navbar-container">
      <div className="navbar-left">
        <ul className="navbar-items-container">
          <li>
            <a href="/" className="navbar-item">
              Home
            </a>
          </li>
          <li>
            <a href="/free" className="navbar-item">
              자유게시판
            </a>
          </li>
          <li>
            <a href="/game" className="navbar-item">
              게임게시판
            </a>
          </li>
        </ul>
      </div>

      <div className="navbar-right">
        <ul className="navbar-items-container">
          {accessToken ? (
            <li>
              <a href="/profile" className="navbar-item">
                Profile
              </a>
              <button
                className="navbar-item navbar-logout-button"
                onClick={handleLogout}
              >
                Logout
              </button>
            </li>
          ) : (
            <>
              <li>
                <a href="/login" className="navbar-item">
                  Login
                </a>
              </li>
              <li>
                <a href="/signup" className="navbar-item">
                  Signup
                </a>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
