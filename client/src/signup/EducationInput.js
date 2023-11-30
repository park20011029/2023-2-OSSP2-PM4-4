function EducationInput({school, eduState, setSchool, setEduState}) {

  return (
    <div id="educationBox" className="grid-element">
      <label>*최종 학력:</label>
      <input type="text" value={school} onChange={(e)=>setSchool(e.target.value)}/>
      <select value={eduState} onChange={(e)=>setEduState(e.target.value)} disabled={school === null}>
          <option value="">상태를 입력하세요.</option>
          <option value="DROPOUT">중퇴</option>
          <option value="ENROLLED">재학</option>
          <option value="GRADUATE">졸업</option>
      </select>
    </div>
  );
}

export default EducationInput;
