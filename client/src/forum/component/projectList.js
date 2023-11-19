import React from "react";
import { useNavigate } from 'react-router-dom';
import renderPageNumber from './RenderPageNumber';
import styles from "../css/ListPage.module.css";
import "../css/Contest_Team_ListTab.css";

// dummy data
let write = {
    number: "123", //글번호
    title: "프로젝트 글 제목",
    name: "작성자",
    date: "2023.11.13"
};
const writeList = [write, write, write, write, write, write];
const totalWrite = 100;
const pageSize = 6;

//Todo: 글 리스트 가져오기
const getList = () => {

}

const ProjectList = () => {
    const navigate = useNavigate();
    //상세페이지 이동
    function moveToWrite(index) {
        navigate(`/ProjectWrite/${index}`);
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
}


export default ProjectList;
