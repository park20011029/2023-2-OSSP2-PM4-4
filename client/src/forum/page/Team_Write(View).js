//공모전 팀원 모집글(보기)
import React, {useEffect, useState} from 'react';
import {json, useNavigate, useParams} from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import styles from "../css/Team_Write(View).module.css";
import "../css/buttons.css";
import {team_CategoryKOR} from "../component/axios_category";
import axios from "axios";
import Team_WriteEdit from "./Team_Write(Edit)";
import Write_Apply from "../modal/Write_Apply";
import Write_ApplyList from "../modal/Write_ApplyList";
import Write_ApplyApprovedList from "../modal/Write_ApplyApprovedList";
import {Siren} from "../../Siren";
import ReportModal from "../../ReportModal";

//DUMMY DATA
const write = {
    "buildingPost": {
        title: "제목",
        content: (
            <div>
                <div>안녕하세요! 제 8회 파크랜드 대학생 마케팅 공모전 팀원을 모집합니다.</div>
                <div>&nbsp;</div>
                <div>https://m.parkland.co.kr/bbs/bbs.asp?bbsId=ZWO2&amp;pType=view&amp;idx=17194</div>
                <div>&nbsp;</div>
                <div>🔻 응모 주제</div>
                <div>응모 주제 1인 '파크랜드의 미래 성장 동력 확보를 위한 신제품/신사업 제안'로 진행 예정</div>
                <div>&nbsp;</div>
                <div>🔻 진행 일정</div>
                <div>접수 마감: ~12.31</div>
                <div>시작 일정: 11월 2-3주차 때부터 진행 예정</div>
            </div>
        ),
        userId: 1,
        user: "박민기",
        creatAt: "2023-12-10"
    },
    "Front-End": [
        {
            "partId": 37,
            "partName": "React",
            "currentApplicant": 0,
            "maxApplicant": 10
        }
    ],
    "Back-End": [
        {
            "partId": 38,
            "partName": "Spring",
            "currentApplicant": 0,
            "maxApplicant": 10
        }
    ],
}
// 새 창의 크기를 화면 크기의 50%로 설정하고, 가운데 정렬
const screenWidth = window.screen.width;
const screenHeight = window.screen.height;
const newWindowWidth = screenWidth * 0.5;
const newWindowHeight = screenHeight * 0.5;
const leftPos = (screenWidth - newWindowWidth) / 2;
const topPos = (screenHeight - newWindowHeight) / 2;
const Team_WriteView = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const userId = localStorage.getItem('userId') === null
        ? 3:localStorage.getItem('userId');
    const [edit, setEdit] = useState(false);
    const [data, setData] = useState({
        title:"",
        writer:"",
        writerId:0,
        date:"",
        content:"",
        partList:[]
    });
    const [isAdmin, setIsAdmin] = useState(userId === data.writerId);

    //지원하기 모달 관리
    const [applyModalOpen, setApplyModalOpen] = useState(false);
    const parts = [];
    //지원 리스트 모달 관리
    const [applyListModalOpen, setApplyListModalOpen] = useState(false);
    //승인된 지원 리스트 모달 관리
    const [applyApprovedListModalOpen, setApplyApprovedListModalOpen] = useState(false);
    //신고 모달 관리
    const [isReportModalOpen, setIsReportModalOpen] = useState(false);

    //debug
    useEffect(() => {
        if(data.title !== "") {
            console.log("게시글 정보 수정됨", data);
        }
    }, [data]);
    useEffect(() => {
        console.log("어드민정보 수정됨", isAdmin);
    }, [isAdmin]);

    //사용자 권한 정보 확인
    const checkAdmin = async() => {
        if(isAdmin === true) return;
        try {
            console.log("checking Admin...");
            const response = await axios.get(`/user/${userId}`);
            const jsonData = response.data.responseDto;
            console.log(jsonData.userRole);
            if(jsonData.userRole === "ADMIN") {
                console.log("isAdmin!");
                setIsAdmin(true);
            }
        } catch(error) {
            console.log(error);
        }
    }

    useEffect(() => {
        //스크롤 처리
        window.onbeforeunload = function pushRefresh() {
            window.scrollTo(0, 0);
        };

        //어드민 확인
        checkAdmin();

        //글 정보 받아오기
        const getData = async() => {
            try {
                const response = await axios.get(`/buildingPost/${id}`);
                const jsonData = response.data.responseDto;
                const brief = jsonData.buildingPost;
                console.log(brief);
                const catList = [];
                team_CategoryKOR.map((key) => {
                    if(jsonData[key] !== undefined) {
                        catList[key] = jsonData[key];
                    }
                });
                setData({
                    title:brief.title,
                    content:brief.content,
                    writerId:brief.userId,
                    writer:brief.user,
                    createAt:brief.creatAt,
                    partList:catList,
                });
            } catch(error) {
                console.log(error);
            }
        }
        getData();

        console.log("initialAdmin:", isAdmin);
    }, []);

    //카테고리 렌더링
    const renderCategory = () => {
        return (
            <div className={styles.category}>
                <table className={styles.categoryTable}>
                    {Object.entries(data.partList).map(([part, list]) => (
                        <tr>
                            <td className={styles.categoryTitle}>
                                {part}
                            </td>
                            {list.map((info) => (
                                <td className={styles.categoryElement}>
                                    <label>{info.partName}</label>
                                    <label>({info.currentApplicant}</label>
                                    <label>/</label>
                                    <label>{info.maxApplicant})</label>
                                </td>
                            ))}
                        </tr>
                    ))}
                </table>
                <div className={styles.categoryCheckApproved}>
                    <button className="yellowButton"
                            onClick={() => setApplyApprovedListModalOpen(true)}>모집정보 확인</button>
                </div>
            </div>
        );
    };

    //파트 이름만 모은 리스트
    const gatherPartName = () => {
        const parts = [];
        Object.entries(data.partList).map(([key, list]) => {
            list.map((element) => {
                parts.push(element.partName);
            });
        });
        return parts;
    }

    //마감하기/채팅하기 전환
    const closeOrChat = () => {
        //마감하기
        if(isAdmin === true || id === data.writerId) {
            return (
                <button className={"redButton"}
                        onClick={() => applyEnd()}
                >마감하기</button>
            );
        }
        //채팅하기
        else {
            return (
                <button className={"yellowButton"}
                        onClick={() => moveToChat()
                }>채팅하기</button>
            )
        }
    }

    const moveToChat = async () => {
        try {
            const response = await axios.post("/chatroom", {
                userId:userId,
                postWriterId:data.writerId
            });
            const jsonData = response.data.responseDto;
            const roomNumber = jsonData.chatRoomId;
            const targetId = jsonData.postWriterId;
            console.log("roomNumber:", roomNumber);

            const response2 = await axios.get(`/user/${data.writerId}`);
            const jsonData2 = response2.data.responseDto;
            localStorage.setItem('EnemyImage', JSON.stringify(jsonData2.url));
            localStorage.setItem('EnemyName', JSON.stringify(jsonData2.nickName));
            window.open(`http://15.164.3.171:3000/chatRoom2/${roomNumber}`, "ChatRoom", `width=${newWindowWidth}, height=${newWindowHeight}, top=${topPos}, left=${leftPos}`);
        } catch(error) {
            console.log(error);
        }
    }

    const applyEnd = async () => {
        if(!window.confirm("마감하시겠습니까?")) return;
        let success = false;
        try {
            const response = await axios.put(`/buildingPost/end/${id}`);
            if(response.status === 200) {
                window.alert("마감되었습니다.");
                success = true;
                window.location.reload();
            }

        } catch(error) {}
        try {
            const response2 = await axios.put(`/projectPostPost/end/${id}`);
            if(response2.status === 200) {
                window.alert("마감되었습니다.");
                success = true;
                window.location.reload();
            }
        } catch(error) {}

        if(success === false) {
            window.alert("마감할 수 없습니다!");
            console.log("게시글 마감 오류 발생!");
        }
    }

    const applyOrCheck = () => {
        //지원자 확인
        if(isAdmin === true || id === data.writerId) {
            return (
                <button className={"yellowButton"}
                        onClick={() => setApplyListModalOpen(true)}
                >지원자 확인</button>
            );
        }
        //지원하기
        else {
            return (
                <button className={"yellowButton"}
                        onClick={() => setApplyModalOpen(true)}
                >지원하기</button>
            );
        }
    }

    const reportOrEdit = () => {
        const openModal = () => {
            setIsReportModalOpen(true);
        };

        const closeModal = () => {
            setIsReportModalOpen(false);
        };
        //수정/삭제
        if(isAdmin === true || id === data.writerId) {
            return (
                <div className={styles.buttonLayout}>
                    <button className={"blueButton"}
                            onClick={() => setEdit(true)}>수정</button>
                    <button className={"redButton"}
                            onClick={deletePost}>삭제</button>
                </div>
            );
        }
        //신고하기
        else {
            return (
                <div className="report">
                    <Siren width={20} height={20} /><button onClick={openModal}>신고</button>
                    <ReportModal
                        showModal={isReportModalOpen}
                        item={{
                            title:data.title,
                            userId:userId,
                            writerId:data.writerId,
                            writer:data.writer,
                            postId:id
                        }}
                        category={"빌딩"}
                        onClose={closeModal}
                    />
                </div>
            )
        }
    }
    //게시글 삭제
    const deletePost = () => {
        if (!window.confirm("게시글을 삭제하시겠습니까?")) return;
        //유효성 검사
        let noApplicant = true;
        Object.entries(data.partList).map(([key, list]) => {
            list.map((element) => {
                if(element.currentApplicant > 0) {
                    noApplicant = false;
                }
            });
        });
        if(noApplicant === false) {
            window.alert("지원이 승인된 사용자가 있어 삭제가 불가능합니다.");
            return;
        }
        const deletePost = async() => {
            try {
                const response = await axios.delete(`/buildingPost/${id}`);
                if (response.status === 200) {
                    window.alert("삭제되었습니다.");
                    navigate(-1);
                }
            } catch (error) {
                window.alert("오류 발생!");
                console.log(error);
            }
        }
        deletePost();
    }

    return (
        <div>
            {applyModalOpen === true ?
                <Write_Apply postInfo={data}
                             postId={id}
                             applyModalOpen={applyModalOpen}
                             setApplyModalOpen={setApplyModalOpen}
                             id={userId}
                /> : <></>
            }
            {applyListModalOpen === true ?
                <Write_ApplyList postId={id}
                                 parts={gatherPartName()}
                                 applyListModalOpen={applyListModalOpen}
                                 setApplyListModalOpen={setApplyListModalOpen}
                /> : <></>
            }
            {applyApprovedListModalOpen === true ?
                <Write_ApplyApprovedList postId={id}
                                         parts={gatherPartName()}
                                         applyApprovedListModalOpen={applyApprovedListModalOpen}
                                         setApplyApprovedListModalOpen={setApplyApprovedListModalOpen}
                /> : <></>
            }
            <Nav/>
            <div className={styles.page}>
                {edit === false ? (
                    //게시글 표시
                    <div>
                        <div className={styles.titleAndCategory}>
                            <div className={styles.title}>
                                <label className={styles.T}>{data.title}</label>
                                <label className={styles.W}>{data.writer}</label>
                                <label className={styles.D}>{data.createAt}</label>
                            </div>
                            {renderCategory()}
                        </div>
                        <div className={styles.body}>
                            <div className={styles.buttonLayout}>
                                {closeOrChat()}
                                {applyOrCheck()}
                            </div>
                            <div className={styles.text} dangerouslySetInnerHTML={{__html: data.content}}/>
                            <div className={styles.report}>
                                {reportOrEdit()}
                            </div>
                        </div>
                        {/* debug: 관리자/일반 전환 */}
                        <button className={"greyButton"}
                                onClick={() => {setIsAdmin(!isAdmin)
                                }}>debug:관리자/일반 전환하기</button>
                    </div>
                    ) : (
                    //게시글 수정
                    <Team_WriteEdit postId={id}
                                    setEdit={setEdit}
                                    data={data}
                    />
                    )}
            </div>
            <Footer/>
        </div>
    );
}

export default Team_WriteView;