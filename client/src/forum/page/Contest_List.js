//공모전 게시글 목록
import React, { useState, useEffect } from "react";
import Nav from "../../layout/Nav"
import Footer from "../../layout/Footer"
import List_Search from "../component/List_Search";
import List_Category from "../component/List_Category";
import List_Forums from "../component/List_Forums";
import styles from "../css/List.module.css";
import List_PageNumber from "../component/List_PageNumber";
import {contest_getCategoryAll} from "../component/axios_category";

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
        number: "124",
        title:"글 제목",
        image:"contestEx1.png"
    },
    write3 : {
        number: "125",
        title:"글 제목",
        image:"contestEx2.png"
    },
    write4 : {
        number: "126",
        title:"글 제목",
        image:"contestEx3.png"
    },
    write5 : {
        number: "127",
        title:"글 제목",
        image:"contestEx4.png"
    },
    write6 : {
        number: "128",
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
        pageNumber:1,               //페이지 번호
        pageSize:dummyData.pageSize,         //한 페이지 당 게시글 수
        pageCount:dummyData.pageCount,       //총 페이지 개수
        pageStart:1                 //페이지 시작번호(1 + n*pageSize)
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

    // 3. 페이지 업데이트
    const updatePage = (number) => {
        setPageInfo({...pageInfo, pageNumber: number});
        search();
        //console.log("Page Number: " + pageInfo.pageNumber);
    }

    // 4. 페이지 번호 변경 시 목록 렌더링
    useEffect(() => {
        console.log("pageNumber useEffect from parent");
        //페이지 번호
        updatePage(pageInfo.pageNumber);
        const pageStart = Math.max(1, 
            Math.floor((pageInfo.pageNumber-1)/pageInfo.pageSize) * pageInfo.pageSize + 1);
        setPageInfo({...pageInfo, pageStart: pageStart})

        //글 목록
        const start = pageInfo.pageSize * (pageInfo.pageNumber - 1);
        const end = start + pageInfo.pageSize;
        const writeList = postList.slice(start, end);
        setPostList(writeList);
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

/*
<List_Category categoryInfo={categoryInfo}
                               setCategory={setCategorySelected}
                               search={search} />
 */