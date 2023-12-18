import React, { useState, useEffect} from "react";
import axios from "axios";
import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import UserSideBar from "./UserSideBar";
import NickNameInput from "../signup/NickNameInput";
import IntroductionInput from "../signup/IntroductionInput";
import ProfileImageUpload from "../signup/ProfileImageUpload";
import "./MyPage.css";

function MyPage() {
    const [nickName, setNickName] = useState(null);
    const [profileImage, setProfileImage] = useState(null);
    const [url, setUrl] = useState('https://via.placeholder.com/150');
    const [isNickNameAvailable, setIsNickNameAvailable] = useState(true);
    const [introduction, setIntroduction] = useState(null);
    const [name, setName] = useState(null);
    const [email, setEmail] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState("010-1234-5678");
    useEffect(() => {
        axios
            .get(`/user/${localStorage.getItem('userId')}`)
            .then((response) => {
                console.log(response.data);
                setNickName(response.data.responseDto.nickName);
                setIntroduction(response.data.responseDto.introduction);
                setName(response.data.responseDto.name);
                setEmail(response.data.responseDto.email);
                setUrl(response.data.responseDto.url);
            })
            .catch((error) => {
                console.error("Error fetching profile data: ", error);
            });
    }, []);

    const modifyProfileData = async ()=> {
        if(isNickNameAvailable){
            try {
                const profileFormData = new FormData();
                profileFormData.append("file", profileImage);
                const response = await axios.put(`/user/${localStorage.getItem('userId')}`, {
                    nickName: nickName,
                    introduction: introduction,
                    name: name,
                    email: email,
                    phoneNumber: phoneNumber,
                });
                if(response.status === 200){
                    if(profileImage !== null){
                        const response = await axios.put(`/user/userImage/${localStorage.getItem('userId')}`,profileFormData);
                        if(response.status === 200) {
                            window.alert('프로필 수정이 완료되었습니다.');
                            window.location.reload();
                        }
                    }
                    else{
                        window.alert('프로필 수정이 완료되었습니다.');
                        window.location.reload();
                    }
                }
                else{
                    window.alert(response.data.error.message);
                }
            }catch(error){
                window.alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        }
        else{
            window.alert("닉네임 중복 여부를 확인해 주세요.");
        }
    };

    return (
        <div>
            <Nav/>
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div className="container-top">
                        <h2>프로필 수정</h2>
                    </div>
                    <div className="main-content">
                        <div id="profileImg" className="main-element">
                            <ProfileImageUpload profileImage={profileImage} url={url} setProfileImage={setProfileImage} setUrl={setUrl}/>
                        </div>
                        <div id="nick" className="main-element">
                            <NickNameInput nickName={nickName} setNickName={setNickName} isNickNameAvailable={isNickNameAvailable} setIsNickNameAvailable={setIsNickNameAvailable}/>
                        </div>
                        <div id="intro" className="main-element">
                            <IntroductionInput introduction={introduction} setIntroduction={setIntroduction}/>
                        </div>
                        <button id="profileModifyButton" onClick={modifyProfileData}>수정</button>
                    </div>
                </div>
            </main>
            <Footer></Footer>
        </div>
    );
}
export default MyPage;
