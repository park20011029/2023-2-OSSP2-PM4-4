import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import MainPage             from "./main/MainPage";
import MyPage               from "./mypage/MyPage";
import Project_ListPage    from "./forum/page/Project_ListPage";
import Contest_ListPage     from "./forum/page/Contest_ListPage";
import Contest_Info_Post   from "./forum/page/Contest_Info_Post";
import Contest_Info_Write from "./forum/page/Contest_Info_Write";
import Contest_Team_ListTab from "./forum/page/Contest_Team_ListTab";
import Contest_Team_WriteView   from "./forum/page/Contest_Team_Write(View)";
import Contest_Team_WritePost   from "./forum/page/Contest_Team_Write(Post)"
import ChatListPage         from "./chat/ChatListPage";
import LogInPage            from "./login/LogInPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/"                 element={<MainPage />}/>
                {/*공모전 정보 게시글 목록 페이지 */}    <Route path="/contestInfoListPage"    element={<Contest_ListPage />}/>
                {/*공모전 정보 게시글 상세 페이지*/}     <Route path="/contestInfoPostPage"     element={<Contest_Info_Post />}/>
                {/*공모전 정보 게시글 작성 페이지*/}     <Route path={"/contestInfoWritePage"}   element={<Contest_Info_Write />}/>
                {/*공모전 정보 게시글 팀원모집 탭*/}     <Route path="/contestInfoPostTeamListPage"      element={<Contest_Team_ListTab />}/>
                {/*공모전 팀원모집 글(보기)*/}          <Route path="/contestTeamWriteView/:id"     element={<Contest_Team_WriteView/>}/>
                {/*공모전 팀원모집 글(쓰기)*/}          <Route path="/contestTeamWritePost"     element={<Contest_Team_WritePost/>}/>
                <Route path="/project_forum"    element={<Project_ListPage />}/>
                <Route path="/chat_list"        element={<ChatListPage />}/>
                <Route path="/login"            element={<LogInPage />}/>
                <Route path="/my_page"          element={<MyPage />}/>
            </Routes>
        </Router>
    );
}

export default App;
