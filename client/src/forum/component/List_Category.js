//게시글 목록용 카테고리 컴포넌트
import React, { useState, useEffect } from 'react';
import styles from "../css/ListPage.module.css";

const List_Category = ({criteria, detailList, selected, updateSelected}) => {
    //Todo: 초기화시 부모 컴포넌트에 값이 반영되지 않음
    [selected, updateSelected] = useState(selected);
    const categories = Object.keys(criteria);

    const checkBoxValue = (category, value) => (e) => {
        const updated = { ...selected };

        if (e.target.checked) { //체크된 경우
            updated[category] = updated[category] || [];
            updated[category].push(value);
        } else { //체크 해제된 경우 해당 요소를 제거
            const itemIndex = updated[category]?.indexOf(value);
            if (itemIndex !== -1) {
                updated[category].splice(itemIndex, 1);
            }
        }
        //업데이트
        updateSelected({ ...updated });
    };

    //체크박스 컴포넌트 렌더링
    const renderCheckBoxes = (category) => {
        const items = detailList[category] || [];

        return items.map((item, index) => {
            const isChecked = selected[category]?.includes(item);
            return (
                <div className={styles.checkList} key={index}>
                    <input type="checkbox"
                        value={item}
                        checked={isChecked}
                        onChange={checkBoxValue(category, item)}
                    />
                    {item}
                </div>
            );
        });
    };

    const resetCheck = () => {
        const empty = {};
        categories.forEach(category => {
            empty[category] = [];
        });
        updateSelected(empty);
    };
    useEffect(() => {
        updateSelected(selected || {});
    }, [selected]);

    const search = () => {
        //Todo : 검색함수
        console.log(selected);
    };

    return (
        <div className={styles.smallPage}>
            <label className={styles.smallTitle}>카테고리</label>
            <div className={styles.criteria}>
                {categories.map((category) => (
                    <div key={category} className={styles.checkbox}>
                        <label className={styles.checkListTitle}>{criteria[category]}</label>
                        {renderCheckBoxes(category)}
                    </div>
                ))}
                <div className={styles.buttons}>
                    <button onClick={resetCheck}>초기화</button>
                    <button onClick={search}>검색</button>
                </div>
            </div>
        </div>
    );
};

export default List_Category;
