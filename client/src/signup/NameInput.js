import React, { useState } from 'react';
import './NameInput.css';

function NameInput({name, setName}) {

  return (
    <div id="nameBox" className="grid-element">
      <label>*이름:</label>
      <input type="text" value={name} onChange={(e)=>setName(e.target.value)} required/>
    </div>
  );
}

export default NameInput;
