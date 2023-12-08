import React, { useState } from 'react';
import "./GenderInput.css";
function GenderInput({gender, setGender}) {

  const handleGenderChange = (e) => {
    setGender(e.target.value);
  };

  return (
    <div id="genderBox" className="grid-element">
      <label>*성별:</label>
      <select value={gender} onChange={handleGenderChange} required>
        <option value="">선택하세요</option>
        <option value={true}>남성</option>
        <option value={false}>여성</option>
      </select>
    </div>
  );
}

export default GenderInput;
