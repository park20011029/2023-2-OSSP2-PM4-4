import { useEffect, useState } from "react";
import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import UserSideBar from "./UserSideBar";
import ReportModal from "../ReportModal";
import "./MyPageReview.css";
import "../Modal.css";
import axios from "axios";
import List_PageNumber from "../layout/List_PageNumber";
import StarRate from "../layout/StarRate";
import { Siren } from "../Siren";

function MyPageReview() {
    const [reviewList, setReviewList] = useState([]);
    const [writtenList, setWrittenList] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [reviewPageInfo, setReviewPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 4, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 10, //총 페이지 개수
    });
    const [writtenPageInfo, setWrittenPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 2, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 10, //총 페이지 개수
    });
    const getReview = async () => {
        try {
            const response = await axios.get(
                `/myPage/myReview/${localStorage.getItem("userId")}?page=${
                    reviewPageInfo.pageNumber - 1
                }&size=${reviewPageInfo.pageSize}`
            );
            setReviewPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: reviewPageInfo.pageSize,
                pageLength: reviewPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            setReviewList(response.data.responseDto.myReviews);
            console.log("받은", response.data.responseDto);
        } catch (error) {
            console.log("Error fetching review list data: ", error);
        }
    };
    useEffect(() => {
        getReview();
    }, [reviewPageInfo.pageNumber]);

    const getWritten = async () => {
        try {
            const response = await axios.get(
                `/myPage/writtenReview/${localStorage.getItem("userId")}?page=${
                    writtenPageInfo.pageNumber - 1
                }&size=${writtenPageInfo.pageSize}`
            );
            setWrittenPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: writtenPageInfo.pageSize,
                pageLength: writtenPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            setWrittenList(response.data.responseDto.writtenReviews);
            console.log("작성한", response.data.responseDto);
        } catch (error) {
            console.log("Error fetching written review list data: ", error);
        }
    };
    useEffect(() => {
        getWritten();
    }, [writtenPageInfo.pageNumber]);
    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <div>
            <Nav />
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div>
                        <div className="review-area">
                            <div className="container-top">
                                <h2>내가 받은 리뷰</h2>
                            </div>
                            <div>
                                {reviewList.length ? (
                                    reviewList.map((item, index) => (
                                        <div className="review-container">
                                            <div key={index} className="reviews">
                                                <div className="review-header">
                                                    <div className="star-ratings">
                                                        <StarRate value={(item.score) * 20} />

                                                    </div>
                                                    <div className="report">
                                                        <Siren width={20} height={20} /><button onClick={openModal}>신고</button>
                                                        <ReportModal
                                                            showModal={isModalOpen}
                                                            item={item}
                                                            category={"리뷰"}
                                                            onClose={closeModal}
                                                        />
                                                    </div>
                                                    <div className="reviewedUser">{item.reviewer}</div>
                                                    <div className="reviewedDate">{item.createDate}</div>
                                                </div>
                                                <div className="review-body">{item.content}</div>
                                            </div>
                                        </div>
                                    ))
                                ) : (
                                    <div className="post-none">받은 리뷰가 없습니다.</div>
                                )}
                                {reviewList.length ? (
                                    <div><List_PageNumber
                                        pageInfo={reviewPageInfo}
                                        setPageInfo={setReviewPageInfo}
                                    /></div>
                                ):(<></>)}
                            </div>
                        </div>
                        <div className="review-area">
                            <div className="container-top">
                                <h2>내가 쓴 리뷰</h2>
                            </div>
                            <div>
                                {writtenList.length ? (
                                    writtenList.map((item, index) => (
                                        <div className="review-container">
                                            <div key={index} className="reviews">
                                                <div className="review-header">
                                                    <div className="star-ratings">
                                                        {console.log(item.score)}
                                                        <StarRate value={(item.score) * 20} />
                                                    </div>
                                                    <div className="reviewedUser">{item.reviewer}</div>
                                                    <div className="reviewedDate">{item.createDate}</div>
                                                </div>
                                                <div className="review-body">{item.content}</div>
                                            </div>
                                        </div>
                                    ))
                                ) : (
                                    <div className="post-none">작성한 리뷰가 없습니다.</div>
                                )}
                                {writtenList.length ? (
                                    <div><List_PageNumber
                                        pageInfo={writtenPageInfo}
                                        setPageInfo={setWrittenPageInfo}
                                    /></div>
                                ):(<></>)}
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <Footer />
        </div>
    );
}
export default MyPageReview;
