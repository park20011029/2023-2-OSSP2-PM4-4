//공모전 정보 게시글 쓰기 페이지
import React, {useState} from "react";
import styles from "../css/Contest_Team_Write(Post).module.css";
import Write_Title from "../component/Write_Title";
import ReactQuill from "react-quill";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";

const categories = ["제목", "분야", "주최기관", "시작일", "종료일", "시상규모"];

//입력 정보
const brief = ['','','','','',''];
const detail = '';
const img = '';

const Contest_Info_Write = () => {
    const [brief, setBrief] = useState(['','','','','','']);
    const [detail, setDetail] = useState('');
    const [img, setImg] = useState('');

    //입력값을 처리하는 함수
    const handleChange = (index) => (e) => {
        const newValue = e.target.value;
        setBrief((prevBrief) => {
            const newBrief = [...prevBrief];
            newBrief[index] = newValue;
            return newBrief;
        });
    };

    return(
        <div>
            <Nav />
                <div className={styles.page}>
                    <div className={styles.bigTitle}>게시글 작성</div>
                    <div className={styles.brief}>
                        <div className={styles.image}>
                            <button>파일 업로드</button>
                            {/*Todo: 이미지 업로드 구현*/}
                        </div>
                        <div className={styles.info}>
                            {Write_Title()}
                            <div className={styles.inputMust}>
                                {categories.map((item, index) => (
                                    <div className={styles.briefLabels}>
                                        <label>{item}:</label>
                                        <input type={"text"}
                                               value={brief[index]}
                                               placeholder={"입력"}
                                                onChange={handleChange(index)}
                                        />
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                    <div className={styles.body}>
                        <div className={styles.bigTitle}>본문</div>
                        <ReactQuill
                            className={styles.input}
                            value={detail}
                        />
                    </div>
                    <div className={styles.submitCancel}>
                        <button className={styles.submit}>신청</button>
                        <button className={styles.cancel}>취소</button>
                    </div>
                </div>
            <Footer />
        </div>
    );
}

export default Contest_Info_Write;
