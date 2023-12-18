import React, { useState, useEffect } from 'react';

function Statistics() {
  const statsData = [
    { label: '빌딩된 팀 수', value: 1000 },
    { label: '공모전 수', value: 500 },
    { label: '유저 수', value: 10000 },
  ];

  const [currentStatIndex, setCurrentStatIndex] = useState(0);


  // useEffect(() => {
  //   const interval = setInterval(() => {
  //     setCurrentStatIndex((prevIndex) => (prevIndex + 1) % statsData.length);
  //   }, 1000); // 슬라이드 전환 시간(이후 조절)
  //
  //   return () => clearInterval(interval);
  // }, [statsData.length]);

  return (
    <div className="flex border-[2px] border-[black] rounded-br-[40px] h-[300px] justify-normal items-center ml-100 bg-[black]  font-['NotoSansKR'] shadow-[0_25px_10px_-15px_rgba(0,0,0,0.3)]">
      {
        statsData.map((stat, index) => (
          <div
            key={index}
            className={`text-[white] border-[1px] border-[white] bg-zinc-900 border-solid rounded-[12px] h-[200px] mx-auto w-1/4 shadow-[20px_25px_10px_-15px_rgba(255,255,255,0.2)]`}>
            <div className="h-full flex flex-col items-center justify-center text-center text-2xl font-bold">
              <div className="mb-[30px]">
                {stat.label}</div>
              <div className="">
                {stat.value}</div>
            </div>
          </div>
        ))
      }
    </div >
  );
}

export default Statistics;
