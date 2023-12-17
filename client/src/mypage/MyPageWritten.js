import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import UserSideBar from "./UserSideBar";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./MyPageWritten.css";
import List_PageNumber from "../layout/List_PageNumber";

function MyPageWritten() {
    const [contestPosts, setContestPosts] = useState([]);
    const [buildingPosts, setBuildingPosts] = useState([]);
    const [contestPageInfo, setContestPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 3, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 2, //총 페이지 개수
    });
    const [buildingPageInfo, setBuildingPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 3, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 2, //총 페이지 개수
    });
    const navigate = useNavigate();
    function moveToContestPost(index) {
        navigate(`/contestInfoPostPage/${index}`);
    }
    function moveToBuildingPost(index) {
        navigate(`/contestTeamWriteView/${index}`);
    }

    const getContestPost = async () => {
        try {
            const response = await axios.get(
                `/myPage/contest/${localStorage.getItem("userId")}?page=${
                    contestPageInfo.pageNumber - 1
                }&size=${contestPageInfo.pageSize}`
            );
            setContestPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: contestPageInfo.pageSize,
                pageLength: contestPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            setContestPosts(response.data.responseDto.myContestPosts);
            console.log(response.data.responseDto);
        } catch (error) {
            console.log("Error fetching contest post data: ", error);
        }
    };
    useEffect(() => {
        getContestPost();
    }, [contestPageInfo.pageNumber]);

    const getBuildingPost = async () => {
        try {
            const response = await axios.get(
                `/myPage/building/${localStorage.getItem("userId")}?page=${
                    buildingPageInfo.pageNumber - 1
                }&size=${buildingPageInfo.pageSize}`
            );
            setBuildingPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: buildingPageInfo.pageSize,
                pageLength: buildingPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });

            setBuildingPosts(response.data.responseDto.myBuildingPosts);
            console.log("myBuilding", response.data.responseDto);
        } catch (error) {
            console.log("Error fetching building post data: ", error);
        }
    };
    useEffect(() => {
        getBuildingPost();
    }, [buildingPageInfo.pageNumber]);
    return (
        <div>
            <Nav />
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div id="contestPost-container" className="post-container">
                        <div className="container-top">
                            <h2>공모전 등록 신청</h2>
                        </div>
                        <div >
                            {contestPosts.length ? (
                                contestPosts.map((item, index) => (
                                    <div className="post">
                                        <div
                                            className="Post-header"
                                            key={index}
                                            onClick={() => moveToContestPost(item.contestId)}
                                        >
                                            {item.title}<br/>
                                        </div>
                                        <div className="Post-body">
                                            {item.startAt}~{item.endAt}
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <div className="post-none">
                                    작성한 공모전 게시글이 없습니다.
                                </div>
                            )}
                            {contestPosts.length ? (
                                <div><List_PageNumber
                                    pageInfo={contestPageInfo}
                                    setPageInfo={setContestPageInfo}
                                /></div>
                            ):(<></>)}
                        </div>
                    </div>
                    <div id="buildingPost-container" className="post-container">
                        <div className="container-top">
                            <h2>팀원 모집</h2>
                        </div>
                        <div>
                            {buildingPosts.length ? (
                                buildingPosts.map((item, index) => (
                                    <div className="post">
                                        <div
                                            className="Post-header"
                                            key={index}
                                            onClick={() => moveToBuildingPost(item.buildingId)}
                                        >
                                            {item.title}
                                        </div>
                                        <div className="Post-body">
                                            {item.creatAt}
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <div className="post-none">작성한 팀원 모집글이 없습니다.</div>
                            )}
                            {buildingPosts.length ? (
                                <div><List_PageNumber
                                    pageInfo={buildingPageInfo}
                                    setPageInfo={setBuildingPageInfo}
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
export default MyPageWritten;
