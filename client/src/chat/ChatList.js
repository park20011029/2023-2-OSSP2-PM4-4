//채팅 목록 페이지
import axios from "axios";
import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import styles from "./ChatList.module.css";
import ChatRoom from "./ChatRoom";


const ChatList = () => {
    const navigate = useNavigate();
    const userId = 1; //Todo: userId
    const [chatList, setChatList] = useState([]);

    //채팅 목록 데이터
    useEffect(() => {
        const getChatList = async() => {
            try {
                const response = await axios.get(`/chat/list/${userId}`);
                const jsonData = response.data.responseDto.ChatRoomList;
                setChatList(jsonData);
            } catch(error) {
                console.log(error);
            }
        }
        getChatList();
    },[]);

    return (
        <div className={styles.page}>
            <label className={styles.bigTitle}>채팅 목록</label>
            <div className={styles.chatList}>
                {chatList.map((chat) => (
                    <div className={styles.chatLine}
                         onClick={() => navigate(`/chatRoom/${chat.chatRoomId}`,
                             {state:{image:chat.image, targetName:chat.opponentNickname}})}>
                        <img src={"/defaultProfile.svg"} alt={"프로필사진"}/> {/*Todo: 이거하기*/}
                        <div className={styles.nameNchat}>
                            <label>{chat.opponentNickname}과의 대화</label>
                            <label>{chat.lastChat}</label>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )

}
export default ChatList;