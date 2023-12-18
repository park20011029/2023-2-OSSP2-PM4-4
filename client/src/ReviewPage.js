import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import StarRate from "./layout/StarRate";

function ReviewPage(){
    const { id } = useParams();
    const [score, setScore] = useState(null);
    const [content, setContent] = useState(null);
    const [reviewDate, setReviewDate] = useState(null);
    const [reviewee, setReviewee] = useState(null);
    const [reviewer, setReviewer] = useState(null);
    const getReviewData = async () =>{
        try{
            const response = await axios.get(`/reviewReport/review/${id}`);
            console.log(response.data.responseDto);
            setScore(response.data.responseDto.score);
            setContent(response.data.responseDto.content);
            setReviewee(response.data.responseDto.reviewee);
            setReviewer(response.data.responseDto.reviewer);
            setReviewDate(response.data.responseDto.createAt);
        }catch(error){
            console.error("error fetching review report data : ", error);
        }
    }
    useEffect(() => {
        getReviewData();
    }, [id]);
    return(
        <div>
            <div className="review-container">
                <div className="reviews">
                    <div className="review-header">
                        <div className="star-ratings">
                            <StarRate value={score * 10} />
                        </div>
                        <div className="reviewedUser">To : {reviewee}</div>
                        <div className="reviewedUser">From : {reviewer}</div>
                        <div className="reviewedDate">{reviewDate}</div>
                    </div>
                    <div className="review-body">{content}</div>
                </div>
            </div>
        </div>
    )
}
export default ReviewPage;