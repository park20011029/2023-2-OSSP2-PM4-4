import AddButton from "../AddButton";
import "./AwardInput.css";
import DeleteButton from "../DeleteButton";
import DateInput from "../DateInput";
import React from "react";
import axios from "axios";

function AwardInput({awards, congress, awardYear, awardType, awardImage,awardImageArray, setAwards, setCongress, setAwardYear, setAwardType, setAwardImage, setAwardImageArray}) {
    const handleAwardImageUpload = (event) => {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const reader = new FileReader();
            reader.onload = (e) => {
                setAwardImage(selectedFile);
            };
            reader.readAsDataURL(selectedFile);
        }
    };
    const handleAddAward = async() => {
        if(localStorage.getItem('userId')){
            if (congress && awardYear && awardType && awardImage) {
                const newAwards = [
                    ...awards,
                    { competition: congress, awardYear: awardYear, awardType: awardType /*awardFile: awardFile*/ },
                ];
                setAwards(newAwards);
                const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
                const resumeId = response.data.responseDto.resumeId;
                if(response.status === 200){
                    const awardFormData = new FormData();
                    awardFormData.append('file', awardImage);
                    const awardInfo={
                        "userId":localStorage.getItem('userId'),
                        "competition":congress,
                        "awardType":awardType,
                        "awardYear":awardYear,
                    }
                    awardFormData.append("awardRequestDto", new Blob([JSON.stringify(awardInfo)],{type:"application/json"}));
                    const response = await axios.post(`/resume/award/${resumeId}`, awardFormData);
                    if(response.status === 200){
                        setCongress('');
                        setAwardYear('');
                        setAwardType('');
                        setAwardImage(null);
                    }
                }
            }
        }
        else{
            if (congress && awardYear && awardType && awardImage) {
                const newAwards = [
                    ...awards,
                    { competition: congress, awardYear: awardYear, awardType: awardType},
                ];
                setAwards(newAwards);
                const newArray = [...awardImageArray, {awardImage},];
                setAwardImageArray(newArray);
                console.log(awardImageArray);
                setCongress('');
                setAwardYear('');
                setAwardType('');
                setAwardImage(null);
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
                       onChange={handleAwardImageUpload}
                />

                <button className="add-button" onClick={handleAddAward}><AddButton size={25}/></button>
            </div>
            <div className="list">
                {awards.map((awardData, index) => (
                    <div key={index} className="flex-container">
                        {index+1}{'.'}<span/>
                        {awardData.competition}<span>/</span>
                        {awardData.awardYear}<span>/</span>
                        {awardData.awardType}
                        <button className="del-button" onClick={() => handleRemoveAward(index)}><DeleteButton size={25}/></button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default AwardInput;
