import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import UserSideBar from "./UserSideBar";
import {useEffect, useState} from "react";
import axios from "axios";
import ReviewModal from "./ReviewModal";
import List_PageNumber from "../layout/List_PageNumber";
import './MyPageWriteReview.css';

function MyPageWriteReview() {
    const [writeList, setWriteList] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [wrPageInfo, setWrPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 2, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 10, //총 페이지 개수
    });
    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };
    const getWriteList = async () => {
        try {
            const response = await axios.get(
                `/myPage/pendingReview/${localStorage.getItem("userId")}?page=${
                    wrPageInfo.pageNumber - 1
                }&size=${wrPageInfo.pageSize}`
            );
            setWrPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: wrPageInfo.pageSize,
                pageLength: wrPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            console.log(response.data.responseDto.pendingReviews);
            setWriteList(response.data.responseDto.pendingReviews);
        } catch (error) {
            console.log("Error fetching review list data: ", error);
        }
    };
    useEffect(() => {
        getWriteList();
    }, [wrPageInfo.pageNumber]);
    return (
        <div>
            <Nav/>
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div className="container-top">
                        <h2>작성할 리뷰</h2>
                    </div>
                    <div>
                        {writeList.length ? (
                            writeList.map((item, index) => (
                                <div>
                                    <div className="writeReview-container">
                                        <div key={index} className="writeList">
                                            <div className="writeReviewContent">
                                                <div className="writeReviewProjectName">{item.projectName}</div>
                                                <div className="writeReviewReviewee">{item.reviewee}</div>
                                            </div>
                                            <button className="writeReviewButton" onClick={openModal}>리뷰 작성하기</button>
                                            <ReviewModal
                                                showModal={isModalOpen}
                                                review={item}
                                                projectName={item.projectName}
                                                onClose={closeModal}
                                            />
                                        </div>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="post-none">작성할 리뷰가 없습니다.</div>
                        )}
                        {writeList.length ? (
                            <div><List_PageNumber
                                pageInfo={wrPageInfo}
                                setPageInfo={setWrPageInfo}
                            /></div>
                        ):(<></>)}
                    </div>
                </div>
            </main>
            <Footer />
        </div>
    );
}
export default MyPageWriteReview;
