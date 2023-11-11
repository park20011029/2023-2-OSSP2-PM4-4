import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import MainPage             from "./main/MainPage";
import MyPage               from "./mypage/MyPage";
import Project_ForumPage    from "./forum/page/Project_ForumPage";
import Project_TeamWrite    from "./forum/page/Project_TeamWrite";
import Contest_ListPage     from "./forum/page/Contest_ListPage";
import Contest_Info_Write   from "./forum/page/Contest_Info_Write";
import Contest_Team_ListTab from "./forum/page/Contest_Team_ListTab";
import Contest_Team_WriteView   from "./forum/page/Contest_Team_Write(View)";
import ChatListPage         from "./chat/ChatListPage";
import LogInPage            from "./login/LogInPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/"                 element={<MainPage />}/>
                <Route path="/contest_forum"    element={<Contest_ListPage />}/>
                <Route path="/contestWrite"     element={<Contest_Info_Write />}/>
                <Route path="/contestTeam"      element={<Contest_Team_ListTab />}/>
                <Route path="/contestTeamWrite" element={<Contest_Team_WriteView/>}/>
                <Route path="/project_forum"    element={<Project_ForumPage />}/>
                <Route path="/project_write"    element={<Project_TeamWrite />}/>
                <Route path="/chat_list"        element={<ChatListPage />}/>
                <Route path="/login"            element={<LogInPage />}/>
                <Route path="/my_page"          element={<MyPage />}/>
            </Routes>
        </Router>
    );
}

export default App;
