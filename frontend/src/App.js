import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./components/common/Layout";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Board from "./pages/board/Board";

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/free" element={<Board category="free" />}></Route>
          <Route path="/game" element={<Board category="game" />}></Route>
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
