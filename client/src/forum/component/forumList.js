import React from "react";
import { useNavigate } from 'react-router-dom';
import renderPageNumber from "./RenderPageNumber";
import styles from "../css/ListPage.module.css";

// dummy data
let write1 = {
    number: "123", //글번호
    title: "글 제목",
    image: "forum_test.png",
};
let write2 = {
    number: "124",
    title:"글 제목",
    image:"contestEx1.png"
};
let write3 = {
    number: "125",
    title:"글 제목",
    image:"contestEx2.png"
};
let write4 = {
    number: "126",
    title:"글 제목",
    image:"contestEx3.png"
};
let write5 = {
    number: "127",
    title:"글 제목",
    image:"contestEx4.png"
};
let write6 = {
    number: "128",
    title:"글 제목",
    image:"contestEx5.png"
};

const writeList = [write1, write2, write3, write4, write5, write6];
const totalWrite = 50;
const pageSize = 6;
// dummy data


//화면크기가 줄어들면 사진의 크기를 줄여 대응
const ForumList = () => {
    const navigate = useNavigate();
    //상세페이지 이동
    function moveToWrite(index) {
        navigate(`/contestInfoPostPage`, {state:{number:index}});
    }

    //작성페이지 이동
    function moveToPost() {
        navigate('/contestInfoWritePage');
    }
    return (
        <div>
            <div className={styles.forumWriteList}>
                {writeList.map((write, index) => (
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
            {renderPageNumber(totalWrite, pageSize)}
        </div>
    );
}


export default ForumList;
