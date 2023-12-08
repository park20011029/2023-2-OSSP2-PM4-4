function JobInput({job, setJob}) {
  const handleJobChange = (e) => {
    setJob(e.target.value);
  };

  return (
    <div id="jobBox" className="grid-element">
      <label>*직업:</label>
      <input type="text" value={job} onChange={handleJobChange} required/>
    </div>
  );
}

export default JobInput;
