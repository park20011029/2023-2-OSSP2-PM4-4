//공모전 게시글 목록
import React, { Component } from "react";
import Nav from "../../layout/Nav"
import Footer from "../../layout/Footer"
import Search from "../component/Search";
import Category from "../component/Category";
import ForumList from "../component/forumList";
import "../css/ContestForumPage.css";

const criteria = { title: "응모 대상", host: "주최 기관", scale: "시상 규모" };
const detailList = {
    title: ["청소년", "대학생", "직장인", "제한없음"],
    host: ["대기업", "공공기업", "외국계기업"],
    scale: ["1천 미만", "1천 ~ 3천", "3천 이상"]
};
const selected = { title: [], host: [], scale: [] };

class ContestForumPage extends Component {
    constructor(props) {
        super(props);

    }
    render() {
        return (
            <div>
                <Nav />
                <div className='ForumPage'>
                    <Search />
                    <Category criteria={criteria} detailList={detailList} selected={selected} />
                    <ForumList />
                </div>
                <Footer />
            </div>
        );
    }
}
export default ContestForumPage;
