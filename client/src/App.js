import MainPage from "./main/MainPage";
import MyPage from "./mypage/MyPage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProjectForumPage from "./forum/page/ProjectForumPage";
import ProjectWrite from "./forum/page/ProjectWrite";
import ContestForumPage from "./forum/page/ContestForumPage";
import ContestWrite from "./forum/page/ContestWrite";
import ChatListPage from "./chat/ChatListPage";
import LogInPage from "./login/LogInPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPage />}></Route>
                <Route path="/contest_forum" element={<ContestForumPage />}></Route>
                <Route path="/contestWrite" element={<ContestWrite />}></Route>
                <Route path="/project_forum" element={<ProjectForumPage />}></Route>
                <Route path="/project_write" element={<ProjectWrite />}></Route>
                <Route path="/chat_list" element={<ChatListPage />}></Route>
                <Route path="/login" element={<LogInPage />}></Route>
                <Route path="/my_page" element={<MyPage />}></Route>
            </Routes>
        </Router>
    );
}

export default App;
