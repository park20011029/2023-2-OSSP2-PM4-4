import './ProjectRecordInput.css';
import AddButton from "../AddButton";
import DeleteButton from "../DeleteButton";
import axios from "axios";

function ProjectRecordInput({projects, projectName, description, gitAddress, setProjects, setProjectName, setDescription, setGitAddress}) {
    const handleAddProject = async() => {
        if(localStorage.getItem('userId')){
            if (projectName && description && gitAddress) {
                const newProjects = [
                    ...projects,
                    { projectName: projectName, description: description, gitUrl: gitAddress},
                ];
                setProjects(newProjects);
                const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
                const resumeId = response.data.responseDto.resumeId;
                const addition = { projectName: projectName, description: description, gitUrl: gitAddress};
                setProjectName('');
                setDescription('');
                setGitAddress('');
                if(response.status === 200){
                    const response = await axios.post(`/resume/project/${resumeId}`,addition);
                }
            }
        }
        else{
            if (projectName && description && gitAddress) {
                const newProjects = [
                    ...projects,
                    { projectName: projectName, description: description, gitUrl: gitAddress},
                ];
                setProjects(newProjects);
                setProjectName('');
                setDescription('');
                setGitAddress('');
            }
        }
    }
    const handleRemoveProject = async(index) => {
        if(localStorage.getItem('userId')){
            const updatedProjects = [...projects];
            updatedProjects.splice(index, 1);
            setProjects(updatedProjects);
            const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
            const resumeId = response.data.responseDto.resumeId;
            const subtraction = projects[index];
            if(response.status === 200){
                const response = await axios.get(`/resume/${resumeId}`);
                if(response.status === 200){
                    response.data.responseDto.projects.forEach((each)=>{
                        if(each.projectName === subtraction.projectName){
                            const response = axios.delete(`/resume/project/${each.id}`);
                        }
                    })
                }
            }
        }
        else{
            const updatedProjects = [...projects];
            updatedProjects.splice(index, 1);
            setProjects(updatedProjects);
        }
    }
    return (
        <div id="recordBox" className="grid-element">
            <h2>프로젝트 기록</h2>
            <div className="inputWithButton">
                <input
                    type="text"
                    placeholder="프로젝트 주제"
                    value={projectName}
                    onChange={(e) => setProjectName(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="세부 사항"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Github 주소"
                    value={gitAddress}
                    onChange={(e) => setGitAddress(e.target.value)}
                />
                <button className="add-button" onClick={handleAddProject}><AddButton size={25}/></button>
            </div>
            <div className="list">
                {projects.map((project, index) => (
                    <div key={index} className="flex-container">
                        {index+1}{'. '}<span/>
                        {project.projectName}<span>/</span>
                        {project.description}<span>/</span>
                        {project.gitUrl}
                        <button className="del-button" onClick={() => handleRemoveProject(index)}><DeleteButton size={25}/></button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ProjectRecordInput;
