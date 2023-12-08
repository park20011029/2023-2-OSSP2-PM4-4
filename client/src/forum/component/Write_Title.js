//제목 입력 컴포넌트
import React, { useState } from 'react';
import styles from "../css/Team_Write(Post).module.css";

const Write_Title = ({setTitle}) => {
    const [data, setData] = useState('');

    return(
        <input className={styles.titleInput}
               placeholder={"제목을 입력하세요"}
               value={data}
               onChange={(e) => {
                   const filteredValue = e.target.value;
                   //console.log("filteredValue:",filteredValue);
                   setData(filteredValue);
                   setTitle(filteredValue);
               }}
        />
    );
}

export default Write_Title;
