import "./UserSideBar.css";
import {useEffect, useState} from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import WithdrawalModal from "./WithdrawalModal";

function UserSideBar() {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };
    const [image, setImage] = useState(null);
    const [nickName, setNickName] = useState(null);
    const [introduction, setIntroduction] = useState(null);
    const initialImage = 'https://via.placeholder.com/150'; // 임의의 이미지 URL
    const navigate = useNavigate();
    useEffect(() => {
        axios
            .get(`/user/${localStorage.getItem('userId')}`)
            .then((response) => {
                setNickName(response.data.responseDto.nickName);
                setIntroduction(response.data.responseDto.introduction);
            })
            .catch((error) => {
                console.error("Error fetching profile data: ", error);
            });
    }, []);

    return (
        <div id="sideBar">
            <div id="userInfo">
                <div id="sideBarProfileImage">
                    {image ? (
                        <img src={image} alt="User Image" />
                    ) : (
                        <img src={initialImage} alt="Initial Image" />
                    )}
                </div>
                <div id="sideBarNickName" className="userData">
                    {nickName}
                </div>
                <div id="sideBarIntro" className="userData">
                    {introduction}
                </div>
            </div>
            <div id="tabs">
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_page");
                        }}
                    >
                        <p>프로필 수정</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_resume");
                        }}
                    >
                        <p>이력서 수정</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_review");
                        }}
                    >
                        <p>리뷰 조회</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_writeReview");
                        }}
                    >
                        <p>리뷰 작성</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_apply");
                        }}
                    >
                        <p>지원 내역</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_written");
                        }}
                    >
                        <p>내가 쓴 글</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={() => {
                            navigate("/my_reward");
                        }}
                    >
                        <p>리워드</p>
                    </button>
                </div>
                <div>
                    <button
                        className="sideBarTabs"
                        onClick={openModal}
                    >
                        <p>회원 탈퇴</p>
                    </button>
                    <WithdrawalModal showModal={isModalOpen} onClose={closeModal} />
                </div>
            </div>
        </div>
    );
}

export default UserSideBar;
