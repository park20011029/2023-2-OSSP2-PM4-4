//공모전 정보 게시글 상세
import React, {useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Contest_Post_TeamTab from "./Contest_Post_TeamTab";
import styles from "../css/Contest_Post_InfoTab.module.css";
import axios from "axios";
import {contest_CategoryTrans} from "../component/axios_category";
import defaultImage from "../assets/default.png";
import {Siren} from "../../Siren";
import ReportModal from "../../ReportModal";

// DUMMY DATA
const write = {
    image: 'forum_test.png',
    title: '엘리스 코드 챌린지',
    category: 'IT',
    target:"타겟",
    organization:"일반기업",
    startAt: '2023년 10월 26일',
    endAt: '2023년 11월 24일',
    scale: '1천 ~ 3천',
    benefit:'돈',
    detail: (
        <div className="ProseMirror prose-headings:font-display prose-base md:prose-lg break-keep break-words tracking-[-0.004em] py-10 focus:outline-none">
            <h2><strong><span style={{ color: '#9333EA' }}>/*elice*/<br/>Code Challenge</span></strong></h2>
            <p>
                <strong>개요</strong><br/>
                <strong> • </strong>
                기술의 경계를 넓히고 싶은 백엔드 개발자를 위한 Real Challenge!
            </p>
            <p>
                <strong>미션</strong><br/>
                <strong>• </strong>
                AI 플랫폼을 구성하는 유저 친화적이고 확정성 있는 API 개발
            </p>
        </div>
    ),
};

const Contest_Post_InfoTab = () => {
    const { id } = useParams();
    const [activeTab, setActiveTab] = useState('Tab1');
    const [post, setPost] = useState({}); //글 정보
    const [isModalOpen, setIsModalOpen] = useState(false);
    const userId = 1; //Todo: userId

    //탭 변경
    const changeTab = (tab) => { setActiveTab(tab); };

    useEffect(() => {
        //스크롤 처리
        window.onbeforeunload = function pushRefresh() {
            window.scrollTo(0, 0);
        };
        //글 정보 Get
        const getPost = async() => {
            try {
                const response = await axios.get(`/contestPost/${id}`);
                const jsonData = response.data.responseDto;
                const post = {};
                post.image = jsonData.imageUrl  || defaultImage;
                post.title = jsonData.title;
                Object.keys(contest_CategoryTrans).forEach((key) => {
                    post[key] = jsonData[key];
                });
                post.content = jsonData.content;
                setPost(post);
            } catch(error) {
                //더미데이터
                setPost(write);
                console.error(error);
            }
        };
        getPost();
    }, [id]);

    useEffect(() => {
        console.log("post:", post);
    }, [post]);
    //요약 내용 렌더링
    const renderBrief = () => {
        console.log("post:",post);
        if (Object.keys(post).length === 0) return null;

        return (
            <div className={styles.brief_labels}>
                <label className={styles.brief_title}>{post.title}</label>
                <table className={styles.briefTable}>
                    {Object.entries(contest_CategoryTrans).map(([key, value]) => (
                        <tr>
                            <td className={styles.rowTitle}>{value} : </td>
                            <td>{post[key][key] || post[key]}</td>
                        </tr>
                    ))}
                </table>

            </div>
        );

    };

    //본문내용 렌더링
    const renderContent = () => {
        return <div className={styles.content} dangerouslySetInnerHTML={{ __html: post.content }} />;
    }

    //신고 모달
    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <div>
            <Nav />
            <div className={styles.page}>
                <div className={styles.brief}>
                    <div className={styles.small_image}>
                        <img src={post.image} alt={"공모전 포스터(소)"} />
                    </div>
                    {renderBrief(post)}
                </div>

                <div className={styles.twoTabs}>
                    <div className={styles.tabButtons}>
                        <button className={(activeTab==='Tab1' ? styles.Pressed : styles.notPressed)}
                                onClick={() => changeTab('Tab1')}>소개</button>
                        <button className={(activeTab==='Tab2' ? styles.Pressed : styles.notPressed)}
                                onClick={() => changeTab('Tab2')}>팀원 모집</button>
                    </div>
                    <div>
                        {activeTab === 'Tab1' && (
                            <div>
                                <img className={styles.big_image} src={post.image} alt={"공모전 포스터(대)"} />
                                <div className={styles.body}>
                                    {renderContent()}
                                    <div className="report">
                                        <Siren width={20} height={20} /><button onClick={openModal}>신고</button>
                                        <ReportModal
                                            showModal={isModalOpen}
                                            item={{
                                                title:post.title,
                                                userId:userId,
                                                postId:id,
                                                writerId:post.userId
                                            }}
                                            category={"공모전"}
                                            onClose={closeModal}
                                        />
                                    </div>
                                </div>
                            </div>
                        )}
                        {activeTab === 'Tab2' && (
                            <div>
                                <Contest_Post_TeamTab contestPostId={id}/>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Contest_Post_InfoTab;
