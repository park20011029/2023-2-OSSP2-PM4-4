//공모전 팀원 모집글(보기)
import React from 'react';
import {useNavigate} from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import {renderToString} from "react-dom/server";
import styles from "../css/Team_Write(View).module.css";

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
        </div>)
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




const Team_WriteView = () => {
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

export default Team_WriteView;