import { useState, useEffect } from "react";
import axios from "axios";
import "../Modal.css";
import StarTest from "../layout/StarTest";
import { MdOutlineRateReview } from "react-icons/md";

function ReviewModal({ showModal, review, onClose }) {
    const [rating, setRating] = useState(0.0); // 초기 별점은 0으로 설정
    const [reviewContent, setReviewContent] = useState("");
    const writeReview = async() =>{
        try{
            const response = await axios.put(`/review/${review.reviewId}`,{
                reviewId: review.reviewId,
                content: reviewContent,
                score: rating*2,
            })
            if(response.status === 200){
                window.alert("리뷰가 등록되었습니다.");
                onClose();
                window.location.reload();
            }
        }catch (error){
            console.error("error writing review : ", error);
        }
    }
    if (!showModal) {
        return null;
    }
    return (
        <div className="modal-overlay">
            <div className="modal">
                <div className="modal-content">
                    <div className="modal-header">
                        <MdOutlineRateReview />
                        <p className="modalTitle">리뷰 작성</p>
                        <span className="close" onClick={onClose}>
              &times;
            </span>
                    </div>
                    <div id="reviewModalBody" className="modal-body">
                        <p>리뷰 대상 : &nbsp;&nbsp;&nbsp;{review.reviewee}<br/><br/></p>
                        <p>참여 프로젝트 : &nbsp;&nbsp;&nbsp;{review.projectName}<br/><br/></p>
                        <div className="flex mb-[15px]">별점 남기기 : &nbsp;&nbsp;&nbsp; <StarTest rating={rating} setRating={setRating}/></div>
                        <textarea value={reviewContent} onChange={(e)=>setReviewContent(e.target.value)} className="modalDetails" rows={10} placeholder="상세한 평가를 남겨주세요."/>
                    </div>
                    <button className="complete" onClick={writeReview}>확인</button>
                </div>
            </div>
        </div>
    );
}
export default ReviewModal;
