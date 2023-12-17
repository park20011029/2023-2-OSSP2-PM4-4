import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import AdminSideBar from "./AdminSideBar";
import axios from "axios";
import {useEffect, useState} from "react";
import List_PageNumber from "../layout/List_PageNumber";
import { useNavigate } from "react-router-dom";
import './ReportList.css';
import RestrictModal from "../RestrictModal";
function ContestReportList() {
    const navigate = useNavigate();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [contestReportList, setContestReportList] = useState([]);
    const [contestReportPageInfo, setContestReportPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 2, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 10, //총 페이지 개수
    });
    const getContestReport = async () => {
        try{
            const response = await axios.get(`/contestPostReport?page=${contestReportPageInfo.pageNumber-1}&size=${contestReportPageInfo.pageSize}`);
            setContestReportList(response.data.responseDto.reportTitles);
            setContestReportPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: contestReportPageInfo.pageSize,
                pageLength: contestReportPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            console.log(response.data.responseDto);
        }catch(error){
            console.error("Error fetching contest report list data: ", error);
        }
    }
    const deleteContestReport = async(reportId) => {
        const confirmed = window.confirm("삭제하시겠습니까?");
        if(confirmed){
            try{
                const response = await axios.put(`/contestPostReport/delete/${reportId}`);
                if(response.status === 200){
                    window.alert('공모전 신고 내역 삭제 완료');
                    window.location.reload();
                }
            }catch(error){
                console.error('error deleting contest report data : ', error);
            }
        }
    }
    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };
    useEffect(() => {
        getContestReport();
    }, [contestReportPageInfo.pageNumber]);

    function moveToContestPost(index) {
        navigate(`/contestInfoPostPage/${index}`);
    }

    return (
        <div>
            <Nav />
            <main className="flex-element">
                <AdminSideBar />
                <div className="main-container">
                    <div className="container-top">
                        <h2>공모전 신고 내역</h2>
                    </div>
                    <div className="container-body">
                        <div className="report-info">
                            <div>신고 대상</div>
                            <div className="ml-[20px]">신고자</div>
                            <div>신고 사유</div>
                            <div className="mr-[330px]">신고 일자</div>
                        </div>
                        <div>
                            {contestReportList.length ? (
                                contestReportList.map((item, index) => (
                                    <div className="report-container">
                                        <div key={index} className="report-content">
                                                <div className="defendant">{item.defendantNickName}</div>
                                                <div className="reporter">{item.reporterNickName}</div>
                                            <div className="reportReason">{item.reportReason}</div>
                                                <div className="reportDate">{item.createAt}</div>
                                            <div className="moveTo"  onClick={()=>moveToContestPost(item.contentId)}>이동</div>
                                            <div className="restrict">
                                                <button onClick={openModal}>제재</button>
                                                <RestrictModal
                                                    showModal={isModalOpen}
                                                    category="contest"
                                                    item={item}
                                                    onClose={closeModal}
                                                /></div>
                                            <div className="deleteReport" onClick={()=>deleteContestReport(item.reportId)}>삭제</div>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <div className="post-none">신고된 공모전 게시글이 없습니다.</div>
                            )}
                            {contestReportList.length ? (
                                <div className="space"><List_PageNumber
                                    pageInfo={contestReportPageInfo}
                                    setPageInfo={setContestReportPageInfo}
                                /></div>
                            ):(<></>)}
                        </div>
                    </div>
                </div>
            </main>
            <Footer />
        </div>
    );
}
export default ContestReportList;
