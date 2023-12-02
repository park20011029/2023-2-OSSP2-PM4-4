import React, { useState, useEffect} from 'react';
import './RegionInput.css';
import axios from "axios";

function RegionInput({city, district, setCity, setDistrict}) {
  const [cityList, setCityList] = useState([]);
  const [districtList, setDistrictList] = useState([]);

  useEffect(()=>{
           axios.get("/region/si").then(response =>{setCityList(response.data.responseDto)})
               .catch(error => {console.error('Error fetching city data: ',error)});
  },[]);
  const handleCityChange = async (e) => {
      setCity(e.target.value);
      try {
          const response = await axios.get(`/region/gu/${e.target.value}`);
          setDistrictList(response.data.responseDto);
      } catch (error) {
          // 에러 처리
          console.error('Error fetching district data:', error);
      }
  };
  const handleDistrictChange = (e) => {
    setDistrict(e.target.value);
  };

  return (
    <div id="regionBox" className="grid-element">
      <label>*지역:</label>
      <select value={city} onChange={handleCityChange} required>
        <option value="">도/특별시를 선택하세요</option>
          {cityList.map(sido => (
              <option key={sido.siId} value={sido.siId}>
                  {`${sido.si}`}
              </option>
          ))}
      </select>
      <select value={district} onChange={handleDistrictChange}>
        <option value="">시/군/구를 선택하세요</option>
          {districtList.map(sigungu => (
              <option key={sigungu.guId} value={sigungu.guId}>
                  {`${sigungu.name}`}
              </option>
          ))}
      </select>
    </div>
  );
}

export default RegionInput;
