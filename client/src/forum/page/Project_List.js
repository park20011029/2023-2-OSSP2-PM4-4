import React, {useEffect, useState} from "react";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import List_Search from "../component/List_Search";
import List_Forum_Category from "../component/List_Forum_Category";
import List_Projects from "../component/List_Projects";
import List_PageNumber from "../../layout/List_PageNumber";
import styles from "../css/List.module.css";
import {team_CategoryList} from "../component/axios_category";
import List_Project_Category from "../component/List_Project_Category";

const write = {
    number: "123", //글번호
    title: "프로젝트 글 제목",
    name: "작성자",
    date: "2023.11.13",
};

const writeList = [write, write, write, write, write, write, write, write, write, write];

const Project_List = () => {
    // 변수 선언
    //정보
    const listData = {
        type:"projectPostPost"
    }
    // 검색어
    const [searchWord, setSearchWord] = useState("");
    // 카테고리
    const [selected, setSelected] = useState(() => {
        let result = {};
         team_CategoryList.map((key) => {
             result[key] = [];
         });
         return result;
    });

    // 게시글 페이지 정보
    const [pageInfo, setPageInfo] = useState({
        pageNumber: 1,
        pageSize: 2,
        pageLength: 10,
        pageCount: 55,
    });

    // 1. 스크롤 처리
    useEffect(() => {
        window.onbeforeunload = function pushRefresh() {
            window.scrollTo(0, 0);
        };
    }, []);

    // 2.debug
    useEffect(() => {
        console.log("데이터 변경됨", selected);
    }, [selected]);

    //Todo: 검색
    useEffect(() => {

    });
    const search = async() => {
        console.log("search");
        console.log(searchWord);
        console.log(selected);
    }

    return (
        <div>
            <Nav />
            <div className={styles.Page}>
                <List_Search setSearchWord={setSearchWord} search={search} />
                {/*
                <List_Project_Category setSelected={setSelected}
                                       search={search}
                />
                */}
                <List_Projects listData={listData}
                               pageInfo={pageInfo}
                               setPageInfo={setPageInfo}
                />
            </div>
            <Footer />
        </div>
    );
};

export default Project_List;