import React, { useState } from "react";
import axios from "axios";
import "./SignUpPage.css";
import Nav from "../layout/Nav";
import ProfileImageUpload from "./ProfileImageUpload";
import NickNameInput from "./NickNameInput";
import IntroductionInput from "./IntroductionInput";
import NameInput from "./NameInput";
import BirthInput from "./BirthInput";
import GenderInput from "./GenderInput";
import JobInput from "./JobInput";
import EmailInput from "./EmailInput";
import RegionInput from "./RegionInput";
import EducationInput from "./EducationInput";
import AwardInput from "./AwardInput";
import SkillStackInput from "./SkillStackInput";
import ProjectRecordInput from "./ProjectRecordInput";
import Footer from "../layout/Footer";

function SignUpPage() {
  const [profileImage, setProfileImage] = useState(null);
  const [url, setUrl] = useState('https://via.placeholder.com/150');
  const [eduImage, setEduImage] = useState(null);
  const [nickName, setNickName] = useState(null);
  const [isNickNameAvailable, setIsNickNameAvailable] = useState(false);
  const [introduction, setIntroduction] = useState(null);
  const [name, setName] = useState(null);
  const [date, setDate] = useState(null);
  const [gender, setGender] = useState(true);
  const [job, setJob] = useState(null);
  const [city, setCity] = useState(null);
  const [district, setDistrict] = useState(null);
  const [school, setSchool] = useState(null);
  const [major, setMajor] = useState(null);
  const [eduState, setEduState] = useState(null);
  const [email, setEmail] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState('010-1234-5678');
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

  const handleUserData = async () => {
    if (isNickNameAvailable) {
      try {
        const profileFormData = new FormData();
        profileFormData.append('file', profileImage);
        const signUp = {
          "nickName": nickName,
          "introduction": introduction,
          "name": name,
          "email": email,
          "phoneNumber": phoneNumber,
        }

        profileFormData.append("userRequestDto", new Blob([JSON.stringify(signUp)],{type: "application/json"}));
        const userResponse = await axios.post("/user/signup", profileFormData);
        if (userResponse.status === 200) {
          console.log("PROFILE POST 요청 성공", userResponse.data.responseDto);
          const projectsWithUserId = projects.map(project => ({
            ...project, userId: userResponse.data.responseDto}));
          console.log(projectsWithUserId);
          const resume = {
            "job": job,
            "birth": date,
            "gender": gender,
            "guId": district,
            "schoolInfo": {
              "userId": userResponse.data.responseDto,
              "name": school,
              "major": major,
              "schoolRegister": eduState,
            },
            "projects" : projectsWithUserId,
            "techStacks": skills,
          }
          const resumeFormData = new FormData();
          resumeFormData.append('file', eduImage);
          resumeFormData.append("resumeRequestDto", new Blob([JSON.stringify(resume)],{type: "application/json"}));
          const resumeResponse = await axios.post(
              `/resume/${userResponse.data.responseDto}`, resumeFormData);
          if(resumeResponse.status === 200) {
            console.log("RESUME POST 요청 성공", resumeResponse.data.responseDto);
            let isAwardOK = true;
            const awardFormData = new FormData();
            for(let i=0; i<awardImageArray.length; i++) {
              awardFormData.append('file', awardImageArray[i].awardImage);
              let award ={
                "userId": userResponse.data.responseDto,
                "competition": awards[i].competition,
                "awardYear":awards[i].awardYear,
                "awardType":awards[i].awardType,
              }
              awardFormData.append("awardRequestDto", new Blob([JSON.stringify(award)],{type:"application/json"}));
              const awardResponse = await axios.post(`/resume/award/${resumeResponse.data.responseDto}`,awardFormData);
              if(awardResponse.status !== 200){
                isAwardOK = false;
              }
            }
            if(isAwardOK === true){
              window.alert("회원 가입 성공");
              // window.location.href = '/';
            }
            else{
              window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
          }
          else {
            window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
          }
        } else {
          // userResponse status != 200
          window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
      } catch (error) {
        window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
      }
    } else {
      window.alert("닉네임 중복 여부를 확인해 주세요.");
    }
  };

  return (
      <div>
        <Nav></Nav>
        <div className="main">
          <div className="container-top">
            <h2>프로필</h2>
            <p>* 항목은 필수 입력</p>
          </div>
          <div id="profile-container">
            <ProfileImageUpload profileImage={profileImage} url={url} setProfileImage={setProfileImage} setUrl={setUrl}/>
            <div>
              <div>
                <NickNameInput
                    nickName={nickName}
                    setNickName={setNickName}
                    isNickNameAvailable={isNickNameAvailable}
                    setIsNickNameAvailable={setIsNickNameAvailable}
                />
              </div>
              <div>
                <IntroductionInput
                    introduction={introduction}
                    setIntroduction={setIntroduction}
                />
              </div>
            </div>
          </div>

          <div className="container-top">
            <h2>이력서</h2>
            <p>사이트의 다양한 기능을 사용하기 위해선 입력이 필요합니다.</p>
          </div>
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
                setTechType={setTechType}
                setTech={setTech}
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
          <div className="complete">
            <button onClick={handleUserData}>완료</button>
          </div>
        </div>
        <Footer></Footer>
      </div>
  );
}
export default SignUpPage;
