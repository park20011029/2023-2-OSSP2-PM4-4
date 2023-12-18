//공모전 게시글 목록
import React, { useState, useEffect } from "react";
import Nav from "../../layout/Nav"
import Footer from "../../layout/Footer"
import List_Search from "../component/List_Search";
import List_Forum_Category from "../component/List_Forum_Category";
import List_Forums from "../component/List_Forums";
import styles from "../css/List.module.css";
import List_PageNumber from "../../layout/List_PageNumber";
import axios from "axios";

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
        pageNumber:0,        //페이지 번호
        pageSize:3,         //한 페이지 당 게시글 수
        pageLength:10,       //한 화면에 표시할 총 페이지 수
        pageCount:55,       //총 페이지 개수
    });
  // end

    // 1. 스크롤 처리
    useEffect(() => {
        window.onbeforeunload = function pushRefresh() {
            window.scrollTo(0, 0);
        };
        //초기 렌더링
        search();
    }, []);

    

    // 검색
    const search = async () => {
        console.log("search method called!");
        console.log("Word: " + searchWord);
        console.log("Category: ", categorySelected);

        let url = `/contestPost/${searchWord}?page=${pageInfo.pageNumber-1}&size=${pageInfo.pageSize}`;
        Object.entries(categorySelected).map(([key, list]) => {
            list.map((element) => {
                url += `&${key}=${element}`;
            });
        });
        console.log("url:", url);

        try {
            const response = await axios.get(url);
            //Todo: postList 갱신
            setPostList(response.data.responseDto);
        } catch (error) {
            console.error(error);
        }
    }


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
                <List_Forum_Category setCategorySelected={setCategorySelected}
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