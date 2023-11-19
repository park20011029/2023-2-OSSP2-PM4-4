import React, { useState } from "react";
import { renderToString } from "react-dom/server"; // Import renderToString
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Contest_Team_ListTab from "./Contest_Team_ListTab";
import styles from "../css/Contest_Info_Write.module.css";


// DUMMY DATA
const write = {
    image: 'forum_test.png',
    title: '엘리스 코드 챌린지',
    area: 'IT',
    host: '일반기업',
    startDate: '2023년 10월 26일',
    endDate: '2023년 11월 24일',
    scale: '1천 ~ 3천',
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
            <p>
                <strong>해택 내역</strong><br/>
                <strong>• </strong>
                총 상금 2천만원의 혜택<br/>
                1.<strong> </strong>대상(1등) : 상금 300만원<br/>
                2. 최우수상(2-3등) : Macbook Pro 13형(M2칩)<br/>
                3. 우수상(4-8등) : iPad Air 256GB<br/>
                4. 장려상(9-20등) : AirPods Max<br/>
                <strong>• </strong>
                유저 친화적이고 확정성 있는 API 개발 경험<br/>
                <strong>•&nbsp;</strong>
                온라인 라이브 코딩 해설 세션을 통한 알고리즘 역량 UP!<br/>
                <strong>• </strong>
                현직 백엔드 엔지니어와의 네트워킹 경험<br/><br/>
                <strong>기간 및 일정</strong><br/>
                <strong>• </strong>
                참가 접수 : 10월 26일(목) 18:00 - 11월 24일(금) 18:00<br/>
                <strong>• </strong>
                온라인 예선 : 11월 25일(토) 00:00 - 12월 4일(월) 00:00<br/>
                <strong>• </strong>
                본선 진출자 발표 : 12월 4일(토) 18:00<br/>
                <strong>• </strong>
                온라인 라이브 코딩 해설 세션 : 12월 11일(월) 19:00<br/>
                <strong>• </strong>
                오프라인 본선&nbsp;&nbsp;•&nbsp;시상식 및 네트워킹 세션 :&nbsp;12월 16일(토) 13:00
            </p>
            <p>
                <br/><strong>지원 자격</strong><br/>
                <strong>• </strong>
                신입, 경력과 무관하게 백엔드/풀스택 개발자라면 누구나 도전 할 수 있습니다.<br/>
                <strong>•&nbsp;</strong>
                석/박사 소속 재학/휴학생(박사 전문연구요원 포함)도 참가 가능합니다.<br/>
                * 단, 사용 가능한 언어 요건(Python)을 반드시 확인하고 지원하세요.<br/><br/>
                <strong>접수 방법</strong><br/>
                <strong>• </strong>엘리스 코드 챌린지 홈페이지 접수
                [<a target="_blank" rel="noopener noreferrer nofollow" className="text-stone-400 underline underline-offset-[3px] hover:text-stone-600 transition-colors cursor-pointer" href="https://code-challenge.elice.io/tracks/4152/promotion?utm_source=community&amp;utm_medium=poster&amp;utm_campaign=cc&amp;utm_term=%ec%9a%94%ec%a6%98%ea%b2%83%eb%93%a4">링크</a>]<br/>
                <strong>• </strong>
                접수 방법은 아래와 같습니다. 자세한<strong>&nbsp;</strong>사항은 홈페이지의 안내를 따라주세요.<br/>
                1) 홈페이지 하단 '수강신청' 클릭<br/>2) 회원 가입 후 로그인 (회원가입시 필요한 성명, 연락처, 이메일주소는 정확하게 기재바랍니다)<br/>3) '시즌 1. 백엔드 챌린지' 클릭<br/>4) '엘리스 코드 챌린지 지원하기'의 '첫 학습 시작하기' 클릭<br/>5) 마케팅 활용 동의서 및 신청 작성 후 제출<br/>
            </p>
            <p>
                <strong>문의 사항</strong><br/>
                <strong>• </strong>엘리스 코드 챌린지 홈페이지 채널톡을 통해 문의 바랍니다.&nbsp;
                [<a target="_blank" rel="noopener noreferrer nofollow" className="text-stone-400 underline underline-offset-[3px] hover:text-stone-600 transition-colors cursor-pointer" href="https://code-challenge.elice.io/tracks/4152/promotion?utm_source=community&amp;utm_medium=poster&amp;utm_campaign=cc&amp;utm_term=%ec%9a%94%ec%a6%98%ea%b2%83%eb%93%a4">링크</a>]
            </p>
        </div>
    ),
};


//요약 내용 렌더링
const renderBrief = () => {
    const categories = [
        { key: '제목', value: write.title },
        { key: '분야', value: write.area },
        { key: '주최기관', value: write.host },
        { key: '접수기간', value: `${write.startDate} ~ ${write.endDate}` },
        { key: '시상규모', value: write.scale },
    ];

    return (
        <div className={styles.brief_labels}>
            <div className={styles.category}>
                {categories.map((item, index) => (
                    <label key={index}>
                        {item.key} :
                    </label>
                ))}
            </div>
            <div className={styles.label}>
                {categories.map((item, index) => (
                    <label className="variable" key={index}>
                        {item.value}
                    </label>
                ))}
            </div>
        </div>
    );
};

const Contest_Info_Post = () => {
    const [activeTab, setActiveTab] = useState('Tab1');

    const changeTab = (tab) => {
        setActiveTab(tab);
    };

    return (
        <div>
            <Nav />
            <div className={styles.page}>
                <div className={styles.brief}>
                    <div className={styles.small_image}>
                        <img src={write.image} alt={"작은 포스터"} />
                    </div>
                    {renderBrief()}
                </div>

                <div className={styles.twoTabs}>
                    <div className={styles.tabButtons}>
                        <button onClick={() => changeTab('Tab1')}>소개</button>
                        <button onClick={() => changeTab('Tab2')}>팀원 모집</button>
                    </div>
                    <div className={styles.tab}>
                        {activeTab === 'Tab1' && (
                            <div className={styles.tab1}>
                                <img className={styles.big_image} src={write.image} alt={"큰 포스터"} />
                                <div dangerouslySetInnerHTML={{__html: renderToString(write.detail)}}/>
                            </div>
                        )}
                        {activeTab === 'Tab2' && (
                            <div className={styles.tab2}>
                                <Contest_Team_ListTab />
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Contest_Info_Post;
