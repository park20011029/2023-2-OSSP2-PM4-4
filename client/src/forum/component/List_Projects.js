//프로젝트 게시글 목록 컴포넌트

import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import List_PageNumber from '../../layout/List_PageNumber';
import styles from "../css/Contest_Post_TeamTab.module.css";
import axios from "axios";

// dummy data
let write = {
    number: "123", //글번호
    title: "프로젝트 글 제목",
    name: "작성자",
    date: "2023.11.13"
};
const writeList = [write, write, write, write, write, write, write, write, write, write];


const List_Projects = ({listData, pageInfo, setPageInfo}) => {
    const navigate = useNavigate();
    const [postList, setPostList] = useState(writeList);

    //Todo: 글 리스트 가져오기
    const getList = async() => {
        try {
            const response = await axios.get("URL");

        } catch(error) {
            console.log(error);
        }
    }

    useEffect(() => {
       getList();
    }, []);

    return (
        <div className={styles.List}>
            <div className={styles.category_row}>
                <label className={styles.title}>제목</label>
                <label className={styles.writer}>작성자</label>
                <label className={styles.date}>작성일</label>
            </div>

            {writeList.map((item, index) => (
                <div className={styles.item} key={index}
                     onClick={() => navigate(`/ContestTeamWriteView/${item.number}`)}>
                    <label className={styles.title}>{item.title}</label>
                    <label className={styles.writer}>{item.name}</label>
                    <label className={styles.date}>{item.date}</label>
                </div>
            ))}

            <div className={styles.writeButton}>
                <button onClick={()=>{navigate('/contestTeamWritePost')}}>글쓰기</button>
            </div>
            <div className={styles.pageNumber}>
                {List_PageNumber({pageInfo, setPageInfo})}
            </div>
        </div>
    );
}


export default List_Projects;
