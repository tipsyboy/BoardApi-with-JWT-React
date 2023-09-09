import React, { useState } from "react";
import "./Auth.css";
import useUnauthorizedApiCall from "../../components/auth/useUnauthorizedApiCall";

const Login = () => {
  const { unAuthPost } = useUnauthorizedApiCall();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    const requestData = {
      email: email,
      password: password,
    };

    unAuthPost("/api/auth/login", requestData)
      .then((response) => {
        const loginData = response.data;
        localStorage.setItem(
          "accessToken",
          JSON.stringify(loginData.accessToken)
        );
        localStorage.setItem(
          "refreshToken",
          JSON.stringify(loginData.refreshToken)
        );
        window.location.href = "/";
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div className="form-container">
      <form onSubmit={handleLogin}>
        <div className="input-container">
          <label className="input-container-label">E-mail</label>
          <input
            className="input-container-input"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Email Address"
            required
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
            required
          />
        </div>
        <p className="error-message">{message}</p>
        <p className="signup-help">
          아직 회원이 아니라면? <a href="/signup">회원가입</a>
        </p>
        <button className="auth-btn" type="submit">
          Login
        </button>
      </form>
    </div>
  );
};

export default Login;
