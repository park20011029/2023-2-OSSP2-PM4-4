//공모전 팀원모집 게시글 리스트
import React from "react";
import { useNavigate } from 'react-router-dom';
import renderPageNumber from '../component/RenderPageNumber';
import Contest_Team_WritePost from "./Contest_Team_Write(Post)";
import "../css/Contest_Team_ListTab.css"

//dummy data
let write = {
    number: "123", //글번호
    title: "팀 구합니다",
    name : "김코딩",
    date : "2023.11.10"
};
const writeList = [write, write, write, write, write, write];
const totalWrite = 100;
const pageSize = 6;

//Todo: 페이지 정보 get
const getList = () => {

};

const Contest_Team_ListTab = () => {
    const navigate = useNavigate();

    // 상세페이지 이동
    function moveToWrite(index) {
        navigate(`/contestTeamWriteView`, { state: { number: index } });
    }

    return (
        <div className="List">
            <div className="category_row">
                <label className="title">제목</label>
                <label className="writer">작성자</label>
                <label className="date">작성일</label>
            </div>

            {writeList.map((item, index) => (
                <div className="item" key={index} onClick={() => moveToWrite(item.number)}>
                    <label className="title">{item.title}</label>
                    <label className="writer">{item.name}</label>
                    <label className="date">{item.date}</label>
                </div>
            ))}
            <div className={"writeButton"}>
                <button onClick={()=>{navigate('/contestTeamWritePost')}}>글쓰기</button>
            </div>
            <div className={"pageNumber"}>
                {renderPageNumber(totalWrite, pageSize, getList)}
            </div>
        </div>
    );
};

export default Contest_Team_ListTab;