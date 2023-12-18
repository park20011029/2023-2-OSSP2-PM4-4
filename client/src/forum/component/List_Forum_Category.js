//공모전 게시글 목록용 카테고리 컴포넌트
import React, { useState, useEffect } from 'react';
import styles from "../css/List.module.css";
import "../css/buttons.css";
import {contest_CategoryKeyList, contest_CategoryTrans, contest_getCategoryAll} from "./axios_category";

const dummyCategory = {
    target:[
        {id:1,target:"a"},
        {id:2,target:"b"},
        {id:3,target:"c"}
    ],
    organization:[
        {id:4,organization:"d"},
        {id:5,organization:"e"},
        {id:6,organization:"f"}
    ],
    category:[
        {id:7,category:"g"},
        {id:8,category:"h"},
        {id:9,category:"i"}
    ],
    scale:[
        {id:10,scale:"j"},
        {id:11,scale:"k"},
        {id:12,scale:"l"}
    ],
    benefit:[
        {id:13,benefit:"1"},
        {id:14,benefit:"2"},
        {id:15,benefit:"3"}
    ],
}
const sel = {
    target:[],
    organization:[],
    category:[],
    scale:[],
    benefit:[]
}

const List_Forum_Category = ({setCategorySelected, search}) => {
    const [categoryData, setCategoryData] = useState(sel);
    const [selected, setSelected] = useState(sel);

    useEffect(() => {
        console.log("selected change", selected);
    }, [selected]);

    //초기 설정
    useEffect(() => {
        const fetchData = async () => {
            try {
                const info = await contest_getCategoryAll(Object.keys(contest_CategoryKeyList));
                setCategoryData(info);
            } catch(error) {
                console.error(error);
            }
        }
        fetchData();
    }, []);

    useEffect(() => {
        console.log("categoryData:", categoryData);
    }, [categoryData]);
    //카테고리 체크
    const isChecked = (category, value) => selected[category].includes(value);

    //카테고리 데이터 처리
    const handleChange = (key, value) => {
        let update = {...selected}
        if (update[key].includes(value)) {
            const index = update[key].indexOf(value);
            update[key].splice(index, 1);
        } else {
            update[key].push(value);
        }

        setSelected(update);
        setCategorySelected(update);
    };

    // 카테고리 리스트 초기화
    const resetCategoryList = () => {
        let temp = {...selected};

        Object.keys(temp).forEach((category) => {
            temp[category] = [];
        });

        console.log("temp:",temp);
        setSelected(temp);
        setCategorySelected(temp);
        console.log("초기화 진행완료");
    }

    /*
    //카테고리 리스트 렌더링
    const chunkArray = (array, chunkSize) => {
        const result = [];
        for (let i = 0; i < array.length; i += chunkSize) {
            result.push(array.slice(i, i + chunkSize));
        }
        return result;
    }

     */
    const renderCategory = () => {
        const chunkSize = 6;
        const allList = [];
        {Object.keys(contest_CategoryKeyList).forEach((key) => {
            const list = categoryData[key] || dummyCategory[key];
            const categoryTitle = contest_CategoryTrans[key];
            console.log(list, categoryTitle);
            //const lines = Math.ceil(list.length/chunkSize);
            //const curList = chunkArray(list, chunkSize);
            allList.push(
                <tr>
                    <td className={styles.tableTitle}>{categoryTitle}</td>
                    {list.map((value) => (
                        <td className={styles.TableElement}>
                            <label key={value.id}>
                                <input
                                    id={value.id}
                                    value={value[key]}
                                    type="checkbox"
                                    checked={isChecked(key, value.id)}
                                    onChange={() => handleChange(key, value.id)}
                                />
                                {value[key]}
                            </label>
                        </td>
                    ))}
                </tr>
            );
        })}

        return (
            <table className={styles.categoryTable}>
                <tbody>{allList}</tbody>
            </table>
        );
    }

    return (
        <div className={styles.smallPage}>
            <label className={styles.smallTitle}>카테고리</label>
            <div className={styles.criteria}>
                {renderCategory()}
                <div className={styles.buttons}>
                    <button className={"redButton"} onClick={resetCategoryList}>초기화</button>
                    <button className={"blueButton"} onClick={search}>검색</button>
                </div>
            </div>
        </div>
    );
};

export default List_Forum_Category;
