//지원자 확인
import React, {useEffect, useState} from "react";
import Modal from 'react-modal';
import styles from "../css/modal.module.css";
import axios from "axios";
import UserPage from "../../UserPage";

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
    const [keyList, setKeyList] = useState([]);
    const [applicantList, setApplicantList] = useState({});
    const [init, setInit] = useState(false);

    const getList = async() => {
        try {
            const response = await axios.get(`/apply/list/${postId}`);
            const jsonData = response.data.responseDto;
            setKeyList(parts);
            setApplicantList(jsonData);
        } catch(error) {
            console.log("지원 내역 리스트 get 오류");
            //더미데이터 처리
            setKeyList(partTemp);
            setApplicantList(dummy);
        }
    }
    useEffect(() => {
        getList();
        setInit(true);
    }, []);

    //유저 페이지로 이동
    function moveToUserPage(userId) {
        const screenWidth = window.screen.width;
        const screenHeight = window.screen.height;

        // 새 창의 크기를 화면 크기의 50%로 설정하고, 가운데 정렬
        const newWindowWidth = screenWidth * 0.7;
        const newWindowHeight = screenHeight * 0.7;
        const leftPos = (screenWidth - newWindowWidth) / 2;
        const topPos = (screenHeight - newWindowHeight) / 2;
        window.open(`http://localhost:3000/userPage/${userId}`, "UserPage",
            "width=${newWindowWidth},height=${newWindowHeight},left=${leftPos},top=${topPos}");
    }

    //승인
    const approve = () => {
        //Todo
    }
    //거절
    const deny = () => {
        //Todo
    }

    if(init === false) return;
    else {
        return (
            <Modal className={styles.modal}
                   isOpen={applyListModalOpen}
                   onRequestClose={() => setApplyListModalOpen(false)}
                   ariaHideApp={false}
            >
                <div className={styles.modalTitle}>
                    지원자 목록
                </div>
                <div className={styles.category}>
                    <table>
                        <thead>
                        <tr>
                            <th>지원파트</th>
                            <th>닉네임</th>
                            <th />
                            <th />
                        </tr>
                        </thead>
                        <tbody>
                        {keyList.map((key) => (
                            applicantList[key].map((element, elementIndex) => (
                                <tr key={elementIndex}>
                                    {elementIndex === 0 && (
                                        <td className={styles.specialTd}
                                            rowSpan={applicantList[key].length}>{key}</td>
                                    )}
                                    <td onClick={() => moveToUserPage(element.userId)}>{element.userName}</td>
                                    <td>
                                        <button onClick={() => approve()}>승인</button>
                                    </td>
                                    <td>
                                        <button onClick={() => deny()}>거절</button>
                                    </td>
                                </tr>
                            ))
                        ))}
                        </tbody>
                    </table>
                </div>
                <div>
                    <button onClick={() => setApplyListModalOpen(false)}>닫기</button>
                </div>
            </Modal>
        );
    }
};

export default Write_ApplyList;