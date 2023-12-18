import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import Nav from "./layout/Nav";
import UserSideBar from "./mypage/UserSideBar";
import StarRate from "./layout/StarRate";
import {Siren} from "./Siren";
import ReportModal from "./ReportModal";
import List_PageNumber from "./layout/List_PageNumber";
import Footer from "./layout/Footer";

function UserPage(){
    const { id } = useParams();
    const userId = 1; //Todo: userId
    const [resumeId, setResumeId] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);
    const [nickName, setNickName] = useState(null);
    const [introduction, setIntroduction] = useState(null);
    const [name, setName] = useState(null);
    const [date, setDate] = useState(null);
    const [gender, setGender] = useState(null);
    const [job, setJob] = useState(null);
    const [city, setCity] = useState(null);
    const [district, setDistrict] = useState(null);
    const [education, setEducation] = useState({name:null, major:null, schoolRegister:null});
    const [email, setEmail] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState("010-1234-5678");
    const [awards, setAwards] = useState([]);
    const [skills, setSkills] = useState([]);
    const [projects, setProjects] = useState([]);
    /* 리뷰 */
    const [reviewList, setReviewList] = useState([]);
    const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
    const [isResumeModalOpen, setIsResumeModalOpen] = useState(false);
    const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);
    const [reviewPageInfo, setReviewPageInfo] = useState({
        pageNumber: 1, //페이지 번호
        pageSize: 4, //한 페이지 당 게시글 수
        pageLength: 10, //페이지 시작번호(1 + n*pageSize)
        pageCount: 10, //총 페이지 개수
    });
    const [selectedCategory, setSelectedCategory] = useState("");
    const getReview = async () => {
        try {
            const response = await axios.get(
                `/myPage/myReview/${localStorage.getItem("userId")}?page=${
                    reviewPageInfo.pageNumber - 1
                }&size=${reviewPageInfo.pageSize}`
            );
            setReviewPageInfo({
                pageNumber: response.data.responseDto.pageInfo.currentPage,
                pageSize: reviewPageInfo.pageSize,
                pageLength: reviewPageInfo.pageLength,
                pageCount: response.data.responseDto.pageInfo.totalPages,
            });
            setReviewList(response.data.responseDto.myReviews);
            console.log("받은", response.data.responseDto);
        } catch (error) {
            console.log("Error fetching review list data: ", error);
        }
    };
    useEffect(() => {
        getReview();
    }, [reviewPageInfo.pageNumber]);

    const getUserData = async()=>{
        try{
            const response = await axios.get(`/userReport/user/${id}`);
            console.log("프로필 : " ,response.data.responseDto);
            setEmail(response.data.responseDto.email);
            setIntroduction(response.data.responseDto.introduction);
            setName(response.data.responseDto.name);
            setNickName(response.data.responseDto.nickName);
            setPhoneNumber(response.data.responseDto.phoneNumber);
            setResumeId(response.data.responseDto.resumeId);
            setImageUrl(response.data.responseDto.url);
            if(response.status === 200){
                const response1 = await axios.get(`/resumeReport/resume/${response.data.responseDto.resumeId}`);
                console.log("이력서 : ", response1.data.responseDto);
                setAwards(response1.data.responseDto.awards);
                setProjects(response1.data.responseDto.projects);
                setSkills(response1.data.responseDto.techStacks);
                setDate(response1.data.responseDto.resume.birth);
                setGender(response1.data.responseDto.resume.gender);
                setJob(response1.data.responseDto.resume.job);
                setCity(response1.data.responseDto.resume.si.si);
                setDistrict(response1.data.responseDto.resume.gu.name);
                setEducation({...education, name: response1.data.responseDto.schoolInfo.name, major: response1.data.responseDto.schoolInfo.major, schoolRegister: response1.data.responseDto.schoolInfo.schoolRegister});
                console.log("수상 : ", awards);
                console.log("학력 : ", education);
                console.log(imageUrl);
            }
        }catch(error){
            console.error("error fetching user data : ", error);
        }
    }
    useEffect(() => {
        getUserData();
    }, [id]);


    return (
        <div className="font-['NoToSansKR']">
            <div className="profileContainer w-[80%] mx-auto my-[50px]">
                <div className="container-top">
                    <h2>프로필</h2>
                </div>
                <div className="report">
                    <Siren width={20} height={20} />
                    <button onClick={() => setIsProfileModalOpen(true)}>유저 신고</button>
                    <ReportModal
                        showModal={isProfileModalOpen}
                        item={{
                            targetId:id,
                            targetName:nickName,
                            userId:userId
                        }}
                        category={"유저"}
                        onClose={() => setIsProfileModalOpen(false)}
                    />
                </div>
                <div className="flex items-center justify-around">
                    <img src={imageUrl} alt="profileImage"/>
                    <div>
                        <div className="flex"><div className="mb-[10px] font-bold">닉네임</div> : {nickName}</div><br/>
                        <div className="flex"><div className="mb-[10px] font-bold">한 줄 소개</div> : {introduction}</div>
                    </div>
                </div>
            </div>
            <div className="resumeContainer w-[80%] mx-auto my-[50px]">
                <div className="container-top">
                    <h2>이력서</h2>
                </div>
                <div className="report">
                    <Siren width={20} height={20} />
                    <button onClick={() => setIsResumeModalOpen(true)}>이력서 신고</button>
                    <ReportModal
                        showModal={isResumeModalOpen}
                        item={{
                            targetId:id,
                            targetName:nickName,
                            userId:userId,
                            resumeId:resumeId,
                        }}
                        category={"이력서"}
                        onClose={() => setIsResumeModalOpen(false)}
                    />
                </div>
                <div className="grid grid-cols-2 gap-y-[20px] gap-x-[60px] px-[20px]">
                    <div className="flex items-center border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">이름</div> : {name}</div>
                    <div className="flex items-center border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">생년월일</div> : {date}</div>
                    <div className="flex items-center border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">성별</div> : {gender ? ("남성"):("여성")}</div>
                    <div className="flex items-center border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">직업</div> : {job}</div>
                    <div className="flex items-center border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">지역</div> : {city} {district}</div>
                    <div className="flex items-center border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">학력</div> : {education.name} {education.major} {education.schoolRegister}</div>
                    <div className="flex items-center col-span-2 border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]"><div className="font-bold">Email</div> : {email}</div>
                    <div className="col-span-2 border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]">
                        <div className="mb-[10px] font-bold">수상 내역</div>
                        {awards.map((award) => (
                            <div key={award.id} className="flex items-center justify-between">
                                <div>ID : {award.id}</div><div>Competition : {award.competition}</div>  <div>Year : {award.awardYear}</div> <div>Type : {award.awardType}</div>
                            </div>
                        ))}
                    </div>
                    <div className="col-span-2 border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]">
                        <div className="mb-[10px] font-bold">기술 스택</div>
                        {skills.map((skill) => (
                            <div key={skill.id} className="flex items-center justify-between">
                                <div>ID : {skill.id}</div> <div>Tech : {skill.tech}</div> <div>TechType : {skill.techType}</div> <div>Description : {skill.description}</div>
                            </div>
                        ))}
                    </div>
                    <div className="col-span-2 border-b-[1px] border-b-[#000000] pb-[20px] px-[10px]">
                        <div className="mb-[10px] font-bold">프로젝트 기록</div>
                        {projects.map((project) => (
                            <div key={project.id} className="flex items-center justify-between">
                                <div>ID : {project.id}</div> <div>ProjectName : {project.projectName}</div> <div>Description : {project.description}</div> <div>GitUrl : {project.gitUrl}</div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
            <div className="reviewContainer w-[80%] mx-auto my-[50px]">
                <div className="review-area">
                    <div className="container-top">
                        <h2>리뷰</h2>
                    </div>
                    <div>
                        {reviewList.length ? (
                            reviewList.map((item, index) => (
                                <div className="review-container">
                                    <div key={index} className="reviews">
                                        <div className="review-header">
                                            <div className="star-ratings">
                                                <StarRate value={(item.score) * 10} />

                                            </div>
                                            <div className="report">
                                                <Siren width={20} height={20} />
                                                <button onClick={() => setIsReviewModalOpen(true)}>신고</button>
                                                <ReportModal
                                                    showModal={isReviewModalOpen}
                                                    item={item}
                                                    category={"리뷰"}
                                                    onClose={() => setIsReviewModalOpen(false)}
                                                />
                                            </div>
                                            <div className="reviewedUser">To : {item.reviewee}</div>
                                            <div className="reviewedUser">From : {item.reviewer}</div>
                                            <div className="reviewedDate">{item.createAt}</div>
                                        </div>
                                        <div className="review-body">{item.content}</div>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="post-none">받은 리뷰가 없습니다.</div>
                        )}
                        {reviewList.length ? (
                            <div><List_PageNumber
                                pageInfo={reviewPageInfo}
                                setPageInfo={setReviewPageInfo}
                            /></div>
                        ):(<></>)}
                    </div>
                </div>
            </div>
        </div>
    )
}
export default UserPage;