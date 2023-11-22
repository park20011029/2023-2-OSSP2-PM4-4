//제목 입력 컴포넌트
import React from 'react';
import styles from "../css/Contest_Team_Write(Post).module.css";

const Write_Title = () => {
    return(
        <input className={styles.titleInput}
               placeholder={"제목을 입력하세요"} />
    );
}

export default Write_Title;
