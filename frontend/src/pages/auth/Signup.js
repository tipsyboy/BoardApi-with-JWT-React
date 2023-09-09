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
  const [generalError, setGeneralError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [nicknameError, setNicknameError] = useState("");

  const isValidationException = (error) => {
    return error.config && error.response && error.response.data.status === 400;
  };

  const isConflictException = (error) => {
    return error.config && error.response && error.response.data.status === 409;
  };

  const setValidationExMessages = (error) => {
    if (isValidationException(error)) {
      const validationExMessages = error.response.data.validationExMessages;
      setEmailError(validationExMessages.email || "");
      setPasswordError(validationExMessages.password || "");
      setNicknameError(validationExMessages.nickname || "");
    } else if (isConflictException(error)) {
      setGeneralError(error.response.data.message || "");
    }
  };

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
        setValidationExMessages(error);
      });
  };

  return (
    <div className="form-container">
      <div className="error-message">{generalError}</div>

      <form onSubmit={handleSignup}>
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
        <div className="error-message">{emailError}</div>

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
        <div className="error-message">{passwordError}</div>

        <div className="input-container">
          <label className="input-container-label">Nickname</label>
          <input
            className="input-container-input"
            type="nickname"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
            placeholder="nickname"
            required
          />
        </div>
        <div className="error-message">{nicknameError}</div>

        <button className="auth-btn" type="submit">
          sign up
        </button>
      </form>
    </div>
  );
};

export default Signup;
