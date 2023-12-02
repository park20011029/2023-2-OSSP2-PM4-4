import React, {useState} from "react";
import "./ProfileImageUpload.css";

function ProfileImageUpload(){
    const [image, setImage] = useState(null);

    // 이미지를 업로드하고 이미지 상태를 업데이트하는 함수
    const handleImageUpload = (event) => {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const reader = new FileReader();
            reader.onload = (e) => {
                setImage(e.target.result);
            };
            reader.readAsDataURL(selectedFile);
        }
    };

    // 초기 이미지를 렌더링 (임의의 이미지를 사용)
    const initialImage = 'https://via.placeholder.com/150'; // 임의의 이미지 URL

    return (
        <div id="imgBox">
            <div>
                {image ? (
                    <img src={image} alt="User Image" />
                ) : (
                    <img src={initialImage} alt="Initial Image" />
                )}
            </div>
            <input type="file" accept="image/*" onChange={handleImageUpload} />
        </div>
    );
}
export default ProfileImageUpload;

