//채팅 목록 페이지
import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import {useState} from "react";
import styles from "./ChatListPage.module.css";
import {useNavigate} from "react-router-dom";

const dummy = [
    {
        image: "defaultProfile.png",
        name: "이름1",
        targetId: 1,
        lastChat: "안녕하세요"
    },
    {
        image:"defaultProfile.png",
        name:"이름2",
        targetId: 2,
        lastChat:"반갑습니다"
    }
]

const ChatListPage = () => {
    const navigate = useNavigate();
    const [chatList, setChatList] = useState(dummy);
    //기본 데이터 가져옴
    useState(() => {
        const getChatList = async() => {
            try {
                let response;
                //Todo: 데이터 요청
                const temp = 1/0;
            } catch(error) {
                console.log(error);
            }
        }
        getChatList();
    },[]);

    return (
        <div>
            <Nav />
            <div className={styles.page}>
                <label className={styles.bigTitle}>채팅 목록</label>
                <div className={styles.chatList}>
                    {chatList.map((chat) => (
                        <div className={styles.chatLine}
                             onClick={() => navigate(`/chat/${chat.targetId}`)}>
                            <img src={chat.image} alt={"프로필사진"}/>
                            <div className={styles.nameNchat}>
                                <label>{chat.name}</label>
                                <label>{chat.lastChat}</label>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <Footer />
        </div>
    )

}
export default ChatListPage;