//프로젝트 게시글 목록용 카테고리 컴포넌트
import {team_CategoryList, team_CategoryTrans, team_CategoryDetail} from "./axios_category";
import React, {useEffect, useState} from "react";
import styles from "../css/List.module.css";

const List_Project_Category = ({setSelected, search}) => {
    //선택한 카테고리
    const [selected_c, setSelected_c] = useState(() =>{
        let result = {};
        team_CategoryList.map((key) => {
            result[key] = [];
        });
        return result;
    });

    //카테고리 반영
    const updateCategory = (category, value) => {
        const updatedSelected = { ...selected_c };
        if (updatedSelected[category].includes(value)) {
            updatedSelected[category] = updatedSelected[category].filter(
                (item) => item !== value
            );
        } else {
            updatedSelected[category] = [...updatedSelected[category], value];
        }
        setSelected_c(updatedSelected);
    };
    useEffect(() => {
        setSelected(selected_c);
    }, [selected_c]);

    const renderList = (key) => {
        return team_CategoryDetail[key].map((value) => (
            <td className={styles.TableElement} onClick={() => updateCategory(key, value)}>
                <input type="checkbox" value={value}
                       checked={selected_c[key].includes(value)}
                />
                <label>{value}</label>
            </td>
        ));
    }

    // 카테고리 리스트 초기화
    const resetCategoryList = () => {
        let temp = {...selected_c};

        Object.keys(temp).forEach((category) => {
            temp[category] = [];
        });

        console.log("temp:",temp);
        setSelected(temp);
        setSelected_c(temp);
        console.log("초기화 진행완료");
    }

    return (
        <div className={styles.smallPage}>
            <label className={styles.smallTitle}>카테고리</label>
                <div className={styles.criteria}>
                    <table className={styles.categoryTable}>
                        {team_CategoryList.map((key) => (
                        <tr>
                            <td className={styles.tableTitle}>{team_CategoryTrans[key]}</td>
                            {renderList(key)}
                        </tr>
                        ))}
                    </table>
                    <div className={styles.buttons}>
                        <button className={"redButton"} onClick={() => resetCategoryList()}>초기화</button>
                        <button className={"blueButton"} onClick={search}>검색</button>
                    </div>
                </div>
        </div>
    )
}

export default List_Project_Category;