import { ChatIcon } from "./ChatIcon";
import { MyPageIcon } from "./MyPageIcon";
import { Link, useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {Cash} from "../Cash";

const Nav = () => {
  const navigate = useNavigate();
  const [point, setPoint] = useState(null);
  const handleLogout = () => {
    // 로그아웃 처리 후
    // ...
    localStorage.removeItem('userId');
    localStorage.removeItem('isLoggedIn');
    navigate('/');
  };
  const getPoint = async() =>{
    try{
      const response = await axios.get(`/user/${localStorage.getItem('userId')}`);
      setPoint(response.data.responseDto.point);
    }catch (error){
      console.error("Error fetching profile data: ", error);
    }
  }
  useEffect(() => {
    getPoint();
  }, [point]);
  return (
      <header>
        <div className="h-[90px] p-[15px] border-b-[#000000] shadow-md mb-[20px]">
          <div className="flex items-center justify-between">
            <div className="flex items-center h-[60px]">
              <div className="mr-[20px]">
                <button
                    onClick={() => {
                      navigate("/");
                    }}
                >
                  <p className="border-[#000000] rounded-full p-[14px] text-[20px] text-blue-400 font-bold">
                    PM4
                  </p>
                </button>
              </div>
              <div className="flex items-center h-[90px] px-[60px]">
                <button
                    className="after:absolute after:content-[''] after:w-0 after:b-0 after:l-1/2 after:h-[3px] after:-translate-x-2/4 after:bg-blue-400 after:transition-all after:transition .5s ease-out hover:after:w-[100px]"
                    onClick={() => {
                      navigate("/contestInfoListPage");
                    }}
                >
                  <p className="text-[17px] font-['NotoSansKR'] font-bold ">
                    공모전 게시판
                  </p>
                </button>
              </div>
              <div className="flex items-center h-[90px] px-[60px]">
                <button
                    className="after:absolute after:content-[''] after:w-0 after:b-0 after:l-1/2 after:h-[3px] after:-translate-x-2/4 after:bg-blue-400 after:transition-all after:transition .5s ease-out hover:after:w-[110px]"
                    onClick={() => {
                      navigate("/project_forum");
                    }}
                >
                  <p className="text-[17px] font-['NotoSansKR'] font-bold">
                    프로젝트 게시판
                  </p>
                </button>
              </div>
            </div>
            <div className="flex items-center h-[60px]">
              <div className="flex items-center">
                {localStorage.getItem('isLoggedIn') ? (
                    <div className="flex items-center border-[1px] border-[#243c5a] shadow-black h-[40px] p-[8px] font-['NotoSansKR'] rounded">
                      <Cash width={20} height={20}/><span className="ml-[10px]">{point}p</span>
                    </div>
                ) : (
                    <></>
                )}
              </div>
              <div className="flex items-center ml-[30px]">
                <button
                    onClick={() => {
                      navigate("/chat_list");
                    }}
                >
                  <ChatIcon></ChatIcon>
                </button>
              </div>
              <div className="flex items-center ml-[30px]">
                {localStorage.getItem('isLoggedIn') ? (
                    <button
                        className="border-[1px] border-[#243c5a] rounded-lg p-[8px] hover:bg-blue-400 hover:text-[#ffffff]"
                        onClick={handleLogout}
                    >
                      Sign Out
                    </button>
                ) : (
                    <button
                        className="border-[1px] border-[#243c5a] rounded-lg p-[8px] hover:bg-blue-400 hover:text-[#ffffff]"
                        onClick={() => {
                          navigate("/sign_in");
                        }}
                    >
                      Sign In
                    </button>
                )}
              </div>
              <div className="flex items-center ml-[30px]">
                {localStorage.getItem('isLoggedIn')? (<button
                    onClick={() => {
                      //navigate("/my_page");
                      navigate("/admin_contest_report");
                    }}
                >
                  <MyPageIcon></MyPageIcon>
                </button>):(<button
                onClick={()=>{ window.alert("로그인 후 이용해주세요.")}}>
                  <MyPageIcon></MyPageIcon>
                </button>)}
              </div>
            </div>
          </div>
        </div>
      </header>
  );
};

export default Nav;
