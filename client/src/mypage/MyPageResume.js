import React, { useEffect, useState } from "react";
import Nav from "../layout/Nav";
import UserSideBar from "./UserSideBar";
import NameInput from "../signup/NameInput";
import BirthInput from "../signup/BirthInput";
import GenderInput from "../signup/GenderInput";
import JobInput from "../signup/JobInput";
import EmailInput from "../signup/EmailInput";
import RegionInput from "../signup/RegionInput";
import EducationInput from "../signup/EducationInput";
import AwardInput from "../signup/AwardInput";
import SkillStackInput from "../signup/SkillStackInput";
import ProjectRecordInput from "../signup/ProjectRecordInput";
import Footer from "../layout/Footer";
import "./MyPageResume.css";
import axios from "axios";
function MyPageResume() {
    const [profileImage, setProfileImage] = useState(null);
    const [eduImage, setEduImage] = useState(null);
    const [eduUrl, setEduUrl] = useState(null);
    const [nickName, setNickName] = useState(null);
    const [introduction, setIntroduction] = useState(null);
    const [resumeId, setResumeId] = useState(null);
    const [name, setName] = useState(null);
    const [date, setDate] = useState(null);
    const [gender, setGender] = useState(null);
    const [job, setJob] = useState(null);
    const [city, setCity] = useState(null);
    const [district, setDistrict] = useState(null);
    const [schoolId, setSchoolId] = useState(null);
    const [school, setSchool] = useState(null);
    const [major, setMajor] = useState(null);
    const [eduState, setEduState] = useState(null);
    const [email, setEmail] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState("010-1234-5678");
    const [awards, setAwards] = useState([]);
    const [congress, setCongress] = useState(null);
    const [awardYear, setAwardYear] = useState(null);
    const [awardType, setAwardType] = useState(null);
    const [awardImage, setAwardImage] = useState(null);
    const [awardImageArray, setAwardImageArray] = useState([]);
    const [skills, setSkills] = useState([]);
    const [techType, setTechType] = useState(null);
    const [tech, setTech] = useState(null);
    const [techDescription, setTechDescription] = useState(null);
    const [projects, setProjects] = useState([]);
    const [projectName, setProjectName] = useState(null);
    const [projectDescription, setProjectDescription] = useState(null);
    const [gitAddress, setGitAddress] = useState(null);

    const getProfileData = async() => {
        try {
            const response = await axios.get(
                `/user/${localStorage.getItem("userId")}`
            );
            setResumeId(response.data.responseDto.resumeId);
            setName(response.data.responseDto.name);
            setEmail(response.data.responseDto.email);
            setNickName(response.data.responseDto.nickName);
            setIntroduction(response.data.responseDto.introduction);
            setPhoneNumber("010-1234-5678");
        } catch (error) {
            console.log("Error fetching profile data: ", error);
        }
    }

    const getResumeData = async() => {
        try {
            const url = `/resume/${resumeId}`;
            const resumeResponse = await axios.get(url);
            console.log(resumeResponse.data.responseDto);
            setDate(resumeResponse.data.responseDto.resume.birth);
            if (resumeResponse.data.responseDto.resume.gender === true) {
                setGender(true);
            } else if (resumeResponse.data.responseDto.resume.gender === false) {
                setGender(false);
            } else {
                setGender("");
            }
            setJob(resumeResponse.data.responseDto.resume.job);
            setCity(resumeResponse.data.responseDto.resume.si.siId);
            setDistrict(resumeResponse.data.responseDto.resume.gu.guId);
            setSchoolId(resumeResponse.data.responseDto.schoolInfo.id);
            setSchool(resumeResponse.data.responseDto.schoolInfo.name);
            if (
                resumeResponse.data.responseDto.schoolInfo.schoolRegister === "재학"
            ) {
                setEduState("ENROLLED");
            } else if (
                resumeResponse.data.responseDto.schoolInfo.schoolRegister === "중퇴"
            ) {
                setEduState("DROPOUT");
            } else if (
                resumeResponse.data.responseDto.schoolInfo.schoolRegister === "졸업"
            ) {
                setEduState("GRADUATE");
            } else {
                setEduState("");
            }
            setMajor(resumeResponse.data.responseDto.schoolInfo.major);
            setEduUrl(resumeResponse.data.responseDto.schoolInfo.schoolImage);
            setAwards(resumeResponse.data.responseDto.awards);
            setProjects(resumeResponse.data.responseDto.projects);
            resumeResponse.data.responseDto.techStacks.forEach((each)=>{
                if(each.techType === "Front-End"){
                    skills.push({techType: 'FRONT', description: each.description, tech: each.tech});
                }
                else if(each.techType === "Back-End"){
                    skills.push({techType: 'BACK', description: each.description, tech: each.tech});
                }
                else if(each.techType === "기타"){
                    skills.push({techType: 'ETC', description: each.description, tech: each.tech});
                }
                else if(each.techType === "AI"){
                    skills.push({techType: each.techType, description: each.description, tech: each.tech});
                }
            })
        } catch (error) {
            console.log("Error fetching resume data: ", error);
        }
    }
    useEffect(()=>{
        getProfileData();
    },[])
    useEffect(()=>{
        if(resumeId){
            getResumeData();
        }
    },[resumeId])

    const modifyResumeData = async ()=> {
        try {
            if(eduImage !== null){
                const eduFormData = new FormData();
                eduFormData.append('file', eduImage);
                const schoolInfo ={
                    "id": schoolId,
                    "userId": localStorage.getItem("userId"),
                    "name": school,
                    "major": major,
                    "schoolRegister": eduState,
                }
                eduFormData.append("schoolUpdateDto", new Blob([JSON.stringify(schoolInfo)],{type: "application/json"}));
                const response = await axios.put('/resume/school', eduFormData);
                if(response.status === 200){
                    const response = await axios.put(`/resume/${resumeId}`, {
                        "job": job,
                        "birth": date,
                        "guId": district,
                        "gender": gender,
                        "userId": localStorage.getItem("userId"),
                    });
                    if(response.status === 200){
                        const response = await axios.put(`/user/${localStorage.getItem('userId')}`, {
                            nickName: nickName,
                            introduction: introduction,
                            name: name,
                            phoneNumber: phoneNumber,
                            email: email,
                        })
                        if(response.status === 200){
                            window.alert('이력서 수정이 완료되었습니다.');
                            window.location.reload();
                        }
                        else{
                            window.alert(response.data.error.message);
                        }
                    }
                    else{
                        window.alert(response.data.error.message);
                    }
                }
            }
            else{
                const response = await axios.put(`/resume/${resumeId}`, {
                    "job": job,
                    "birth": date,
                    "guId": district,
                    "gender": gender,
                    "userId": localStorage.getItem("userId"),
                });
                if(response.status === 200){
                    const response = await axios.put(`/user/${localStorage.getItem('userId')}`, {
                        nickName: nickName,
                        introduction: introduction,
                        name: name,
                        phoneNumber: phoneNumber,
                        email: email,
                    })
                    if(response.status === 200){
                        window.alert('이력서 수정이 완료되었습니다.');
                        window.location.reload();
                    }
                    else{
                        window.alert(response.data.error.message);
                    }
                }
                else{
                    window.alert(response.data.error.message);
                }
            }
        }catch(error){
            window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    };
    return (
        <div>
            <Nav/>
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div className="container-top">
                        <h2>이력서 수정</h2>
                    </div>
                    <div className="main-content">
                        <div className="grid-container">
                            <NameInput name={name} setName={setName} />
                            <BirthInput date={date} setBirth={setDate} />
                            <GenderInput gender={gender} setGender={setGender} />
                            <JobInput job={job} setJob={setJob} />
                            <RegionInput
                                city={city}
                                district={district}
                                setCity={setCity}
                                setDistrict={setDistrict}
                            />
                            <EducationInput
                                school={school}
                                major={major}
                                eduState={eduState}
                                eduImage={eduImage}
                                setSchool={setSchool}
                                setMajor={setMajor}
                                setEduState={setEduState}
                                setEduImage={setEduImage}
                            />
                            <EmailInput email={email} setEmail={setEmail} />
                            <AwardInput
                                awards={awards}
                                congress={congress}
                                awardYear={awardYear}
                                awardType={awardType}
                                awardImage={awardImage}
                                awardImageArray={awardImageArray}
                                setAwards={setAwards}
                                setCongress={setCongress}
                                setAwardYear={setAwardYear}
                                setAwardType={setAwardType}
                                setAwardImage={setAwardImage}
                                setAwardImageArray={setAwardImageArray}
                            />
                            <SkillStackInput
                                skills={skills}
                                techType={techType}
                                tech={tech}
                                techDescription={techDescription}
                                setSkills={setSkills}
                                setTech={setTech}
                                setTechType={setTechType}
                                setTechDescription={setTechDescription}
                            />
                            <ProjectRecordInput
                                projects={projects}
                                projectName={projectName}
                                description={projectDescription}
                                gitAddress={gitAddress}
                                setProjects={setProjects}
                                setProjectName={setProjectName}
                                setDescription={setProjectDescription}
                                setGitAddress={setGitAddress}
                            />
                        </div>
                        <button id="resumeModifyButton" onClick={modifyResumeData}>수정</button>
                    </div>
                </div>
            </main>
            <Footer></Footer>
        </div>
    );
}
export default MyPageResume;
