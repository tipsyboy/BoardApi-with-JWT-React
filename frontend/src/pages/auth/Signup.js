import React, { useState } from "react";
import "./Auth.css";
import useUnauthorizedApiCall from "../../components/auth/useUnauthorizedApiCall";
import { useNavigate } from "react-router-dom";

const Signup = () => {
  const { unAuthPost } = useUnauthorizedApiCall();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [message, setMessage] = useState("");

  const handleSignup = (e) => {
    e.preventDefault();

    const requestData = {
      email: email,
      password: password,
      nickname: nickname,
    };

    unAuthPost("/api/auth/signup", requestData)
      .then((response) => {
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="form-container">
      <form onSubmit={handleSignup}>
        <div className="input-container">
          <label className="input-container-label">E-mail</label>
          <input
            className="input-container-input"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Email Address"
          />
        </div>
        <div className="input-container">
          <label className="input-container-label">Password</label>
          <input
            className="input-container-input"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="password"
          />
        </div>
        <div className="input-container">
          <label className="input-container-label">Nickname</label>
          <input
            className="input-container-input"
            type="nickname"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
            placeholder="nickname"
          />
        </div>
        <button className="auth-btn" type="submit">
          sign up
        </button>
      </form>
    </div>
  );
};

export default Signup;
