import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./components/common/Layout";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Board from "./pages/board/Board";
import PostsDetail from "./pages/posts/PostsDetail";
import PostsForm from "./pages/posts/PostsForm";
import PostsUpdate from "./pages/posts/PostsUpdate";

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/free" element={<Board category="free" />}></Route>
          <Route path="/game" element={<Board category="game" />}></Route>
          <Route path="/posts/:postsId" element={<PostsDetail />}></Route>
          <Route path="/post" element={<PostsForm />}></Route>
          <Route path="/posts/edit/:postsId" element={<PostsUpdate />}></Route>
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
