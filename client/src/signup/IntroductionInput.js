import React, { useState } from "react";
import "./IntroductionInput.css";

function IntroductionInput({ introduction, setIntroduction }) {
  const [byteCount, setByteCount] = useState(0);

  const handleIntroductionChange = (e) => {
    const text = e.target.value;
    let countedBytes = 0;

    for (let i = 0; i < text.length; i++) {
      const char = text[i];
      // 유니코드 코드 포인트를 확인하여 한글인지 영어/다른 문자인지 판별
      const codePoint = text.codePointAt(i);
      if (codePoint <= 127) {
        countedBytes++; // 영어 및 다른 문자는 1바이트
      } else {
        countedBytes += 2; // 한글은 2바이트
      }
    }

    if (countedBytes <= 50) {
      setIntroduction(text);
      setByteCount(countedBytes);
    }
  };

  return (
    <div id="introductionBox">
      <label>
        *한 줄 소개 :<br />
        <span>(최대 50bytes)</span>
      </label>
      <input
        type="text"
        value={introduction}
        onChange={handleIntroductionChange}
        required
      />
      <p>{byteCount} / 50 bytes</p>
    </div>
  );
}

export default IntroductionInput;
