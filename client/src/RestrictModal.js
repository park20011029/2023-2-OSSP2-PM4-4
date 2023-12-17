import "./Modal.css";
import "./RestrictModal.css";
import { useEffect, useState } from "react";
import axios from "axios";

function RestrictModal({ showModal, category, item, onClose }) {
    const [restrictionType, setRestrictionType] = useState(""); // 추가된 부분: 제재 유형 상태 관리
    const [selectedDuration, setSelectedDuration] = useState(""); // 추가된 부분: 선택된 기간 상태 관리

    const handleRestrictionChange = (e) => {
        setRestrictionType(e.target.value);
    };

    const handleDurationChange = (e) => {
        setSelectedDuration(e.target.value);
    };

    if (!showModal) {
        return null;
    }

    let durationDropdown = null; // 추가된 부분: 드롭다운 초기화

    // 추가된 부분: 이용 제한 기간을 선택할 수 있는 드롭다운 박스 렌더링
    if (restrictionType === "penalty") {
        durationDropdown = (
            <select className="h-[24px] ml-[15px] border-[1px] border-[#000000] rounded-[8px] px-[3px] text-[13px]" value={selectedDuration} onChange={handleDurationChange}>
                {/*<option value="">이용 제한 기간 선택</option>*/}
                <option value={1}>1일</option>
                <option value={7}>7일</option>
                <option value={30}>30일</option>
            </select>
        );
    }

    const handleConfirm = async(category,restrict,reportId) => {
        if(category === 'review'){
            if(restrict === 'penalty'){
                try{
                    const response1 = await axios.put(`/reviewReport/execute/${reportId}/${selectedDuration}`);
                    if(response1.status === 200){
                        window.alert("리뷰 작성자 제재 완료");
                        onClose();
                        const response2 = await axios.put(`/reviewReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error executing reviewed user : ', error);
                }
            }
            else if(restrict ==='expel'){
                try{
                    const response1 = await axios.put(`/reviewReport/expel/${reportId}`);
                    if(response1.status === 200){
                        window.alert("리뷰 작성자 추방 완료");
                        onClose();
                        const response2 = await axios.put(`/reviewReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error expelling reviewed user : ', error);
                }
            }
        }
        else if(category === 'contest'){
            if(restrict === 'penalty'){
                try{
                    const response1 = await axios.put(`/contestPostReport/execute/${reportId}/${selectedDuration}`);
                    if(response1.status === 200){
                        window.alert("공모전 게시글 작성자 제재 완료");
                        onClose();
                        const response2 = await axios.put(`/contestPostReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error executing contest posted user : ', error);
                }
            }
            else if(restrict ==='expel'){
                try{
                    const response1 = await axios.put(`/contestPostReport/expel/${reportId}`);
                    if(response1.status === 200){
                        window.alert("공모전 게시글 작성자 추방 완료");
                        onClose();
                        const response2 = await axios.put(`/contestPostReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error expelling contest posted user : ', error);
                }
            }
        }
        else if(category === 'building'){
            if(restrict === 'penalty'){
                try{
                    const response1 = await axios.put(`/buildingPostReport/execute/${reportId}/${selectedDuration}`);
                    if(response1.status === 200){
                        window.alert("빌딩 게시글 작성자 제재 완료");
                        onClose();
                        const response2 = await axios.put(`/buildingPostReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error executing building posted user : ', error);
                }
            }
            else if(restrict ==='expel'){
                try{
                    const response1 = await axios.put(`/buildingPostReport/expel/${reportId}`);
                    if(response1.status === 200){
                        window.alert("빌딩 게시글 작성자 추방 완료");
                        onClose();
                        const response2 = await axios.put(`/buildingPostReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error expelling building posted user : ', error);
                }
            }
        }
        else if(category === 'user'){
            if(restrict === 'penalty'){
                try{
                    const response1 = await axios.put(`/userReport/execute/${reportId}/${selectedDuration}`);
                    if(response1.status === 200){
                        window.alert("사용자 제재 완료");
                        onClose();
                        const response2 = await axios.put(`/userReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error executing reported user : ', error);
                }
            }
            else if(restrict ==='expel'){
                try{
                    const response1 = await axios.put(`/userReport/expel/${reportId}`);
                    if(response1.status === 200){
                        window.alert("사용자 추방 완료");
                        onClose();
                        const response2 = await axios.put(`/userReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error expelling reported user : ', error);
                }
            }
        }
        else if(category === 'resume'){
            if(restrict === 'penalty'){
                try{
                    const response1 = await axios.put(`/resumeReport/execute/${reportId}/${selectedDuration}`);
                    if(response1.status === 200){
                        window.alert("사용자(이력서) 제재 완료");
                        onClose();
                        const response2 = await axios.put(`/resumeReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error executing resume reported user : ', error);
                }
            }
            else if(restrict ==='expel'){
                try{
                    const response1 = await axios.put(`/resumeReport/expel/${reportId}`);
                    if(response1.status === 200){
                        window.alert("사용자(이력서) 추방 완료");
                        onClose();
                        const response2 = await axios.put(`/resumeReport/delete/${reportId}`);
                        if(response2.status === 200){
                            window.location.reload();
                        }
                    }
                }catch(error){
                    console.error('error expelling resume reported user : ', error);
                }
            }
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal">
                <div className="modal-content">
                    <div className="modal-header">
                        <p className="modalTitle">회원 제재</p>
                        <span className="close" onClick={onClose}>
                            &times;
                        </span>
                    </div>
                    <div id="restrictModalBody" className="modal-body">
                        <p>제재 대상 : &nbsp;&nbsp;&nbsp;{item.defendantNickName}</p>
                        <br/>
                        <div className="flex items-center">
                            <p>제재 내용 : &nbsp;&nbsp;&nbsp;</p>
                            <input className="mx-[10px] my-0"
                                type="radio"
                                name="restrict"
                                value="expel"
                                onChange={handleRestrictionChange}
                            />
                            추방
                            <input className="mx-[10px] my-0"
                                type="radio"
                                name="restrict"
                                value="penalty"
                                onChange={handleRestrictionChange}
                            />
                            기간제 이용 제한

                        {durationDropdown} {/* 추가된 부분: 드롭다운 박스 표시 */}
                        </div><br/>
                    </div>
                    <button className="complete" onClick={()=>handleConfirm(category,restrictionType,item.reportId)}>확인</button>
                </div>
            </div>
        </div>
    );
}
export default RestrictModal;
