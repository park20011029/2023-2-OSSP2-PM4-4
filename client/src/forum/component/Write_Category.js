//팀원모집 글쓰기 카테고리 선택 컴포넌트
//모집하려는 카테고리와 인원을 선택할 수 있게 함.

import React, { useState, useEffect } from 'react';
import styles from '../css/Team_Write(Post).module.css';
import * as ac from "../component/axios_category";

const Write_Category = ({setCategory}) => {
    const [enable, setEnable] = useState(false);
    const [element, setElement] = useState({
        techType:"", partName:"", maxApplicant:0
    })

    const handleDataChange = (key, part, e) => {
        setElement((prevElement) => {
            const updatedElement = { ...prevElement, techType: key };
            if (part === "partName") {
                setEnable(true);
                updatedElement[part] = e.target.value;
            }
            if (part === "maxApplicant") {
                updatedElement[part] = e.target.value;
                setCategory(updatedElement);
            }
            if (updatedElement.partName && updatedElement.maxApplicant) {
                setCategory(updatedElement);
            }
            return updatedElement;
        });
    };


    return (
        <div>
            <div className={styles.title}>모집 카테고리 선택</div>
            <div className={styles.criteria}>
                {Object.entries(ac.team_CategoryTrans).map(([key, value]) => (
                    <div className={styles.row}>
                        <div className={styles.categoryTitle}>
                            {/* 카테고리 제목 */}{value}
                        </div>
                        {/* 각 카테고리별 드롭다운 */}
                        <select onChange={(e) => handleDataChange(key, "partName", e)}>
                            <option value="" disabled selected hidden>항목 선택</option>
                            {ac.team_CategoryDetail[key].map((part) => (
                                <option key={part} id={part} value={part}>
                                {part}</option>
                            ))}
                        </select>
                        {/* 인원 선택 */}
                        <select disabled={!enable}
                            onChange={(e) => handleDataChange(key, "maxApplicant", e)}>
                            <option value="" disabled selected hidden>인원 선택</option>
                            {Array.from({ length: 9 }, (_, index) => (
                                <option key={index + 1} value={index + 1}>
                                    {index + 1}</option>
                            ))}
                        </select>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Write_Category;

/*
                   <div className={styles.element}>
                                    <label className={`${styles.check} ${
                                        selected[category][part] >= 1 ? styles.checked : styles.unchecked}`}
                                        onClick={() => handlePartClick(category, part)}>
                                        {part}</label>

                                </div>
 */