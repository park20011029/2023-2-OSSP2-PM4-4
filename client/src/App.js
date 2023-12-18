import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./main/MainPage";
import Project_ListPage    from "./forum/page/Project_List";
import Contest_ListPage from "./forum/page/Contest_List";
import Contest_Info_Post   from "./forum/page/Contest_Post_InfoTab";
import Contest_Info_Write from "./forum/page/Contest_Write";
import Contest_Team_ListTab from "./forum/page/Contest_Post_TeamTab";
import Team_WriteView from "./forum/page/Team_Write(View)";
import Team_WritePost from "./forum/page/Team_Write(Post)";
import SignUpPage from "./signup/SignUpPage";
import SignInPage from "./signin/SignInPage";
import MyPage from "./mypage/MyPage";
import MyPageResume from "./mypage/MyPageResume";
import MyPageReview from "./mypage/MyPageReview";
import MyPageApply from "./mypage/MyPageApply";
import MyPageWritten from "./mypage/MyPageWritten";
import MyPageReward from "./mypage/MyPageReward";
import MyPageWriteReview from "./mypage/MyPageWriteReview";
import BuildingReportList from "./adminpage/BuildingReportList";
import ContestReportList from "./adminpage/ContestReportList";
import ReviewReportList from "./adminpage/ReviewReportList";
import UserReportList from "./adminpage/UserReportList";
import ResumeReportList from "./adminpage/ResumeReportList";
import UserPage from "./UserPage";
import ReviewPage from "./ReviewPage";
import ChatList from "./chat/ChatList";
import ChatRoom from "./chat/ChatRoom";
import GoogleRedirect from "./signin/GoogleRedirect";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPage/>}/>
                {/*공모전 정보 게시글 목록 페이지 */}    <Route path="/contestInfoListPage"          element={<Contest_ListPage />}/>
                {/*공모전 정보 게시글 상세 페이지*/}     <Route path="/contestInfoPostPage/:id"      element={<Contest_Info_Post />}/>
                {/*공모전 정보 게시글 작성 페이지*/}     <Route path={"/contestInfoWritePage"}       element={<Contest_Info_Write />}/>
                {/*공모전 정보 게시글 팀원모집 탭*/}     <Route path="/contestInfoPostTeamListPage"  element={<Contest_Team_ListTab />}/>
                {/*프로젝트 게시판*/}                  <Route path="/project_forum"                element={<Project_ListPage />}/>
                {/*팀원모집 글(보기)*/}                <Route path="/TeamWriteView/:id"            element={<Team_WriteView/>}/>
                {/*팀원모집 글(쓰기)*/}                <Route path="/TeamWritePost/:postId"            element={<Team_WritePost/>}/>
                {/*채팅 목록 페이지*/}                 <Route path="/chat_list"                    element={<ChatList />}/>
                {/*채팅 페이지 */}                    <Route path="/chatRoom/:chatRoomId"         element={<ChatRoom />}/>
                {/*-------------------------------------------------------------------------------------------------------*/}
                <Route path="/sign_in" element={<SignInPage/>}></Route>
                <Route path="/sign_up" element={<SignUpPage/>}></Route>
                <Route path="/my_page" element={<MyPage/>}></Route>
                <Route path="/my_resume" element={<MyPageResume/>}></Route>
                <Route path="/my_review" element={<MyPageReview/>}></Route>
                <Route path="/my_apply" element={<MyPageApply/>}></Route>
                <Route path="/my_writeReview" element={<MyPageWriteReview/>}></Route>
                <Route path="/my_written" element={<MyPageWritten/>}></Route>
                <Route path="/my_reward" element={<MyPageReward/>}></Route>
                <Route path="/admin_contest_report" element={<ContestReportList/>}></Route>
                <Route path="/admin_building_report" element={<BuildingReportList/>}></Route>
                <Route path="/admin_review_report" element={<ReviewReportList/>}></Route>
                <Route path="/admin_user_report" element={<UserReportList/>}></Route>
                <Route path="/admin_resume_report" element={<ResumeReportList/>}></Route>
                <Route path="/userPage/:id" element={<UserPage/>}></Route>
                <Route path="/reviewPage/:id" element={<ReviewPage/>}></Route>
                <Route path="/auth/" element={<GoogleRedirect/>} />
            </Routes>
        </Router>
    );
}

export default App;
