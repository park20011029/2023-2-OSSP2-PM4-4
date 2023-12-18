//지원자 확인
import React, {useEffect, useState} from "react";
import Modal from 'react-modal';
import styles from "../css/modal.module.css";
import "../css/buttons.css";
import axios from "axios";

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

const Write_ApplyApprovedList = ({ postId, parts, applyApprovedListModalOpen, setApplyApprovedListModalOpen}) => {
    const [keyList, setKeyList] = useState([]);
    const [applicantList, setApplicantList] = useState([]);
    const [init, setInit] = useState(false);

    const getList = async() => {
        try {
            const response = await axios.get(`/apply/post/${postId}`);
            const jsonData = response.data.responseDto.applyList;
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

    const moveToProfile = (userId) => {
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

    if(init === false) return;
    else {
        return (
            <Modal className={styles.modal}
                   isOpen={applyApprovedListModalOpen}
                   onRequestClose={() => setApplyApprovedListModalOpen(false)}
                   ariaHideApp={false}
            >
                <div className={"modal-header"}>승인된 지원자 목록</div>
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
                            </tr>
                            </thead>
                            <tbody>
                            {applicantList.map((element, elementIndex) => (
                                <tr key={elementIndex}>
                                    <td>{element.nickName}</td>
                                    <td className={styles.specialTd}>{element.partName}</td>
                                    <td>
                                        <button className="yellowButton"
                                                onClick={() => moveToProfile(element.userId)}>프로필 이동</button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className={styles.confirm}>
                        <button className={"close"}
                                onClick={() => setApplyApprovedListModalOpen(false)}>닫기</button>
                    </div>
                </div>
            </Modal>
        );
    }
};

export default Write_ApplyApprovedList;