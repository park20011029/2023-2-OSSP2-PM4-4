import React, {useEffect, useState} from "react";
import Modal from 'react-modal';
import styles from "../css/modal.module.css";
import axios from "axios";


const Write_Apply = ({ postInfo, applyModalOpen, setApplyModalOpen, id }) => {
    const initialState = {number:0, name:""};
    const [applyInfo, setApplyInfo] = useState(initialState);
    const [isSelected, setIsSelected] = useState(false);

    const handleApply = (element) => {
        setApplyInfo((prevApplies) => {
            const isPartIdIncluded = prevApplies.number === element.partId;

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

    //Todo error: 500
    const submit = () => {
        if(!window.confirm(`현재 선택한 파트: ${applyInfo.name}\n지원하시겠습니까?`))
            return;
        const send = async() => {
            try {
                const transferData = {userId:id, partId:applyInfo.number};
                console.log(transferData);
                const response = await axios.post(`/apply`, transferData);
                if(response === 200) {
                    console.log("지원 내역 전송 완료.");
                    window.alert("지원 완료되었습니다.");
                    setApplyModalOpen(false);
                }
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
            <div className={styles.modalTitle}>
                지원하려는 모집 게시글: {postInfo.title}
            </div>
            <div className={styles.category}>
                <label>지원하려는 카테고리 선택</label>
                <table>
                    <thead>
                    <tr>
                        <th>정보1</th>
                        <th>정보2</th>
                        <th>모집된 인원</th>
                        <th>최대 모집 인원</th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    {Object.entries(postInfo.partList).map(([key, list]) => {
                        return ( list.map((element) => (
                            <tr>
                                <td>{key}</td>
                                <td>{element.partName}</td>
                                <td>{element.currentApplicant}</td>
                                <td>{element.maxApplicant}</td>
                                <td>
                                    <input type={"checkbox"}
                                           onChange={() => handleApply(element)}
                                           checked={applyInfo.number === element.partId}
                                           disabled={isSelected && applyInfo.number !== element.partId}
                                    />
                                </td>
                            </tr>
                        )));
                    })}
                    </tbody>
                </table>
            </div>
            <div className={styles.result}>
                <label>현재 지원한 카테고리</label>
                <label>{applyInfo.name}</label>
            </div>
            <div className={styles.confirm}>
                <button onClick={submit}>지원</button>
                <button onClick={() => setApplyModalOpen(false)}>취소</button>
            </div>
        </Modal>
    );
};

export default Write_Apply;
