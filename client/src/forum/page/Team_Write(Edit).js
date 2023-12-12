//프로젝트 게시글 수정
import React, {useEffect, useState} from "react";
import axios from "axios";
import styles from "../css/Team_Write(Post).module.css";
import ReactQuill from "react-quill";
import {team_CategoryBackTrans, team_CategoryKOR, team_CategoryTrans} from "../component/axios_category";
import Write_Category_Dropdown from "../component/Write_Category_Dropdown";

const Team_WriteEdit = ({postId, setEdit, data}) => {
    const [newData, setNewData] = useState({...data});

    //데이터 처리
    const handleData = (key, data) => {
        setNewData((prevData) => ({
            ...prevData,
            [key]: data
        }))
    }
    //데이터를 전송 데이터로 변경
    const modifyData = () => {
        //기존/신규 데이터 partList ID를 리스트화
        const extractIDList = (object) => {
            let result = [];
            team_CategoryKOR.map((key) => {
                object[key].map((element) => {
                    if(element.partId !== undefined)
                        result.push(element.partId);
                });
            });
            return result;
        }
        const orig_IDList = extractIDList(data.partList);
        const new_IDList = extractIDList(newData.partList);

        //제거된 데이터
        const removed_List = orig_IDList.filter(id => !new_IDList.includes(id));

        //변경 및 추가된 데이터 처리
        let modified_List = [];
        let added_List = [];
        Object.entries(newData.partList).map(([key, list]) => {
            list.map((element) => {
                const innerKey = team_CategoryBackTrans[key];
                //추가
                if(element.partId === undefined) {
                    added_List.push({
                        techType:innerKey,
                        partName:element.partName,
                        maxApplicant:element.maxApplicant
                    });
                }
                //변경
                else {
                    const origList = data.partList[key];
                    const origElementIndex = origList.findIndex(
                        (item) => item.partName === element.partName
                    );
                    const origElement = origList[origElementIndex];
                    console.log("origElement:", origElement);
                    if (origElement.maxApplicant !== element.maxApplicant) {
                        console.log("modified!", element.partId);
                        modified_List.push(element);
                    }
                }

            });
        });

        return {
            title: newData.title,
            content: newData.content,
            removed: removed_List,
            modified: modified_List,
            added: added_List
        };
    }

    //debug - 데이터 변경 시
    useEffect(() => {
        console.log("기존데이터", data);
        console.log("수정페이지", newData);
    }, [newData]);

    const submit = async () => {
        if(!window.confirm("수정하시겠습니까?")) return;

        //적합성 확인
        if(newData.title === "") {
            window.alert("제목을 입력해주세요.");
            return;
        }
        if(newData.partList.length === 0) {
            window.alert("카테고리는 최소 1개 이상 등록해야 합니다.");
            return;
        }
        if(newData.content === "") {
            window.alert("분문을 입력해주세요.");
            return;
        }

        try {
            const transferData = modifyData();
            console.log(transferData);
            let response;
            if(postId !== '0') {
                response = await axios.put(`/buildingPost/${postId}`, transferData);
            }
            else if(postId === '0') {
                response = await axios.put(`/projectPostPost/${postId}`, transferData);
            }
            if(response.status === 200) {
                //성공
                window.alert("완료되었습니다.");
                setEdit(false);
            }
        } catch(error) {
            window.alert("오류 발생!", error);
            console.log(newData);
            console.error(error);
        }
    }

    const updateData = (key, list) => {
        setNewData((prevData) => ({
            ...prevData,
            partList: {
                ...prevData.partList,
                [key]: list,
            },
        }));
    }
    //카테고리 - 목록에서 추가
    const addCategory = (key, part, applicant) => {
        const innerKey = team_CategoryTrans[key];
        const selectedIndex = newData.partList[innerKey].findIndex(
            (item) => item.partName === part
        );
        if(selectedIndex !== -1) {
            window.alert("해당 카테고리는 등록된 정보가 있습니다!");
            return;
        }
        let originalList = newData.partList[innerKey] || [];
        originalList.push({
            partName:part,
            maxApplicant:applicant,
            currentApplicant:0
        });
        updateData(innerKey, originalList);
    }
    //카테고리 - 목록에서 제거
    const removeCategory = (key, element) => {
        if(element.currentApplicant > 0) {
            if(!window.confirm(
                "지원이 완료된 사용자가 존재합니다.\n" +
                "임의로 삭제하실 경우, 신고대상이 될 수 있습니다.\n" +
                "삭제하시겠습니까?"
            )) return;
        }
        const updatedPartList = newData.partList[key].filter(
            (item) =>
                !(item.partName === element.partName && item.techType === element.techType)
        );
        updateData(key, updatedPartList);
    };
    //카테고리 - 최대인원 수정
    const handleMaxChange = (key, element) => (e) => {
        const newMax = e.target.value;
        if (newMax < element.currentApplicant) {
            window.alert("최대 인원은 모집된 인원보다 작을 수 없습니다!");
            return;
        }

        const selectedIndex = newData.partList[key].findIndex(
            (item) =>
                item.partName === element.partName && item.techType === element.techType
        );

        if (selectedIndex !== -1) {
            const updatedPartList = [...newData.partList[key]];
            const updatedElement = {...element, maxApplicant: newMax};
            updatedPartList[selectedIndex] = updatedElement;
            updateData(key, updatedPartList);
        }
    }

    return(
        <div>
            {/*제목 입력*/}
            <div className={styles.title}>
                {/*게시글 작성*/}
                <label>게시글 수정</label>
                <input className={styles.titleInput}
                       placeholder={"제목을 입력하세요"}
                       value={newData.title}
                       onChange={(e) => {
                           const filteredValue = e.target.value;
                           handleData("title", filteredValue);
                       }}
                />
            </div>
            {/*모집 카테고리*/}
            <div className={styles.categoryTitle}>
                <label>카테고리 수정</label>
            </div>
            <div className={styles.criteria}>
                <Write_Category_Dropdown addCategory={addCategory}/>
                <table>
                    <thead>
                        <tr>
                        <th>정보1</th>
                        <th>정보2</th>
                        <th>모집된 인원</th>
                        <th>최대 모집 인원</th>
                        <th> </th>
                        </tr>
                    </thead>
                    <tbody>
                        {Object.entries(newData.partList).map(([key, list]) => {
                            return ( list.map((element) => (
                                <tr>
                                <td>{key}</td>
                                <td>{element.partName}</td>
                                <td>{element.currentApplicant}</td>
                                <td>
                                    <select onChange={handleMaxChange(key, element)} value={element.maxApplicant}>
                                        {Array.from({ length: 9 }, (_, index) => (
                                            <option key={index + 1} value={Number(index + 1)}>
                                                {index + 1}
                                            </option>
                                        ))}
                                    </select>
                                </td>
                                <td>
                                    <button className={styles.remove}
                                        onClick={() => removeCategory(key, element)}>
                                        삭제
                                    </button>
                                </td>
                                </tr>
                            )));
                        })}
                    </tbody>
                </table>
            </div>
            {/* 본문 */}
            <div className={styles.body}>
                {/* 본문 입력 창 */}
                <ReactQuill
                    className={styles.input}
                    value={newData.content}
                    onChange={(value) => {
                        handleData("content", value);
                    }}
                />
                {/* 작성,취소 버튼 */}
                <div className={styles.submitCancel}>
                    <button className={styles.submit}
                            onClick={submit}>완료</button>
                    <button onClick={() => {
                        if(window.confirm('취소하시겠습니까?')) {
                            alert("취소되었습니다.");
                            window.location.reload();
                        }
                    }}>취소</button>
                </div>
            </div>
        </div>
    )
}

export default Team_WriteEdit;