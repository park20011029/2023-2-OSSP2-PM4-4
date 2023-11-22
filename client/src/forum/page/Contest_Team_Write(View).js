//공모전 팀원 모집글(보기)
import React from 'react';
import {useNavigate} from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import {renderToString} from "react-dom/server";
import styles from "../css/Contest_Team_Write(View).module.css";

//DUMMY DATA
const categoryList = {
    FrontEnd: "Front-end",
    BackEnd: "Back-end",
    Extra: "기타"
};
const write = {
    title: "팀원 구합니다@@@@@@@@@@",
    writer: "김코딩",
    date: "2023.11.10",
    category: {
        FrontEnd: [["React", 1, 3]],
        BackEnd: [["Spring",2,2], ["Django",0,2]],
        Extra: [["디자인",0,1], ["기획",0,1]]
    },
    body: (
        <div className="post-detail">
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
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <div>🔻 기존 팀원 소개</div>
        <div>&nbsp;</div>
        <div>둘 다 작년 파크랜드 공모전 참여 경험이 있으며,</div>
        <div>이번에는 수상을 목표로 다시 도전하려고 합니다!</div>
        <div>&nbsp;</div>
        <div>1. 주요 역할 - 로직</div>
        <div>✔️ 경영 + 패디 복전</div>
        <div>✔️ 패션 관련 대외활동 경험 다수</div>
        <div>✔️ 창업 대회 참여 및 친환경 패션 펀딩 성공 이력O</div>
        <div>✔️ 광고 공모전 및 프로젝트 다수 참여, 수상 2회</div>
        <div>&nbsp;</div>
        <div>2. 주요 역할 - 아이디어</div>
        <div>✔️ 경영학과</div>
        <div>✔️ 광고 공모전 및 프로젝트 다수 참여, 수상 2회</div>
        <div>&gt; DCA 파이널리스트</div>
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <div>🔻 총 모집 팀원 2명</div>
        <div>&nbsp;</div>
        <div>1. 메인 디자인 가능</div>
        <div>2. 로직 + 서브 디자인</div>
        <div>&nbsp;</div>
        <div>✔️ PPT 기획서 디자인 및 제작 능력</div>
        <div>✔️ 일러스트나 포토샵 툴 활용 가능</div>
        <div>✔️ 패션 제품이나 사업의 시각화를 잘 해주실 수 있는 분</div>
        <div>✔️ 패션 디자인 전공자 (필수X)</div>
        <div>&nbsp;</div>
        <div>✔️ 광고 공모전이나 사업 제안 공모전 경험 O</div>
        <div>✔️ 아이디어나 비즈니스 제안에 자신있으신 분</div>
        <div>&nbsp;</div>
        <div>✔️ 시간과 열정을 많이 쏟을 수 있으신 분</div>
        <div>✔️ 서울 대면 회의가 가능하신 분</div>
        <div>&nbsp;</div>
        <div>** 이에 조금이나마 적합하시다면 하단의 오픈채팅 링크로 양식에 맞춰 연락 부탁드립니다😊</div>
        <div>&nbsp;</div>
        <div>전공/거주지/본인 이력(포폴)/자신있는 역량/회의 가능 주기</div>
        <div>&nbsp;</div>
        <div>✨ 수상을 목표로 열심히 달려봐요!!✨</div>
        <div>&nbsp;</div>
        <div>파크랜드 공모전 팀원 모집</div>
        <div>https://open.kakao.com/o/s7ehzyQf</div>
        <p>&nbsp;</p></div>)
}

//카테고리 렌더링
const renderCategory = () => {
    return (
        <div className={styles.category}>
            {Object.entries(write.category).map(([category, list]) => (
                <div className={styles.row}>
                    <div className={styles.categoryTitle}>
                        {categoryList[category]}
                    </div>
                    {list.map((element, index) => (
                        <label key={index}>
                            {`${element[0]} (${element[1]} / ${element[2]})`}
                        </label>
                    ))}
                </div>
            ))}
        </div>
    );
};




const Contest_Team_WriteView = () => {
    let currentUrl = window.location.href;
    let writeNo = new URLSearchParams(currentUrl.split('?')[1]);
    console.log(writeNo);
    //Todo : urlparam을 이용하여 글 정보 받아오기
    return(
        <div>
            <Nav />
          <div className={styles.page}>
              <div className={styles.titleAndCategory}>
                  <div className={styles.title}>
                      <label className={styles.T}>{write.title}</label>
                      <label className={styles.W}>{write.writer}</label>
                      <label className={styles.D}>{write.date}</label>
                  </div>
                  {renderCategory()}
              </div>
              <div className={styles.body}>
                  <div className={styles.appointment}>
                      <button>채팅하기</button>
                      <button>지원하기</button>
                  </div>
                  <div className={styles.text}>
                      <div dangerouslySetInnerHTML={{__html: renderToString(write.body)}}/>
                  </div>
                  <div className={styles.report}>
                      <button>신고하기</button>
                  </div>
              </div>
          </div>
            <Footer />
        </div>
    );
}

export default Contest_Team_WriteView;