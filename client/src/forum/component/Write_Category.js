//팀원모집 글쓰기 카테고리 선택 컴포넌트
//모집하려는 카테고리와 인원을 선택할 수 있게 함.

import React, {useState, useEffect } from 'react';
import styles from '../css/Contest_Team_Write(Post).module.css';

const Write_Category = (props) => {
    const {categoryList, categoryDetail, selected, setSelected} = props;

    const handlePartClick = (category, part) => {
        const newValue = selected[category][part] === 0 ? 1 : 0;
        setSelected((prevSelected) => ({
            ...prevSelected,
            [category]: {
                ...(prevSelected[category] || {}),
                [part]: newValue,
            },
        }));
    };


    const handleNumberChange = (category, part, value) => {
        let parsedValue = parseInt(value, 10) || 0;
        if(parsedValue < 0) {
            parsedValue = 0;
        }

        setSelected((prevSelected) => ({
            ...prevSelected,
            [category]: {
                ...(prevSelected[category] || {}),
                [part]: parsedValue,
            },
        }));
    };


    return (
        <div>
            <div className={styles.title}>모집 카테고리 선택</div>
            <div className={styles.criteria}>
                {Object.entries(categoryDetail).map(([category, list]) => (
                    <div className={styles.row}>
                        <div className={styles.categoryTitle}>
                            {categoryList[category]}
                        </div>
                        {list.map((part) => (
                            <div className={styles.element}>
                                <label className={`${styles.check} ${
                                    selected[category][part] >= 1 ? styles.checked : styles.unchecked}`}
                                    onClick={() => handlePartClick(category, part)}>
                                    {part}</label>
                                <input
                                    type="number"
                                    value={selected[category][part]}
                                    onChange={(e) => handleNumberChange(category, part, e.target.value)}
                                    disabled={selected[category][part] === 0}
                                />
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Write_Category;