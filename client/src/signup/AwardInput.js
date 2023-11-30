import AddButton from "../AddButton";
import "./AwardInput.css";
import DeleteButton from "../DeleteButton";
import DateInput from "../DateInput";
import React from "react";

function AwardInput({awards, congress, awardYear, awardType, awardFile, setAwards, setCongress, setAwardYear, setAwardType, setAwardFile}) {
    const handleAddAward = () => {
        if (congress && awardYear && awardType && awardFile) {
            const newAwards = [
                ...awards,
                { competition: congress, awardYear: awardYear, awardType: awardType /*awardFile: awardFile*/ },
            ];
            setAwards(newAwards);
            setCongress('');
            setAwardYear('');
            setAwardType('');
            setAwardFile(null);
        }
        // 입력 안 된 내용 있는 경우 에러 메세지?
    }
    const handleRemoveAward = (index) => {
        const updatedAwards = [...awards];
        updatedAwards.splice(index, 1);
        setAwards(updatedAwards);
    }
    return (
        <div id="awardBox" className="grid-element">
            <h2>수상 내역</h2>
            <div className="inputWithButton">
                <input
                    type="text"
                    placeholder="수상 타이틀"
                    value={congress}
                    onChange={(e) => setCongress(e.target.value)}
                />
                <DateInput date={awardYear} setDate={setAwardYear}/>
                <input
                    type="text"
                    placeholder="수상 내용"
                    value={awardType}
                    onChange={(e) => setAwardType(e.target.value)}
                />
                <input
                    type="file"
                    accept="image/*"
                    onChange={(e) => setAwardFile(e.target.files[0])}
                />
                {/*<span role="img" aria-label="attach-file">📎</span>*/}
                <button className="add-button" onClick={handleAddAward}><AddButton size={25}/></button>
            </div>
            <div className="list">
                {awards.map((awardData, index) => (
                    <div key={index} className="flex-container">
                        {index+1}{'.'}<span/>
                        {awardData.competition}<span>/</span>
                        {awardData.awardYear}<span>/</span>
                        {awardData.awardType}<span>/</span>
                        {awardData.awardFile && (
                            <img src={URL.createObjectURL(awardData.awardFile)} alt="Award" />
                        )}
                        <button className="del-button" onClick={() => handleRemoveAward(index)}><DeleteButton size={25}/></button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default AwardInput;
