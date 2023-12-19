//지원자 확인
import React, {useEffect, useState} from "react";
import Modal from 'react-modal';
import styles from "../css/modal.module.css";
import "../../Modal.css";
import "../css/buttons.css";
import axios from "axios";
import UserPage from "../../UserPage";
import app from "../../App";

//더미데이터
const partTemp = ["React", "Spring"];
const dummy = {
    React: [
        {
            userName: "닉네임",
            userID: 1
        },
        {
            userName:"닉네임2",
            userId:2
        }
    ],
    Spring: [
        {
            userName:"닉네임3",
            userId:3
        }
    ]
}

const Write_ApplyList = ({ postId, parts, applyListModalOpen, setApplyListModalOpen}) => {
    const [keyList, setKeyList] = useState();
    const [applicantList, setApplicantList] = useState([]);
    const [init, setInit] = useState(false);

    const getList = async() => {
        try {
            const response
                = await axios.get(`/apply/leader/${postId}`);
            const jsonData = response.data.responseDto.applyList;
            setKeyList(parts);
            setApplicantList(jsonData);
        } catch(error) {
            console.log("지원 내역 리스트 get 오류");
            //더미데이터 처리
            //setKeyList(partTemp);
            //setApplicantList(dummy);
        }
    }
    useEffect(() => {
        getList();
        setInit(true);
    }, []);

    useEffect(() => {
        console.log("데이터 변경됨");
        console.log("key:", keyList);
        console.log("appList:", applicantList);
    }, [keyList, applicantList]);

    //유저 페이지로 이동
    function moveToUserPage(userId) {
        const screenWidth = window.screen.width;
        const screenHeight = window.screen.height;

        // 새 창의 크기를 화면 크기의 50%로 설정하고, 가운데 정렬
        const newWindowWidth = screenWidth * 0.7;
        const newWindowHeight = screenHeight * 0.7;
        const leftPos = (screenWidth - newWindowWidth) / 2;
        const topPos = (screenHeight - newWindowHeight) / 2;
        window.open(`http://15.164.3.171:3000/userPage/${userId}`, "UserPage",
            "width=${newWindowWidth},height=${newWindowHeight},left=${leftPos},top=${topPos}");
    }

    //승인
    const approve = async(applyId) => {
        try {
            const response = await axios.put(`/apply/permit/${applyId}`);
            if(response.status !== 200) {
                window.alert("승인 처리 오류!");
                return;
            } else {
                window.alert("완료되었습니다");
                setApplyListModalOpen(false);
            }
        } catch(error) {
            console.log(error);
        }
    }
    //거절
    const deny = async(applyId) => {
        try {
            const url = `/apply/deny/${applyId}`;
            console.log(url);
            const response = await axios.put(url);
            if(response.status !== 200) {
                window.alert("거절 처리 오류!");
                return;
            } else {
                window.alert("완료되었습니다");
                setApplyListModalOpen(false);
            }
        } catch(error) {
            console.log(error);
        }
    }

    if(!init) return null;
    else {
        return (
            <Modal className={styles.modal}
                   isOpen={applyListModalOpen}
                   onRequestClose={() => setApplyListModalOpen(false)}
                   ariaHideApp={false}
            >
                <div className={"modal-header"}>지원자 목록</div>
                <div className={"modal-body"}>
                    <div className={styles.modalBigTitle}>
                        지원자 목록
                    </div>
                    <div className={styles.modalCategory}>
                        <table>
                            <thead>
                            <tr>
                                <th>닉네임</th>
                                <th>지원파트</th>
                                <th />
                                <th />
                            </tr>
                            </thead>
                            <tbody>
                            {applicantList.length === 0 ?
                                <tr className={styles.noList}>
                                    <td colSpan={4}>없음</td>
                                </tr>
                                :
                             applicantList.map((element, elementIndex) => (
                                <tr key={elementIndex}>
                                    {element.state !== "거절" && element.state !== "승인" ?
                                        <>
                                        <td onClick={() => moveToUserPage(element.userId)}>
                                            {element.nickName}
                                        </td>
                                        <td className={styles.specialTd}>
                                            {element.partName}
                                        </td>
                                        <td>
                                        <button className={"blueButton"}
                                            onClick={() =>
                                                approve(element.applyId)}>
                                        승인</button>
                                        </td>
                                        <td>
                                        <button className={"redButton"}
                                            onClick={() =>
                                                deny(element.applyId)}>
                                            거절</button>
                                        </td>
                                        </>
                                    : <></>
                                    }

                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className={styles.confirm}>
                        <button className={"close"}
                                onClick={() => setApplyListModalOpen(false)}>닫기</button>
                    </div>
                </div>
            </Modal>
        );
    }
};

export default Write_ApplyList;