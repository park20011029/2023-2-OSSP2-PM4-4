import MainPage from "./main/MainPage";
import MyPage from "./mypage/MyPage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProjectForumPage from "./forum_project/ProjectForumPage";
import ContestForumPage from "./forum_contest/ContestForumPage";
import ChatListPage from "./chat/ChatListPage";
import LogInPage from "./login/LogInPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPage />}></Route>
                <Route path="/contest_forum" element={<ContestForumPage />}></Route>
                <Route path="/project_forum" element={<ProjectForumPage />}></Route>
                <Route path="/chat_list" element={<ChatListPage />}></Route>
                <Route path="/login" element={<LogInPage />}></Route>
                <Route path="/my_page" element={<MyPage />}></Route>
            </Routes>
        </Router>
    );
}

export default App;
