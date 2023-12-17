//팀원 모집 글 작성 페이지
import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Write_Title from "../component/Write_Title";
import Write_Category from "../component/Write_Category";
import Write_Body from "../component/Write_Body";
import styles from "../css/Team_Write(Post).module.css";

const Team_WritePost = () => {
    const navigate = useNavigate();
    //데이터
    const [data, setData] = useState({
        userId:0,
        title:'',
        content:'',
        usePoint:false,
        partList:[],
    });
    
    //데이터 처리
    const setTitle = (title) => {
        setData((prevData) => ({
            ...prevData,
            title: title
        }))
    }
    const setCategory = (list) => {
        setData((prevData) => ({
            ...prevData,
            partList: list,
        }));
    };

    const setContent = (content) => {
        setData((prevData) => ({
            ...prevData,
            content:content,
        }));
    }

    const setReward = (reward) => {
        setData((prevData) => ({
            ...prevData,
            usePoint:reward,
        }));
    }

    //debug - 데이터 변경 시
    useEffect(() => {
        console.log("데이터 변경됨", data);
    }, [data]);


    //Todo: 리워드 처리, 전송
    const submit = () => {
        if(!window.confirm("완료하시겠습니까?")) return;
        try {

        } catch(error) {
            console.log(error);
        }
        console.log(data.content);
        navigate(-1);
    }

    return(
        <div>
            <Nav />
            <div className={styles.page}>
                <div className={styles.title}>
                    {/*게시글 작성*/}
                    <label>게시글 작성</label>
                    {/*제목 입력 창*/}
                    <Write_Title setTitle={setTitle}/>
                </div>
                {/*모집 카테고리 선택*/}
                <Write_Category setCategory={setCategory} />
                {/* 본문 */}
                <Write_Body setContent={setContent}
                            setReward={setReward}
                            submit={submit}
                />
            </div>
            <Footer />
        </div>
    )
}

export default Team_WritePost;