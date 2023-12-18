import React, {useState} from "react";
import "./ProfileImageUpload.css";

function ProfileImageUpload({profileImage, url, setProfileImage, setUrl}){

    // 이미지를 업로드하고 이미지 상태를 업데이트하는 함수
    const handleProfileImageUpload = (event) => {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const reader = new FileReader();
            reader.onload = (e) => {
                //setProfileImage(e.target.result);
                setProfileImage(selectedFile);
                setUrl(e.target.result);
            };
            reader.readAsDataURL(selectedFile);
        }
        console.log("selected File : ", selectedFile);
        console.log("profile Image : ", profileImage);
    };

    // 초기 이미지를 렌더링 (임의의 이미지를 사용)
    const initialImage = 'https://via.placeholder.com/150'; // 임의의 이미지 URL

    return (
        <div id="imgBox">
            <div>
                {url ? (
                    <img src={url} alt="User Image" />
                ) : (
                    <img src={initialImage} alt="Initial Image" />
                )}
            </div>
            <input type="file" accept="image/*" onChange={handleProfileImageUpload} />
        </div>
    );
}
export default ProfileImageUpload;

