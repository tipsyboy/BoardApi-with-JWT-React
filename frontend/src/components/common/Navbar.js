import React, { useEffect, useState } from "react";
import "./Navbar.css";

const Navbar = () => {
  // const [loginData, setLoginData] = useState(null);
  const [loginData, setLoginData] = useState(
    JSON.parse(localStorage.getItem("loginData"))
  );

  useEffect(() => {
    const storedLoginData = localStorage.getItem("loginData");
    if (storedLoginData) {
      setLoginData(JSON.parse(storedLoginData));
    }
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
          {loginData ? (
            <li>
              <a href="/profile" className="navbar-item">
                Profile
              </a>
              {/* <button className="navbar-item">Logout</button> */}
            </li>
          ) : (
            <>
              {" "}
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
