//지원하기
import React, {useEffect, useState} from "react";
import Modal from 'react-modal';
import styles from "../css/modal.module.css";
import axios from "axios";


const Write_Apply = ({ postInfo,postId, applyModalOpen, setApplyModalOpen, id }) => {
    const initialState = {number:0, name:""};
    const [applyInfo, setApplyInfo] = useState(initialState);
    const [isSelected, setIsSelected] = useState(false);

    const handleApply = (element) => {
        setApplyInfo((prevApplies) => {
            const isPartIdIncluded = prevApplies.number === element.partId;
            const isAvailable = element.currentApplicant < element.maxApplicant;
            if (isPartIdIncluded) {
                setIsSelected(false);
                return initialState;
            } else {
                setIsSelected(true);
                return {
                    number: element.partId,
                    name: element.partName,
                };
            }
        });
    };

    const submit = () => {
        if(!window.confirm(`현재 선택한 파트: ${applyInfo.name}\n지원하시겠습니까?`))
            return;
        if(id === postInfo.writerId) {
            window.alert("작성자는 자신의 게시글에 지원할 수 없습니다.");
            return;
        }
        const send = async() => {
            try {
                const transferData = {userId:id, partId:applyInfo.number};
                console.log(transferData);
                const response = await axios.post(`/apply/${postId}`, transferData);
                console.log("지원 내역 전송 완료.");
                window.alert("지원 완료되었습니다.");
                setApplyModalOpen(false);
            } catch(error) {
                console.log(error);
                window.alert("오류 발생!");
                return;
            }
        }
        send();
    }

    useEffect(() => {
        console.log("지원정보 변경됨", applyInfo);
    }, [applyInfo]);

    return (
        <Modal className={styles.modal}
               isOpen={applyModalOpen}
               onRequestClose={() => setApplyModalOpen(false)}
               ariaHideApp={false}
        >
            <div className={"modal-header"}>지원하기</div>
            <div className={"modal-body"}>
                <div className={styles.modalBigTitle}>
                    지원하려는 모집 게시글: {postInfo.title}
                </div>
                <div>
                    <label className={styles.modalBigTitle}>
                        현재 지원한 카테고리: {applyInfo.name}</label>
                    <table className={styles.modalCategory}>
                        <thead>
                        <tr>
                            <th>파트</th>
                            <th>세부 파트</th>
                            <th>모집된 인원</th>
                            <th>최대 모집 인원</th>
                            <th>선택</th>
                        </tr>
                        </thead>
                        <tbody>
                        {Object.entries(postInfo.partList).map(([key, list]) => {
                            return ( list.map((element) => (
                                <tr onClick={() => handleApply(element)}>
                                    <td>{key}</td>
                                    <td>{element.partName}</td>
                                    <td>{element.currentApplicant}</td>
                                    <td>{element.maxApplicant}</td>
                                    <td>
                                        <input type={"checkbox"}
                                               checked={applyInfo.number === element.partId}
                                               disabled={element.currentApplicant === element.maxApplicant
                                                   || isSelected && applyInfo.number !== element.partId}
                                        />
                                    </td>
                                </tr>
                            )));
                        })}
                        </tbody>
                    </table>
                </div>
                <div className={styles.confirm}>
                    <button className={styles.complete} onClick={submit}>지원</button>
                    <button className="close" onClick={() => setApplyModalOpen(false)}>닫기</button>
                </div>
            </div>
        </Modal>
    );
};

export default Write_Apply;