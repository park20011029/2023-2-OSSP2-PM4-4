//공모전 게시글 목록
import React, { Component } from "react";
import Nav from "../layout/Nav"
import Footer from "../layout/Footer"
import Search from "./Search";
import Category from "./searchState";
import List from "./List";
import "./ContestForumPage.css";

class ContestForumPage extends Component {
    render() {
        return (
            <div>
                <Nav />
                <div className='ForumPage'>
                    <Search />
                    <Category />
                    <List />
                </div>
                <Footer />
            </div>
        );
    }
}
export default ContestForumPage;
