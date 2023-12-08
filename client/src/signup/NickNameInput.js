import React, { useState } from "react";
import axios from "axios";
import "./NickNameInput.css";

function NickNameInput({
                           nickName,
                           setNickName,
                           isNickNameAvailable,
                           setIsNickNameAvailable,
                       }) {
    const [error, setError] = useState("");

    const checkNickNameAvailability = async () => {
        if (nickName === "") {
            setError("닉네임을 입력하세요.");
        } else {
            setError("");
            const response = await axios.put(`/user/nickname/${nickName}`);
            if (response.status === 200) {
                if (response.data.responseDto === false) {
                    // 닉네임 사용 가능
                    setIsNickNameAvailable(true);
                } else {
                    // 닉네임 사용 불가능
                    setIsNickNameAvailable(false);
                    setError("이미 사용중인 닉네임입니다.");
                }
            } else {
                // 닉네임 확인 중 오류 발생
                window.alert(response.data.error.message);
                setIsNickNameAvailable(false);
                setNickName("");
            }
        }
    };

    return (
        // 길이제한 필요
        <div id="nickNameBox">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;*닉네임:</label>
            <input
                type="text"
                value={nickName}
                onChange={(e) => setNickName(e.target.value)}
            />
            <button onClick={checkNickNameAvailability}>중복 확인</button>
            {!isNickNameAvailable && <p style={{ color: "red" }}>{error}</p>}
            {isNickNameAvailable && (
                <p style={{ color: "green" }}>사용 가능한 닉네임입니다.</p>
            )}
        </div>
    );
}

export default NickNameInput;
