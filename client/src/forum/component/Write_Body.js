//팀원 모집글 본문 컴포넌트
import React, {useState} from 'react';
import ReactQuill from 'react-quill';
import {useNavigate} from "react-router-dom";
import 'react-quill/dist/quill.snow.css';
import '../css/buttons.css';
import styles from '../css/Team_Write(Post).module.css';
import axios from "axios";

const Write_Body = ({ setContent, setReward, userId, submit }) => {
    const [content, setContent_v] = useState("");
    const [useReward, setUseReward] = useState(false);
    const navigate = useNavigate();

    const handleRewardChange = async() => {
        //Todo: 리워드 사용 가능여부 검사
        if(useReward === false) {
            try {
                const response = axios.get(`/user/${userId}`);
                const jsonData = response.data.responseDto;
                const reward = jsonData.point;
                if(reward >= 300)
                    setUseReward(true);
                else {
                    window.alert("포인트 잔액이 부족합니다.");
                    return;
                }
            } catch(error) {
                window.alert("유저정보 조회 중 오류 발생!");
                return;
            }
        }
        else
            setUseReward(false);
        setReward(useReward);
    };

    return (
        <div className={styles.body}>
            {/* 본문 입력 창 */}
            <ReactQuill
                className={styles.input}
                value={content}
                onChange={(value) => {
                    setContent_v(value);
                    setContent(value);
                }}
            />
            {/* 리워드 사용버튼 */}
            <button className={"greyButton"}>
                <div className={styles.reward}>
                    <input type="checkbox" checked={useReward} onChange={handleRewardChange} />
                    <div className={styles.description}>
                        <label>리워드 사용</label>
                        <label className={styles.des}>n일간 게시판 상단에 고정됩니다.</label>
                    </div>
                </div>
            </button>
            {/* 작성,취소 버튼 */}
            <div className={styles.submitCancel}>
                <button className={"blueButton"}
                        onClick={submit}>작성</button>
                <button className={"redButton"}
                        onClick={() => {
                            if(window.confirm('취소하시겠습니까?')) {
                                alert("취소되었습니다.");
                                navigate(-1);
                            }
                }}>취소</button>
            </div>
        </div>
    );
};

export default Write_Body;
