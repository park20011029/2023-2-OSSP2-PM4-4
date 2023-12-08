import React from "react";
import { useNavigate } from 'react-router-dom';
import renderPageNumber from './RenderPageNumber';
import styles from "../css/Contest_Team_ListTab.module.css";

// dummy data
let write = {
    number: "123", //글번호
    title: "프로젝트 글 제목",
    name: "작성자",
    date: "2023.11.13"
};
const writeList = [write, write, write, write, write, write, write, write, write, write];
const totalWrite = 100;
const pageSize = 10;

//Todo: 글 리스트 가져오기
const getList = () => {

}

const ProjectList = () => {
    const navigate = useNavigate();
    //상세페이지 이동
    function moveToWrite(index) {
        navigate(`/ContestTeamWriteView/${index}`);
    }
    return (
        <div className={styles.List}>
            <div className={styles.category_row}>
                <label className={styles.title}>제목</label>
                <label className={styles.writer}>작성자</label>
                <label className={styles.date}>작성일</label>
            </div>

            {writeList.map((item, index) => (
                <div className={styles.item} key={index} onClick={() => moveToWrite(item.number)}>
                    <label className={styles.title}>{item.title}</label>
                    <label className={styles.writer}>{item.name}</label>
                    <label className={styles.date}>{item.date}</label>
                </div>
            ))}

            <div className={styles.writeButton}>
                <button onClick={()=>{navigate('/contestTeamWritePost')}}>글쓰기</button>
            </div>
            <div className={styles.pageNumber}>
                {renderPageNumber(totalWrite, pageSize, getList)}
            </div>
        </div>
    );
}


export default ProjectList;
