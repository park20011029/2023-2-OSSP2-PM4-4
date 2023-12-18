//프로젝트 게시글 목록 컴포넌트
import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import List_PageNumber from '../../layout/List_PageNumber';
import styles from "../css/Contest_Post_TeamTab.module.css";
import "../css/buttons.css";
import axios from "axios";

// dummy data
let write = {
    number: "32", //글번호
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
            let response;
            //공모전
            if(listData.type === "contestPost") {
                response = await axios.get(`/${listData.type}/${listData.id}`);
            }
            //프로젝트
            else if(listData.type === "projectPostPost") {
                response = await axios.get(`/${listData.type}/`);
            }


        } catch(error) {
            console.log(error);
        }
    }

    useEffect(() => {
       getList();
    }, []);

    return (
        <>
        <table className={styles.List}>
            <tr className={styles.category_row}>
                <th className={styles.title}>제목</th>
                <th className={styles.writer}>작성자</th>
                <th className={styles.date}>작성일</th>
            </tr>

            {writeList.map((item, index) => (
                <tr className={styles.item} key={index}
                     onClick={() => navigate(`/TeamWriteView/${item.number}`)}>
                    <td className={styles.title}>
                        {item.title}
                    </td>
                    <td className={styles.writer}>{item.name}</td>
                    <td className={styles.date}>{item.date}</td>
                </tr>
            ))}
        </table>
        <div className={styles.button}>
            <button className="yellowButton"
                    onClick={()=>{
                if(listData.type === "contestPost")
                    navigate(`/TeamWritePost/${listData.id}`);
                else
                    navigate(`/TeamWritePost/0`);
            }}>글쓰기</button>
        </div>
        <div className={styles.pageNumber}>
            {List_PageNumber({pageInfo, setPageInfo})}
        </div>
    </>
    );
}


export default List_Projects;
