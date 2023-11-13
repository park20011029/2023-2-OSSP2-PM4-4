//게시글 목록용 카테고리 컴포넌트
import React, { useState, useEffect } from 'react';
import "../css/Contest_ListPage.css";

const List_Category = (props) => {
    const [selectedItems, setSelectedItems] = useState(props.selected || {});
    const criteria = props.criteria || {};
    const categories = Object.keys(criteria);

    const checkBoxValue = (category, value) => (e) => {
        const updatedSelected = { ...selectedItems };

        if (e.target.checked) {
            updatedSelected[category] = updatedSelected[category] || [];
            updatedSelected[category].push(value);
        } else {
            const itemIndex = updatedSelected[category]?.indexOf(value);
            if (itemIndex !== -1) {
                updatedSelected[category].splice(itemIndex, 1);
            }
        }

        setSelectedItems({ ...updatedSelected });
    };

    const renderCheckBoxes = (category) => {
        const items = props.detailList[category] || [];

        return items.map((item, index) => {
            const isChecked = selectedItems[category]?.includes(item);
            return (
                <label key={index}>
                    <input
                        type="checkbox"
                        value={item}
                        checked={isChecked}
                        onChange={checkBoxValue(category, item)}
                    />
                    {item}
                </label>
            );
        });
    };

    const resetCheck = () => {
        setSelectedItems(props.selected || {});
    };

    const search = () => {
        //Todo : 검색함수
        console.log(props.selected);
    };

    useEffect(() => {
        resetCheck();
    }, [props.selected]);

    return (
        <div className="Category">
            <label className="title">카테고리</label>
            <div className="criteria">
                {categories.map((category) => (
                    <div key={category} className="checkbox">
                        <label>{criteria[category]}</label>
                        {renderCheckBoxes(category)}
                    </div>
                ))}
                <div className={"buttons"}>
                    <button onClick={resetCheck}>초기화</button>
                    <button onClick={search}>검색</button>
                </div>
            </div>
        </div>
    );
};

export default List_Category;
