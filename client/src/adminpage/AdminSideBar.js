
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import './AdminSideBar.css';

function AdminSideBar() {
    const [image, setImage] = useState(null);
    const initialImage = 'https://via.placeholder.com/150'; // 임의의 이미지 URL
    const navigate = useNavigate();
    return (
        <div id="sideBar">
            <div id="adminInfo">
                <div id="sideBarProfileImage">
                    {image ? (
                        <img src={image} alt="User Image" />
                    ) : (
                        <img src={initialImage} alt="Initial Image" />
                    )}
                </div>
                <div id="sideBarNickName" className="userData">
                    Admin
                </div>
            </div>
            <div id="tabs">
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/admin_contest_report");
                        }}
                    >
                        <p>공모전 신고 내역</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/admin_building_report");
                        }}
                    >
                        <p>팀 빌딩 신고 내역</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/admin_review_report");
                        }}
                    >
                        <p>리뷰 신고 내역</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/admin_user_report");
                        }}
                    >
                        <p>회원 신고 내역</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/admin_resume_report");
                        }}
                    >
                        <p>이력서 신고 내역</p>
                    </button>
                </div>
            </div>
        </div>
    );
}

export default AdminSideBar;
