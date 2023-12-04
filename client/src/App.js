import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./main/MainPage";
import MyPage from "./mypage/MyPage";
import Project_ListPage    from "./forum/page/Project_List";
import Contest_ListPage from "./forum/page/Contest_List";
import Contest_Info_Post   from "./forum/page/Contest_Post_InfoTab";
import Contest_Info_Write from "./forum/page/Contest_Write";
import Contest_Team_ListTab from "./forum/page/Contest_Post_TeamTab";
import Contest_Team_WriteView   from "./forum/page/Team_Write(View)";
import Contest_Team_WritePost   from "./forum/page/Team_Write(Post)"
import ChatListPage from "./chat/ChatListPage";
import ChatRoomSocket from "./chat/ChatRoomSocket";
import SignUpPage from "./signup/SignUpPage";
import SignInPage from "./signin/SignInPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPage/>}></Route>
                {/*공모전 정보 게시글 목록 페이지 */}    <Route path="/contestInfoListPage"    element={<Contest_ListPage />}/>
                {/*공모전 정보 게시글 상세 페이지*/}     <Route path="/contestInfoPostPage"     element={<Contest_Info_Post />}/>
                {/*공모전 정보 게시글 작성 페이지*/}     <Route path={"/contestInfoWritePage"}   element={<Contest_Info_Write />}/>
                {/*공모전 정보 게시글 팀원모집 탭*/}     <Route path="/contestInfoPostTeamListPage"      element={<Contest_Team_ListTab />}/>
                {/*팀원모집 글(보기)*/}                <Route path="/contestTeamWriteView/:id"     element={<Contest_Team_WriteView/>}/>
                {/*팀원모집 글(쓰기)*/}                <Route path="/contestTeamWritePost"     element={<Contest_Team_WritePost/>}/>
                <Route path="/chat_socket" element={<ChatRoomSocket />}/>
                <Route path="/project_forum"    element={<Project_ListPage />}/>
                <Route path="/chat_list"        element={<ChatListPage />}/>
                <Route path="/sign_in" element={<SignInPage/>}></Route>
                <Route path="/sign_up" element={<SignUpPage/>}></Route>
                <Route path="/my_page" element={<MyPage/>}></Route>
            </Routes>
        </Router>
    );
}

export default App;
