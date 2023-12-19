import { Siren } from "../Siren";
import {useNavigate} from "react-router-dom";
import "../Modal.css";
import axios from "axios";

function WithdrawalModal({ showModal, onClose }) {
    const navigate = useNavigate();
    if (!showModal) {
        return null;
    }
    const withdrawal = async() =>{

        const response = await axios.delete(`/user/${localStorage.getItem('userId')}`);
        // 로그아웃 처리 후
        // ...

        // 로그아웃 상태로 변경
        localStorage.removeItem('userId');
        window.alert('탈퇴 완료되었습니다.');
        window.location.href = '/';
    }
    return (
        <div className="modal-overlay">
            <div className="modal">
                <div className="modal-content">
                    <div className="modal-header">
                        <Siren width={30} height={30} />
                        <p className="modalTitle">회원 탈퇴</p>
                        <span className="close" onClick={onClose}>&times;</span>
                    </div>
                    <div className="my-[40px]">
                        <p>회원 탈퇴 시 개인 정보는 모두 삭제됩니다.<br/><br/>탈퇴하시겠습니까?</p>
                    </div>
                    <button className="complete" onClick={withdrawal}>탈퇴하기</button>
                </div>
            </div>
        </div>
    );
}
export default WithdrawalModal;
