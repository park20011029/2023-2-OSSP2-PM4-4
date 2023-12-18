//공모전 팀원모집 게시글 리스트
import React, {useEffect, useState} from "react";
import "../css/Contest_Post_TeamTab.module.css"
import List_Projects from "../component/List_Projects";
import axios from "axios";

const Contest_Post_TeamTab = ({contestPostId}) => {
    const [listData, setListData] = useState({
        type:"buildingPost",
        id: contestPostId
    })
    //게시글 페이지 정보
    const [pageInfo, setPageInfo] = useState({
        pageNumber:1,        //페이지 번호
        pageSize:6,         //한 페이지 당 게시글 수
        pageLength:10,       //한 화면에 표시할 총 페이지 수
        pageCount:55,       //총 페이지 개수
    });
    const [postData, setPostData] = useState([]);
    const search = async() => {
        const url = `/buildingPost/list`;
        console.log("URL:", url);
        try {
            const response = await axios.get(url);
            const jsonData = response.data.responseDto;
            const pInfo = jsonData.pageInfo;
            setPostData(jsonData.buildingPosts);
            setPageInfo({
                pageNumber: pInfo.currentPage,
                pageSize: pInfo.pageSize,
                pageCount: Math.ceil(pInfo.totalItems/pInfo.pageSize),
                pageLength: pageInfo.pageLength
            });
        } catch(error) {
            console.log(error);
        }
    }
    return (
        <List_Projects listData={listData}
                       pList={postData}
                       pageInfo={pageInfo}
                       setPageInfo={setPageInfo} />
    );
};

export default Contest_Post_TeamTab;