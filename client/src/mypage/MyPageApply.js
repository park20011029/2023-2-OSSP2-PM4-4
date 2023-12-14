import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import UserSideBar from "./UserSideBar";
import "./MyPageApply.css";
import axios from "axios";
import {useEffect, useState} from "react";
import List_PageNumber from "../layout/List_PageNumber";
import {useNavigate} from "react-router-dom";

function MyPageApply() {
    const navigate = useNavigate();
    const [applyList, setApplyList] = useState([]);
    const [applyPageInfo, setApplyPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 4, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 10, //총 페이지 개수
    });
    function moveToBuildingPost(index) {
        navigate(`/contestTeamWriteView/${index}`);
    }
    const getApply= async() => {
        try{
            const response = await axios.get(`myPage/apply/${localStorage.getItem('userId')}?page=${
                applyPageInfo.pageNumber - 1
            }&size=${applyPageInfo.pageSize}`);
            setApplyPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: applyPageInfo.pageSize,
                pageLength: applyPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            setApplyList(response.data.responseDto.applyList);
            console.log(response.data.responseDto.applyList);
        }catch(error){
            console.log("Error fetching apply list data: ", error);
        }
    }
    useEffect(() => {
        getApply();
    }, [applyPageInfo.pageNumber]);
    let borderClassName = '';

    return (
        <div>
            <Nav />
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div className="container-top">
                        <h2>지원 내역</h2>
                    </div>
                    <div className="container-body">
                        <div className="apply-info">
                            <div>글 제목</div>
                            <div>지원 파트</div>
                            <div>승인 상태</div>
                        </div>
                        <div>
                            {applyList.length ? (
                                applyList.map((item, index) => (
                                    <div>
                                    <div className="apply-container">
                                        <div key={index} className="apply-content">
                                            <div className="apply-title" onClick={() => moveToBuildingPost(item.buildingPostId)}>
                                                {item.buildingPost}
                                            </div>
                                            <div className='apply-part'>
                                                {item.partName}
                                            </div>
                                            <div className={`apply-part${item.state}`}>
                                                {item.state}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                ))
                            ):(<div className="post-none">지원 내역이 없습니다.</div>)}
                            {applyList.length ? (
                                <div id="space"><List_PageNumber
                                    pageInfo={applyPageInfo}
                                    setPageInfo={setApplyPageInfo}
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
export default MyPageApply;
