import React, { useState } from 'react';
import DateInput from "../DateInput";
import './BirthInput.css';


function BirthInput({date, setBirth}) {
   return (
    <div id="ageBox" className="grid-element">
        <label>*생년월일:</label>
        <DateInput date={date} setDate={setBirth}/>
    </div>
  );
}

export default BirthInput;
