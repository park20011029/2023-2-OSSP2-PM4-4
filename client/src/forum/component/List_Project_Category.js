//프로젝트 게시글 목록용 카테고리 컴포넌트
import {team_CategoryList, team_CategoryTrans, team_CategoryDetail} from "./axios_category";
import {useEffect, useState} from "react";

const List_Project_Category = ({setSelected}) => {
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
            <div onClick={() => updateCategory(key, value)}>
                <input type="checkbox" value={value}
                       checked={selected_c[key].includes(value)}
                />
                <label>{value}</label>
            </div>
        ));
    }
    return (
        <div>
            {team_CategoryList.map((key) => (
            <>
                <label>{team_CategoryTrans[key]}</label>
                {renderList(key)}
            </>
            ))}
        </div>
    )
}

export default List_Project_Category;