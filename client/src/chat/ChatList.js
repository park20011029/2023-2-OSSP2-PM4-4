//채팅 목록 페이지
import axios from "axios";
import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import styles from "./ChatList.module.css";
import ChatRoom from "./ChatRoom";

const dummy = [
    {
        image: "defaultProfile.svg",
        opponentNickname: "이름1",
        chatRoomId:1,
        lastChat: "안녕하세요"
    },
    {
        image:"defaultProfile.svg",
        opponentNickname:"이름2",
        chatRoomId:2,
        lastChat:"반갑습니다"
    }
]

const ChatList = () => {
    const navigate = useNavigate();
    const userId = 1; //Todo: userId
    const [chatList, setChatList] = useState();

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

    const moveToChat = (image, name) => {
        return <ChatRoom image={image}
                          targetName={name} />
    }
    return (
        <div className={styles.page}>
            <label className={styles.bigTitle}>채팅 목록</label>
            <div className={styles.chatList}>
                {chatList.map((chat) => (
                    <div className={styles.chatLine}
                         onClick={() => navigate(`/chatRoom/${chat.chatRoomId}`,
                             {state:{image:chat.image, targetName:chat.opponentNickname}})}>
                        <img src={chat.image} alt={"프로필사진"}/>
                        <div className={styles.nameNchat}>
                            <label>{chat.opponentNickname}</label>
                            <label>{chat.lastChat}</label>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )

}
export default ChatList;