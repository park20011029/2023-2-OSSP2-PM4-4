import React, { useState } from 'react';
import "./EmailInput.css";

function EmailInput({email, setEmail}) {

  const [isEmailValid, setIsEmailValid] = useState(true); // 유효한 이메일 형식 여부

  const handleEmailChange = (e) => {
    const inputEmail = e.target.value;
    setEmail(inputEmail);

    // 이메일 유효성 검사 정규식
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const isValid = emailPattern.test(inputEmail);

    setIsEmailValid(isValid);
  };

  return (
    <div id="emailBox" className="grid-element">
      <label>*E-mail:</label>
      <input type="email" value={email} onChange={handleEmailChange} required/>
      {!isEmailValid && <p style={{ color: 'red' }}>유효한 이메일 주소를 입력하세요.</p>}
    </div>
  );
}

export default EmailInput;
