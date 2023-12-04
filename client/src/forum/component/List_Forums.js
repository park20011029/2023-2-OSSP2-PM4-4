//공모전 게시판 목록
import React from "react";
import { useNavigate } from 'react-router-dom';
import styles from "../css/List.module.css";

const List_Forums = ({postList}) => {
    const navigate = useNavigate();

    //상세페이지 이동
    const moveToWrite = (index) => {
        navigate(`/contestInfoPostPage/${index}`);
    }

    //작성페이지 이동
    const moveToPost = () => {
        navigate('/contestInfoWritePage');
    }

    return (
        <div>
            <div className={styles.forumWriteList}>
                {postList.map((write) => (
                    <div key={write.number} className={styles.forumWrite}>
                        <img src={write.image} alt={write.title}
                            onClick={()=>moveToWrite(write.number)}/>
                        <label onClick={()=>moveToWrite(write.number)}>
                            {write.title}</label>
                    </div>
                ))}
            </div>
            <div className={styles.writeButton}>
                <button onClick={()=>moveToPost()}>글쓰기</button>
            </div>

        </div>
    );
}


export default List_Forums;
