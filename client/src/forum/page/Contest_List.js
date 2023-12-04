//공모전 게시글 목록
import React, { useState, useEffect } from "react";
import Nav from "../../layout/Nav"
import Footer from "../../layout/Footer"
import List_Search from "../component/List_Search";
import List_Category from "../component/List_Category";
import List_Forums from "../component/List_Forums";
import styles from "../css/List.module.css";
import List_PageNumber from "../../layout/List_PageNumber";

/*
 * 데이터 구조
 * Board
 *   variables:
 *      searchWord = "",
 *      categorySelected = {},
 *      postList = []
 *
 *   components:
 *      1 Search    : 검색창
 *      2 Category  : 카테고리
 *      3 List      : 글 목록
 */

//dummy
const dummyData = {
    /* category --> {id: n, category:name} */
    write1 : {
        number: "1", //글번호
        title: "글 제목",
        image: "forum_test.png",
    },
    write2 : {
        number: "2",
        title:"글 제목",
        image:"contestEx1.png"
    },
    write3 : {
        number: "3",
        title:"글 제목",
        image:"contestEx2.png"
    },
    write4 : {
        number: "4",
        title:"글 제목",
        image:"contestEx3.png"
    },
    write5 : {
        number: "5",
        title:"글 제목",
        image:"contestEx4.png"
    },
    write6 : {
        number: "6",
        title:"글 제목",
        image:"contestEx5.png"
    },
    totalWrite : 6,
    pageSize : 3,
    pageCount : 2
}
const writeList = [
    dummyData.write1,
    dummyData.write2,
    dummyData.write3,
    dummyData.write4,
    dummyData.write5,
    dummyData.write6
];

const Contest_List = () => {
  //변수 선언
    //검색어
    const [searchWord, setSearchWord] = useState("");
    //선택한 카테고리
    const [categorySelected, setCategorySelected]
        = useState({});
    //게시글 목록
    const [postList, setPostList] = useState(writeList);
    //게시글 페이지 정보
    const [pageInfo, setPageInfo] = useState({
        pageNumber:13,        //페이지 번호
        pageSize:6,         //한 페이지 당 게시글 수
        pageLength:10,       //한 화면에 표시할 총 페이지 수
        pageCount:55,       //총 페이지 개수
    });
  // end

    // 1. 스크롤 처리
    useEffect(() => {
        window.onbeforeunload = function pushRefresh() {
            window.scrollTo(0, 0);
        };
    }, []);

    

    // 2. Todo: 검색기능 구현 - 카테고리 데이터형식 변경
    //검색어는 pathVariable, 나머지는 requestParam
    const search = () => {
        console.log("search method called!");
        //console.log("Word: " + searchWord);
        //console.log("Category: ");
        //console.log(categorySelected);
    }
    /*
    const search = async () => {
        console.log("search method called!");
        console.log("Word: " + searchWord);
        console.log("Category: ");
        console.log(categorySelected);

        try {
            const response = await axios.post(`/contestPost/`, {
                searchWord: searchWord,
                category: categorySelected,
                pageNumber: pageInfo.pageNumber,
                pageSize: pageInfo.pageSize
            });
            setPostList(response.data.responseDto);
        } catch (error) {
            setPostList(DwriteList);
            console.error(error);
        }
    }*/

    // 4. 페이지 번호 변경 시 목록 렌더링
    useEffect(() => {
        console.log("pageNumber Changed!" + pageInfo.pageNumber);
        //페이지 번호
        setPageInfo({...pageInfo, pageNumber: pageInfo.pageNumber});
        
        //Todo: search호출 후 postList갱신
        search();
        
        //글 목록
        //setPostList(writeList);
    }, [pageInfo.pageNumber]);

    return (
        <div>
            <Nav />
            <div className={styles.Page}>
                <List_Search setSearchWord={setSearchWord} search={search} />
                <List_Category setCategorySelected={setCategorySelected}
                               search={search} />
                <List_Forums postList={postList} />
            </div>
            <List_PageNumber pageInfo={pageInfo}
                             setPageInfo={setPageInfo} />
            <Footer />
        </div>
    );
}
export default Contest_List;