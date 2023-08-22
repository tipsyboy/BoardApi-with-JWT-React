import React from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import "./Layout.css";

const Layout = ({ children }) => {
  return (
    <div className="layout-container">
      <Navbar />
      <div className="contents">{children}</div>
      <Footer />
    </div>
  );
};

export default Layout;
