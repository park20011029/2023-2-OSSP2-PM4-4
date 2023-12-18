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
    const [postList, setPostList] = useState([]);

    const getList = async() => {
        try {
            let response;
            let pInfo;
            //공모전
            if(listData.type === "buildingPost") {
                response = await axios.get(`/${listData.type}/list/${listData.id}`);
                const data = response.data.responseDto;
                pInfo = data.pageInfo;
                setPostList(data.buildingPosts);
            }
            //프로젝트
            else if(listData.type === "projectPostPost") {
                response = await axios.get(`/${listData.type}/list`);
                const data = response.data.responseDto;
                pInfo = data.pageInfo;
                setPostList(data.projectPosts);
            }

            //페이지 처리
            setPageInfo({
                pageNumber:pInfo.currentPage,
                pageSize:pageInfo.pageSize,
                pageLength:pageInfo.pageLength,
                pageCount:Math.ceil(pInfo.totalItems/pInfo.pageSize)
            });
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
            {postList.length === 0 ?
                <div className={styles.noList}>
                    <label>게시글이 없습니다</label>
                </div>
            :
            postList.map((item, index) => (
                <tr className={styles.item} key={index}
                     onClick={() => navigate(`/TeamWriteView/${item.postId}`)}>
                    <td className={styles.title}>
                        {item.title}
                    </td>
                    <td className={styles.writer}>{item.user}</td>
                    <td className={styles.date}>{item.creatAt}</td>
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
