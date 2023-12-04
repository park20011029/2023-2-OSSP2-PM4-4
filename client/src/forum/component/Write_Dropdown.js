//공모전 카테고리 드롭다운 컴포넌트
import styles from "../css/Team_Write(Post).module.css";
import React, {useEffect, useState} from "react";
import {contest_CategoryTrans, contest_getCategoryAll} from "./axios_category";

const Write_Dropdown = ({ categoryList, handleDataChange }) => {
    const [categoryData, setCategoryData] = useState(categoryList);
    //카테고리 내용(드롭다운/체크박스)
    const [detail, setDetail] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await contest_getCategoryAll(Object.keys(contest_CategoryTrans));
                setDetail(result);
            } catch (error) {
                console.error("Error fetching category details:", error);
            }
        };

        fetchData();
    }, []); // Empty dependency array means this effect runs once after the initial render

    let labels = [];

    Object.entries(detail).map(([key, list]) => {
        labels.push(
            <div className={styles.detailLine} key={`detailLine-${key}`}>
                <label key={`label-${key}`}>
                    {categoryData[key]} :
                </label>
                <select key={`select-${key}`} id={key} onChange={handleDataChange}>
                    <option value="" disabled selected hidden>
                        선택하세요
                    </option>
                    {list.map((option) => (
                        <option
                            key={option[key]}
                            id={option[key]}
                            value={option.id}
                            onChange={handleDataChange}
                        >
                            {option[key]}
                        </option>
                    ))}
                </select>
            </div>
        );
    });

    return labels;
};

export default Write_Dropdown;
