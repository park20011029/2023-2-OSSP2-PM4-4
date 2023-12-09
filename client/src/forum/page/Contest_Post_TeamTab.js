//공모전 팀원모집 게시글 리스트
import React, { useState } from "react";
import "../css/Contest_Post_TeamTab.module.css"
import List_Projects from "../component/List_Projects";

const Contest_Post_TeamTab = ({id}) => {
    const [listData, setListData] = useState({
        type:"contestPost",
        id: id
    })
    //게시글 페이지 정보
    const [pageInfo, setPageInfo] = useState({
        pageNumber:1,        //페이지 번호
        pageSize:6,         //한 페이지 당 게시글 수
        pageLength:10,       //한 화면에 표시할 총 페이지 수
        pageCount:55,       //총 페이지 개수
    });

    return (
        <List_Projects listData={listData}
                       pageInfo={pageInfo}
                       setPageInfo={setPageInfo} />
    );
};

export default Contest_Post_TeamTab;