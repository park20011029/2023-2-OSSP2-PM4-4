import { ChatIcon } from "./ChatIcon";
import { MyPageIcon } from "./MyPageIcon";
import { Link, useNavigate } from "react-router-dom";

const Nav = () => {
  const navigate = useNavigate();
  return (
    <header>
      <div className="h-[90px] p-[15px] border-b-[#000000] shadow-md">
        <div className="flex items-center justify-between">
          <div className="flex items-center h-[60px]">
            <div className="mr-[20px]">
              <button
                onClick={() => {
                  navigate("/");
                }}
              >
                <p className="border-[2px] border-[#000000] rounded-full p-[14px] text-[20px] text-blue-400 font-bold">
                  PM4
                </p>
              </button>
            </div>
            <div className="flex items-center h-[90px] px-[60px]">
              <button
                className="after:absolute after:content-[''] after:w-0 after:b-0 after:l-1/2 after:h-[3px] after:-translate-x-2/4 after:bg-blue-400 after:transition-all after:transition .5s ease-out hover:after:w-[100px]"
                onClick={() => {
                  navigate("/contest_forum");
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
              <button
                onClick={() => {
                  navigate("/chat_divst");
                }}
              >
                <ChatIcon></ChatIcon>
              </button>
            </div>
            <div className="flex items-center ml-[30px]">
              <button
                className="border-[1px] border-[#243c5a] rounded-lg p-[8px] hover:bg-blue-400 hover:text-[#ffffff]"
                onClick={() => {
                  navigate("/login");
                }}
              >
                <p>Sign in</p>
              </button>
            </div>
            <div className="flex items-center ml-[30px]">
              <button
                onClick={() => {
                  navigate("/my_page");
                }}
              >
                <MyPageIcon></MyPageIcon>
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Nav;
