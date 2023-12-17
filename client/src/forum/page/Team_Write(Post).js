//팀원 모집 글 작성 페이지
import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Write_Title from "../component/Write_Title";
import Write_Category from "../component/Write_Category";
import Write_Body from "../component/Write_Body";
import styles from "../css/Team_Write(Post).module.css";
import axios from "axios";

const Team_WritePost = () => {
    const navigate = useNavigate();
    //postID: 공모전 ID. 0이면 프로젝트를 의미함
    const {postId} = useParams();
    
    //데이터
    const [data, setData] = useState({
        userId:1,
        title:'',
        content:'',
        usePoint:false,
        partList:[],
    });
    
    //Todo: 수정 유효성 검사
    useEffect(() => {
        //get UserID and Writer ID

    }, []);

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


    //Todo: UserId, 리워드 처리
    const submit = async () => {
        if(!window.confirm("완료하시겠습니까?")) return;

        //적합성 확인
        if(data.title === "") {
            window.alert("제목을 입력해주세요.");
            return;
        }
        if(data.partList.length === 0) {
            window.alert("카테고리는 최소 1개 이상 등록해야 합니다.");
            return;
        }
        if(data.content === "") {
            window.alert("분문을 입력해주세요.");
            return;
        }

        try {
            let response;
            if(postId !== '0') {
                response = await axios.post(`/buildingPost/${postId}`, data);
            }
            else if(postId === '0') {
                response = await axios.post(`/projectPostPost`, data);
            }
            if(response.status === 200) {
                //성공
                window.alert("완료되었습니다.");
                navigate(-1);
            }
        } catch(error) {
            window.alert("오류 발생!", error);
            console.log(data);
            console.error(error);
        }
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
                            userId={data.userId}
                            submit={submit}
                />
            </div>
            <Footer />
        </div>
    )
}

export default Team_WritePost;