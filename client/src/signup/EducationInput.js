function EducationInput({school, major, eduState, setSchool, setMajor, setEduState}) {

  return (
    <div id="educationBox" className="grid-element">
      <label>*최종 학력:</label>
      <input type="text" value={school} onChange={(e)=>setSchool(e.target.value)} placeholder="학교명"/>
      <input type="text" value={major} onChange={(e)=>setMajor(e.target.value)} placeholder="전공 / 계열" disabled={school === null || school===""}/>
      <select value={eduState} onChange={(e)=>setEduState(e.target.value)} disabled={major === null || major === ""}>
          <option value="">상태를 입력하세요.</option>
          <option value="DROPOUT">중퇴</option>
          <option value="ENROLLED">재학</option>
          <option value="GRADUATE">졸업</option>
      </select>
    </div>
  );
}

export default EducationInput;
