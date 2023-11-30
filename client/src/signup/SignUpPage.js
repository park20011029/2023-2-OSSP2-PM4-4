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
  const [eduState, setEduState] = useState(null);
  const [email, setEmail] = useState(null);
  const [awards, setAwards] = useState([]);
  const [congress, setCongress] = useState(null);
  const [awardYear, setAwardYear] = useState(null);
  const [awardType, setAwardType] = useState(null);
  const [awardFile, setAwardFile] = useState(null);
  const [skills, setSkills] = useState([]);
  const [techType, setTechType] = useState(null);
  const [tech, setTech] = useState(null);
  const [techDescription, setTechDescription] = useState(null);
  const [projects, setProjects] = useState([]);
  const [projectName, setProjectName] = useState(null);
  const [projectDescription, setProjectDescription] = useState(null);
  const [gitAddress, setGitAddress] = useState(null);

  const handleUserData = async () => {
    //if (isNickNameAvailable) {
      try {
        const userResponse = await axios.post("/user/signup", {
          nickName: nickName,
          introduction: introduction,
          name: name,
          email: email,
          phoneNumber: "010-1234-5678",
        });
        if (userResponse.status === 200) {
          console.log("PROFILE POST 요청 성공");
          const resumeResponse = await axios.post(
              `/resume/${userResponse.data.responseDto}`,
              {
                job: job,
                birth: date,
                gender: gender,
                guId: district,
                schoolInfo: {
                  name: school,
                  schoolRegister: eduState,
                },
              }
          );
          if (resumeResponse.status === 200) {
            console.log("RESUME POST 요청 성공");
            let awardError = 0;
            awards.forEach((award) => {
              const data = {
                competition: award.competition,
                awardYear: award.awardYear,
                awardType: award.awardType,
              };
              axios
                  .post(`/resume/award/${resumeResponse.data.responseDto}`, data)
                  .then((awardResponse) => {
                    if (awardResponse.status !== 200) {
                      window.alert(awardResponse.data.error.message);
                      awardError = 1;
                    }
                  })
                  .catch((error) => {
                    window.alert(
                        "오류가 발생했습니다. 잠시 후 다시 시도해주세요."
                    );
                  });
            });
            if (awardError === 0) {
              console.log("AWARD POST 요청 성공");
              let techError = 0;
              skills.forEach((tech) => {
                const data = {
                  techType: tech.techType,
                  tech: tech.tech,
                  description: tech.description,
                };
                axios
                    .post(
                        `/resume/techStack/${resumeResponse.data.responseDto}`,
                        data
                    )
                    .then((techResponse) => {
                      if (techResponse.status !== 200) {
                        window.alert(techResponse.data.error.message);
                        techError = 1;
                      }
                    })
                    .catch((error) => {
                      window.alert(
                          "오류가 발생했습니다. 잠시 후 다시 시도해주세요."
                      );
                    });
              });
              if (techError === 0) {
                console.log("TECH POST 요청 성공");
                let projectError = 0;
                projects.forEach((project) => {
                  const data = {
                    projectName: project.projectName,
                    description: project.description,
                    gitUrl: project.gitUrl,
                  };
                  axios
                      .post(
                          `/resume/project/${resumeResponse.data.responseDto}`,
                          data
                      )
                      .then((projectResponse) => {
                        if (projectResponse.status !== 200) {
                          window.alert(projectResponse.data.error.message);
                          techError = 1;
                        }
                      })
                      .catch((error) => {
                        window.alert(
                            "오류가 발생했습니다. 잠시 후 다시 시도해주세요."
                        );
                      });
                });
                if (projectError === 0) {
                  window.alert("회원 가입 성공");
                  window.location.href = '/';
                } else {
                  // projectError != 0
                  window.alert('프로젝트 기록 오류 발생');
                }
              } else {
                // techError != 0
                window.alert('기술 스택 오류 발생');
              }
            } else {
              // awardError !=0
              window.alert('수상 내역 오류 발생');
            }
          } else {
            // resumeResponse status != 200
            window.alert(resumeResponse.data.error.message);
          }
        } else {
          // userResponse status != 200
          window.alert(userResponse.data.error.message);
        }
      } catch (error) {
        window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
      }
    } ///else {
      //window.alert("닉네임 중복 여부를 확인해 주세요.");
    //}
  //};

  return (
      <div>
        <Nav></Nav>
        <div className="main">
          <div className="container-top">
            <h2>프로필</h2>
            <p>* 항목은 필수 입력</p>
          </div>
          <div id="profile-container">
            <ProfileImageUpload></ProfileImageUpload>
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
                eduState={eduState}
                setSchool={setSchool}
                setEduState={setEduState}
            />
            <EmailInput email={email} setEmail={setEmail} />
            <AwardInput
                awards={awards}
                congress={congress}
                awardYear={awardYear}
                awardType={awardType}
                awardFile={awardFile}
                setAwards={setAwards}
                setCongress={setCongress}
                setAwardYear={setAwardYear}
                setAwardType={setAwardType}
                setAwardFile={setAwardFile}
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
