//팀원모집 글쓰기 카테고리 선택 컴포넌트
//모집하려는 카테고리와 인원을 선택할 수 있게 함.
import React, { useState, useEffect } from 'react';
import styles from '../css/Team_Write(Post).module.css';
import {team_CategoryDetail, team_CategoryList, team_CategoryTrans} from "./axios_category";
import RenderDropdown from "./Write_Category_Dropdown";

const Write_Category = ({setCategory}) => {
    //카테고리 선택 컴포넌트 목록
    const categoryInfo = ({...team_CategoryDetail});
    //선택한 카테고리
    const [selectedCategory, setSelectedCategory] = useState([]);


    //선택한 항목을 리스트에 추가
    const addCategory = (techType, partName, maxApplicant) => {
        //console.log(techType, partName, maxApplicant); //debug
        //유효성 검사 - 선택사항
        if(techType === "" || partName === "" || maxApplicant === "") {
            window.alert("항목을 모두 선택해주세요.");
            return;
        }

        //선택한 항목이 기존에 존재하는지 검색
        let findObject = selectedCategory.find(item =>
            item.techType === techType
            && item.partName === partName
        );

        if(findObject) { //목록에 있음 - 수정
            findObject.maxApplicant = maxApplicant;
        }
        else { //목록에 없음 - 추가
            setSelectedCategory(prevSelected => [
                ...prevSelected,
                {
                    techType: techType,
                    partName: partName,
                    maxApplicant: maxApplicant
                }
            ]);
        }
    };

    //목록에서 제거
    const removeCategory = (element) => {
        const updatedCategories = selectedCategory.filter(item =>
            !(item.techType === element.techType
                && item.partName === element.partName)
        );
        setSelectedCategory(updatedCategories);
    };

    //부모 컴포넌트에 데이터 반영
    useEffect(() => {
        //console.log("카테고리 항목 갱신됨", selectedCategory);
        setCategory(selectedCategory);
    }, [selectedCategory]);

    return (
        <div>
            <div className={styles.title}>모집 카테고리 선택</div>
            {/* 각 카테고리 제목 */}
            <label>카테고리 선택</label>
            <RenderDropdown addCategory={addCategory} />
            <div className={styles.selectedBox}>
                <label>선택한 항목</label>
                {selectedCategory.map((element) => (
                    <div className={styles.selectedRow}>
                        <label>{element.techType}</label>
                        <label>{element.partName}</label>
                        <label>{element.maxApplicant}</label>
                        <button onClick={() => removeCategory(element)}>제거하기</button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Write_Category;