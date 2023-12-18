//공모전 게시판 목록
import React from "react";
import { useNavigate } from 'react-router-dom';
import styles from "../css/List.module.css";
import "../css/buttons.css";

const List_Forums = ({postList}) => {
    const navigate = useNavigate();

    //상세페이지 이동
    const moveToWrite = (index) => navigate(`/contestInfoPostPage/${index}`);

    //작성페이지 이동
    const moveToPost = () => navigate('/contestInfoWritePage');

    return (
        <div>
            <div className={styles.forumWriteList}>
                {postList.length === 0 ?
                    <div className={styles.noPost}>
                        <label>결과 없음</label>
                    </div>
                :
                    postList.map((write) => (
                        <div key={write.contestId} className={styles.forumWrite}>
                            <img src={write.imageUrl} alt={write.title}
                                onClick={()=>moveToWrite(write.contestId)}/>
                            <label onClick={()=>moveToWrite(write.contestId)}>
                                {write.title}</label>
                        </div>
                    ))
                }
            </div>
            <div className={styles.writeButton}>
                <button className="yellowButton" onClick={()=>moveToPost()}>글쓰기</button>
            </div>

        </div>
    );
}


export default List_Forums;
