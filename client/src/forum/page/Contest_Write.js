//공모전 정보 게시글 작성
import React, {useEffect, useState} from "react";
import styles from "../css/Team_Write(Post).module.css";
import "../css/buttons.css";
import Write_Title from "../component/Write_Title";
import Write_Contest_Dropdown from "../component/Write_Contest_Dropdown";
import Write_Calendar from "../component/Write_DatePick";
import ReactQuill from "react-quill";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import {contest_CategoryKOR, contest_CategoryList, contest_CategoryKeyPost} from "../component/axios_category";

const Contest_Write = () => {
    const navigate = useNavigate();
    //카테고리 제목
    const categoryList = {
        image:"포스터",        //image
        title:"제목",         //text

        target:"응모대상",      //dropdown
        organization:"주최기관",
        category:"공모분야",
        scale:"수상규모",
        benefit:"수상혜택",

        startAt:"시작일",    //calendar
        endAt:"종료일",

        content:"상세내용",     //textarea
    }

    //전송할 객체
    const [image, setImage] = useState({
        src: "",
        url: ""
    });
    const [data, setData] = useState({});
    const [content, setContent] = useState('');

    //debug
    useEffect(() => {
        console.log("parent brief state changed.");
        console.log("brief:", data);
    }, [data]);
    useEffect(() => {
        console.log("content:",content);
    }, [content]);
    useEffect(() => {
        //스크롤 처리
        window.onbeforeunload = function pushRefresh() {
            window.scrollTo(0, 0);
        }
        const id = localStorage.getItem('userId');
        if(id !== null)
            setData({...data, userId: id});
        else
            setData({...data, userId:1});
        
    }, []);

    // 2.입력값을 처리하는 함수
    const handleImgChange = (e) => { //이미지
        const selectedImage = e.target.files[0];
        setImage({
            src:selectedImage,
            url:URL.createObjectURL(selectedImage)
        });

        /*
        //미리보기
        const preview = new FileReader();
        preview.onloadend = () => {
            setImage({...image, url:preview.result});
        };
        if(selectedImage) {
            preview.readAsDataURL(selectedImage);
        }
         */
    };

    const setTitle = (title) => {
        setData((prevData) => ({
            ...prevData,
            title: title
        }))
    }
    const handleDataChange_Event = (e) => { //나머지 값
        const newID = e.target.value;
        const newKey = e.target.id;
        setData({...data, [newKey]: newID});
    };
    const handleDateChange_Param = (key, value) => {
        setData({...data, [key]:value});
    }

    // 4.작성완료 버튼
    const upload = async() => {
        if(!window.confirm("작성하시겠습니까?")) return;
        console.log(image);
        //유효성 검사
        if(!image.url) return(window.alert("이미지를 업로드하세요"));
        if(!data.title) return(window.alert("제목을 입력하세요"));
        for(let i=0; i<contest_CategoryList.length; i++) {
            if(!data[contest_CategoryList[i]])
                return(window.alert(contest_CategoryKOR[i]+"을(를) 입력하세요."));
        }
        const sdate = new Date(data.startAt);
        const edate = new Date(data.endAt);
        if(sdate >= edate) return(window.alert("시작일은 마감일보다 빨라야합니다."));
        if(!content) return(window.alert("본문을 입력하세요."));

        try {
            const formData = new FormData();
            formData.append('file', image.src);
            console.log(image.src);
            //나머지 데이터 처리
            const postData = {};
            Object.keys(contest_CategoryKeyPost).forEach((key) => {
                postData[contest_CategoryKeyPost[key]] = data[key];
            });
            console.log("data", postData);
            formData.append(
                "contestPostRequestDto", new Blob([JSON.stringify(postData)], {type: "application/json"})
            );
            console.log("formData:", formData);
            const config = {"Content-Type": 'image/jpeg',};

            const response = await axios.post("/contestPost", formData);
            /*{
                headers: {
                    Authorization: `Bearer ${"accessToken"}`, //Todo: accessToken
                }, config
            });
             */

            if(response.status === 200) {
                window.alert("완료되었습니다.");
                navigate(-1);
            }
        } catch(error) {
            window.alert("오류 발생!", error);
            console.error(error);
        }
    }

    return (
        <div>
            <Nav />
            <div className={styles.page}>
                <div className={styles.bigTitle}>게시글 작성</div>
                <div className={styles.brief}>
                    <div className={styles.imageUpload}>
                        {image.url && <img className={styles.image} src={image.url} alt="Preview"/>}
                        <input type="file" accept="image/*"
                               onChange={handleImgChange} />
                    </div>
                    <div className={styles.info}>
                        {/* 제목 입력 */}
                        <Write_Title setTitle={setTitle} />
                        {/* 드롭다운 카테고리 선택 */}
                        <table className={styles.briefTable}>
                            <Write_Contest_Dropdown categoryList={categoryList}
                                                    handleDataChange={handleDataChange_Event} />
                            <Write_Calendar keyName={"startAt"}
                                            setData={handleDateChange_Param} />
                            <Write_Calendar keyName={"endAt"}
                                            setData={handleDateChange_Param} />
                        </table>
                    </div>
                </div>
                <div className={styles.body}>
                    <div className={styles.bigTitle}>본문</div>
                    <ReactQuill
                        className={styles.input}
                        value={content}
                        onChange={((value) => {
                            setContent(value);
                            handleDateChange_Param("content", value);
                        })}
                    />
                </div>
                <div className={styles.submitCancel}>
                    <button className={"blueButton"} onClick={upload}>신청</button>
                    <button className={"redButton"} onClick={() => {
                        if(window.confirm("취소하시겠습니까?")) { navigate(-1); }
                    }}>취소</button>
                </div>
            </div>
            <Footer />
        </div>
    );
}
export default Contest_Write;

/*
        try {

            const imgResponse = await axios.post("URL", formData, {
                headers: {'Content-Type': 'multipart/form-data'},
            });
        } catch(error) {
            console.error(error);
        }

            //이미지
    const addStore = () => {
        const url = "http://13.125.112.60:8080/api/v1/managers/stores"

        const formData = new FormData();
        formData.append('imageFile', storeThumbnail); //이미지파일 이름

        const data = {
            "name": store_name,
            "address":address,
            "callNumber":phoneNumber
        };
        console.log(data);
        console.log(storeThumbnail);



        try {

            console.log("post객체:", postData);
            const response = await axios.post("/contestPost", postData);
            if(response.status === 200) {
                //성공
                window.alert("완료되었습니다.");
                navigate(-1);
            }
        } catch(error) {

        }
    }
        */