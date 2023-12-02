import AddButton from "../AddButton";
import DeleteButton from "../DeleteButton";
import './SkillStackInput.css';
import axios from "axios";
function SkillStackInput({skills, techType, tech, techDescription, setSkills, setTechType, setTech, setTechDescription}) {
    const handleAddSkill = async() => {
        if(localStorage.getItem('userId')){
            if (techType && tech && techDescription) {
                const newSkill = [...skills, {techType: techType, tech: tech, description: techDescription},];
                setSkills(newSkill);
                const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
                const resumeId = response.data.responseDto.resumeId;
                const addition = {techType: techType, tech: tech, description: techDescription};
                setTechType('');
                setTech('');
                setTechDescription('');
                if(response.status === 200){
                    const response = await axios.post(`/resume/techStack/${resumeId}`,addition);
                }
            }
        }
        else{
            if (techType && tech && techDescription) {
                const newSkill = [...skills, {techType: techType, tech: tech, description: techDescription},];
                setSkills(newSkill);
                setTechType('');
                setTech('');
                setTechDescription('');
            }
        }
    }
    const handleRemoveSkill = async(index) => {
        if(localStorage.getItem('userId')){
            const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
            const resumeId = response.data.responseDto.resumeId;
            const subtraction = skills[index];
            const updatedSkills = [...skills];
            updatedSkills.splice(index, 1);
            setSkills(updatedSkills);
            if(response.status === 200){
                const response = await axios.get(`/resume/${resumeId}`);
                if(response.status === 200){
                    response.data.responseDto.techStacks.forEach((each)=>{
                        if(each.tech === subtraction.tech && each.description === subtraction.description){
                            const response = axios.delete(`/resume/techStack/${each.id}`);
                        }
                    })
                }
            }
        }
        else{
            const updatedSkills = [...skills];
            updatedSkills.splice(index, 1);
            setSkills(updatedSkills);
        }

    }
    return (
        <div id="skillBox" className="grid-element">
            <h2>기술 스택</h2>

            <div className="inputWithButton">
                <select value={techType} onChange={(e)=>setTechType(e.target.value)}>
                    <option value="">선택해주세요.</option>
                    <option value="FRONT">front-end</option>
                    <option value="BACK">back-end</option>
                    <option value="AI">ai</option>
                    <option value="ETC">기타</option>
                </select>
                <input value={tech} onChange={(e)=>setTech(e.target.value)} placeholder="Tech"/>
                <input value={techDescription} onChange={(e)=>setTechDescription(e.target.value)} placeholder="Description"/>
                <button className="add-button" onClick={handleAddSkill}><AddButton size={25}/></button>
            </div>

            <div className="list">
                {skills.map((skill, index) => (
                    <div key={index} className="flex-container">
                        {index+1}{'. '}<span/>
                        {skill.techType}<span>/</span>
                        {skill.tech}<span>/</span>
                        {skill.description}
                        <button className="del-button" onClick={() => handleRemoveSkill(index)}><DeleteButton size={25}/></button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default SkillStackInput;
