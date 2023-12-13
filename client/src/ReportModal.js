import { useState, useEffect } from "react";
import { Siren } from "./Siren";
import "./Modal.css";
import "./ReportModal.css";
import axios from "axios";
function ReportModal({ showModal, item, category, onClose }) { //item => 리뷰면 리뷰 데이터, 이력서면 이력서 데이터 등
    const [targetNickName, setTargetNickName] = useState(null);
    const [targetId, setTargetId] = useState(null);
    const [selectedOption, setSelectedOption] = useState(null);
    const [description, setDescription] = useState("");
    const handleOptionChange = (e) => {
        setSelectedOption(e.target.value); // 라디오 버튼 변경 시 선택된 옵션 변경
    };
    useEffect(() => {
        if(category ==='리뷰'){
             setTargetId(item.reviewerId);
             setTargetNickName(item.reviewer);
        }
        if (!showModal) {
            setSelectedOption(null); // 모달이 닫힐 때 체크박스 초기화
        }
    }, [showModal]);

    if (!showModal) {
        return null;
    }
    const report = async(category) =>{
        if(category === '리뷰'){
            try{
                const response = await axios.post('/review/report', {
                    description: description,
                    reportReason: selectedOption,
                    reporterId:localStorage.getItem('userId'),
                    defendantId:targetId,
                    reviewId:item.reviewId,
                })
                if(response.status === 200){
                    window.alert("신고가 완료되었습니다.");
                    onClose();
                    window.location.reload();
                }
            }catch(error){
                console.error('error reporting review : error');
            }

        }
    }
    return (
        <div className="modal-overlay">
            <div className="modal">
                <div className="modal-content">
                    <div className="modal-header">
                        <Siren width={30} height={30} />
                        <p className="modalTitle">신고하기</p>
                        <span className="close" onClick={onClose}>
              &times;
            </span>
                    </div>
                    <div id="reportModalBody" className="modal-body">
                        <p>신고 대상 : &nbsp;&nbsp;&nbsp;{targetNickName}<br/><br/></p>
                        <p>신고 항목 : &nbsp;&nbsp;&nbsp;{category}<br/><br/></p>
                        <p>신고 사유 : </p><br/>
                        <div id="reportReasons">
                            <label className="reportChecks">
                                <input
                                    type="radio"
                                    value="INAPPROPRIATE"
                                    checked={selectedOption === 'INAPPROPRIATE'}
                                    onChange={handleOptionChange}
                                />
                                부적절한 언행
                            </label>
                            <label className="reportChecks">
                                <input
                                    type="radio"
                                    value="FALSE_INFORMATION"
                                    checked={selectedOption === 'FALSE_INFORMATION'}
                                    onChange={handleOptionChange}
                                />
                                허위 정보 의심
                            </label>
                            <label className="reportChecks">
                                <input
                                    type="radio"
                                    value="REPETITIVE"
                                    checked={selectedOption === 'REPETITIVE'}
                                    onChange={handleOptionChange}
                                />
                                동일 내용 반복
                            </label>
                            <label className="reportChecks">
                                <input
                                    type="radio"
                                    value="ETC"
                                    checked={selectedOption === 'ETC'}
                                    onChange={handleOptionChange}
                                />
                                기타
                            </label>
                        </div>
                        <textarea value={description} onChange={(e)=>setDescription(e.target.value)} id="reportDetails" className="modalDetails" rows={10} placeholder="상세 신고 사유를 입력해주세요."></textarea>
                    </div>
                    <button className="complete" onClick={()=>report(category)}>확인</button>
                </div>
            </div>
        </div>
    );
}
export default ReportModal;
