import AddButton from "../AddButton";
import "./AwardInput.css";
import DeleteButton from "../DeleteButton";
import DateInput from "../DateInput";
import React from "react";
import axios from "axios";

function AwardInput({awards, congress, awardYear, awardType, awardFile, setAwards, setCongress, setAwardYear, setAwardType, setAwardFile}) {
    const handleAddAward = async() => {
        if(localStorage.getItem('userId')){
            if (congress && awardYear && awardType && awardFile) {
                const newAwards = [
                    ...awards,
                    { competition: congress, awardYear: awardYear, awardType: awardType /*awardFile: awardFile*/ },
                ];
                setAwards(newAwards);
                const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
                const resumeId = response.data.responseDto.resumeId;
                const addition = { competition: congress, awardYear: awardYear, awardType: awardType};
                setCongress('');
                setAwardYear('');
                setAwardType('');
                setAwardFile(null);
                if(response.status === 200){
                    const response = await axios.post(`/resume/award/${resumeId}`,addition);
                }
            }
        }
        else{
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
        }
    }
    const handleRemoveAward = async(index) => {
        if(localStorage.getItem('userId')){
            const updatedAwards = [...awards];
            updatedAwards.splice(index, 1);
            setAwards(updatedAwards);
            const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
            const resumeId = response.data.responseDto.resumeId;
            const subtraction = awards[index];
            if(response.status === 200){
                const response = await axios.get(`/resume/${resumeId}`);
                if(response.status === 200){
                    response.data.responseDto.awards.forEach((each)=>{
                        if(each.id === subtraction.id){
                            const response = axios.delete(`/resume/award/${each.id}`);
                        }
                    })
                }
            }
        }
        else{
            const updatedAwards = [...awards];
            updatedAwards.splice(index, 1);
            setAwards(updatedAwards);
        }
    }
    return (
        <div id="awardBox" className="grid-element">
            <h2>수상 내역</h2>
            <div className="inputWithButton">
                <input className="awardText"
                       type="text"
                       placeholder="수상 타이틀"
                       value={congress}
                       onChange={(e) => setCongress(e.target.value)}
                />
                <DateInput date={awardYear} setDate={setAwardYear}/>
                <input className="awardText"
                       type="text"
                       placeholder="수상 내용"
                       value={awardType}
                       onChange={(e) => setAwardType(e.target.value)}
                />
                <input className="awardText"
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
