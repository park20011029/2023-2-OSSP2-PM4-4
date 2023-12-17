import React, {useEffect, useState} from "react";
import {team_CategoryDetail, team_CategoryList, team_CategoryTrans} from "./axios_category";
import styles from "../css/Team_Write(Post).module.css";

const RenderDropdown = ({ addCategory }) => {
    const [selectedKey, setSelectedKey] = useState("");
    const [selectedList, setSelectedList] = useState([""]);
    const [selectedPart, setSelectedPart] = useState("");
    const [selectedApplicant, setSelectedApplicant] = useState("");

    //파트 변경 시 하위 카테고리 목록 변경
    useEffect(() => {
        const list = team_CategoryDetail[selectedKey];
        if(!list)
            setSelectedList([""]);
        else setSelectedList(list);
        setSelectedPart("");
        setSelectedApplicant("");
    }, [selectedKey]);

    const handleKeyChange = (e) => {
        setSelectedKey(e.target.value);

    }
    const handlePartChange = (e) => {
        setSelectedPart(e.target.value);
    };

    const handleApplicantChange = (e) => {
        setSelectedApplicant(e.target.value);
    };

    return (
        <div className={styles.categoryDropdown}>
            <select onChange={handleKeyChange} value={selectedKey}>
                <option value="" disabled selected hidden>
                    분야
                </option>
                {team_CategoryList.map((key) => (
                    <option key={key} id={key} value={key}>
                        {team_CategoryTrans[key]}
                    </option>
                ))}
            </select>
            <select onChange={handlePartChange} value={selectedPart}>
                <option value="" disabled selected hidden>
                    프레임워크
                </option>
                {selectedList.map((part) => (
                    <option key={part} id={part} value={part}>
                        {part}
                    </option>
                ))}
            </select>

            <select onChange={handleApplicantChange} value={selectedApplicant}>
                <option value="" disabled selected hidden>
                    인원
                </option>
                {Array.from({ length: 9 }, (_, index) => (
                    <option key={index + 1} value={Number(index + 1)}>
                        {index + 1}
                    </option>
                ))}
            </select>

            <button onClick={() => {
                addCategory(selectedKey, selectedPart, selectedApplicant);
                setSelectedPart("");
                setSelectedApplicant("");
                setSelectedKey("");
            }}>
                추가
            </button>
        </div>
    );
};

export default RenderDropdown;
