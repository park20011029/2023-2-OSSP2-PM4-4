import { useState, useEffect } from "react";
import { Siren } from "./Siren";
import "./Modal.css";
import "./ReportModal.css";
import axios from "axios";
function ReportModal({ showModal, item, category, onClose }) { //item => 리뷰면 리뷰 데이터, 이력서면 이력서 데이터 등
    const [targetNickName, setTargetNickName] = useState(null);
    const [targetId, setTargetId] = useState(null);
    const [title, setTitle] = useState(null);
    const [selectedOption, setSelectedOption] = useState(null);
    const [description, setDescription] = useState("");
    const handleOptionChange = (e) => {
        setSelectedOption(e.target.value); // 라디오 버튼 변경 시 선택된 옵션 변경
    };
    useEffect(() => {
        console.log("아이템 : ", item);
        console.log("카테고리 : ",category);
        if(category === '리뷰'){
            setTargetId(item.reviewerId);
            setTargetNickName(item.reviewer);
        }
        else if(category ==='공모전'){
            setTargetId(item.writerId);
            setTitle(item.title);
        }
        else if(category ==='빌딩'){
            setTargetId(item.writerId);
            setTitle(item.title);
            setTargetNickName(item.writer);
        }
        else if(category ==='유저'){
            setTargetId(item.targetId);
            setTargetNickName(item.targetName);
        }
        else if(category ==='이력서'){
            setTargetId(item.targetId);
            setTargetNickName(item.targetName);
            //신고자 아이디
        }
        if (!showModal) {
            setSelectedOption(null); // 모달이 닫힐 때 체크박스 초기화
            setDescription(null);
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
                    reviewId:item.reviewId, //item.postId
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
        else if(category ==='공모전'){
            try{
                const response = await axios.post('/contestPost/report', {
                    description: description,
                    reportReason: selectedOption,
                    reporterId:localStorage.getItem('userId'),
                    defendantId:targetId,
                    contestPostId:item.postId, //item.postId
                })
                if(response.status === 200){
                    window.alert("신고가 완료되었습니다.");
                    onClose();
                    window.location.reload();
                }
            }catch(error){
                console.error('error reporting contest post : error');
            }
        }
        else if(category==='빌딩'){
            try{
                const response = await axios.post('/buildingPost/report', {
                    description: description,
                    reportReason: selectedOption,
                    reporterId:localStorage.getItem('userId'),
                    defendantId:targetId,
                    contestPostId:item.postId, //item.postId
                })
                console.log(response);
                if(response.status === 200){
                    window.alert("신고가 완료되었습니다.");
                    onClose();
                    window.location.reload();
                }
            }catch(error){
                console.error('error reporting building post : error');
            }
        }
        else if(category==='유저'){
            try{
                const response = await axios.post('/user/report', {
                    description: description,
                    reportReason: selectedOption,
                    reporterId:localStorage.getItem('userId'),
                    defendantId:targetId,
                })
                if(response.status === 200){
                    window.alert("신고가 완료되었습니다.");
                    onClose();
                    window.location.reload();
                }
            }catch(error){
                console.error('error reporting user : error');
            }
        }
        else if(category === '이력서'){
            try{
                const response = await axios.post('/resume/report', {
                    description: description,
                    reportReason: selectedOption,
                    reporterId:localStorage.getItem('userId'),
                    defendantId:targetId,
                    resumeId:item.resumeId, //item.postId
                })
                if(response.status === 200){
                    window.alert("신고가 완료되었습니다.");
                    onClose();
                    window.location.reload();
                }
            }catch(error){
                console.error('error reporting resume : error');
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
                        <p>신고 대상 : &nbsp;&nbsp;&nbsp;{title ? <span>{title}</span> : <span></span>}{title&&targetNickName ? <span> : </span> : <span></span>}{targetNickName ? <span>{targetNickName}</span> : <span></span>}<br/><br/></p>
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