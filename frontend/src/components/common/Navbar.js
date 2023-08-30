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
    console.log(storedAccessToken);
  }, []);

  return (
    <nav className="navbar-container">
      <div className="navbar-left">
        <ul className="navbar-items-container">
          <li>
            <Link to="/" className="navbar-item">
              Home
            </Link>
          </li>
          <li>
            <Link to="/free" className="navbar-item">
              자유게시판
            </Link>
          </li>
          <li>
            <Link to="/game" className="navbar-item">
              게임게시판
            </Link>
          </li>
        </ul>
      </div>

      <div className="navbar-right">
        <ul className="navbar-items-container">
          {accessToken ? (
            <li>
              <Link to="/profile" className="navbar-item">
                Profile
              </Link>
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
                <Link to="/login" className="navbar-item">
                  Login
                </Link>
              </li>
              <li>
                <Link to="/signup" className="navbar-item">
                  Signup
                </Link>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
