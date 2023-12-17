import { ChatIcon } from "./ChatIcon";
import { MyPageIcon } from "./MyPageIcon";
import { Link, useNavigate } from "react-router-dom";

const Nav = ({ isLoggedIn, setIsLoggedIn }) => {
  const rootURL = 'http://localhost:3000';
  const navigate = useNavigate();
  const handleLogout = () => {
    // 로그아웃 처리 후
    // ...

    setIsLoggedIn(false); // 로그아웃 상태로 변경
    localStorage.removeItem('token');
    navigate('/');
  };

  const screenWidth = window.screen.width;
  const screenHeight = window.screen.height;

  // 새 창의 크기를 화면 크기의 50%로 설정하고, 가운데 정렬
  const newWindowWidth = screenWidth * 0.5;
  const newWindowHeight = screenHeight * 0.5;
  const leftPos = (screenWidth - newWindowWidth) / 2;
  const topPos = (screenHeight - newWindowHeight) / 2;

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
                  <p className="border-[2px] border-[#000000] rounded-full p-[14px] text-[20px] text-blue-400 font-bold">
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
                <button
                    onClick={() =>
                      window.open(`http://localhost:3000/chat_list`, "ChatListPage", `width=${newWindowWidth}, height=${newWindowHeight}, top=${topPos}, left=${leftPos}`)
                    }
                >
                  <ChatIcon></ChatIcon>
                </button>
              </div>
              <div className="flex items-center ml-[30px]">
                {isLoggedIn ? (
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
                {isLoggedIn? (<button
                    onClick={() => {
                      navigate("/my_page");
                      //navigate("/admin_post_request");
                    }}
                >
                  <MyPageIcon></MyPageIcon>
                </button>):(<button

                >
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
