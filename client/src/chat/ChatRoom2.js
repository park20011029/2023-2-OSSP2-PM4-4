import React, { useState, useEffect } from "react";
import * as StompJS from "stompjs";
import * as SockJS from "sockjs-client";
import axios from "../api/index";
import styles from "./ChatPage.module.css";
import ChatLog from "./ChatLog";
import {useLocation, useNavigate, useParams} from "react-router-dom";


const ChatRoom2 = () => {
    const image = localStorage.getItem("EnemyImage") || "defaultProfile.svg";
    const targetName = localStorage.getItem("EnemyName")
    const navigate = useNavigate();
    const userId = 1 //Todo: userId
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");
    const [chatRoomId] = useState(useParams().chatRoomId);
    const [sender] = useState(userId);
    const [isConnected, setIsConnected] = useState(false);

    // stompClient를 useState로 관리
    const [stompClient, setStompClient] = useState(null);

    //웹소켓 연결
    const connectToWebSocket = () => {
        const onConnect = (frame) => {
            console.log("WebSocket connected:", frame);
            subscribeToRoom();
            setIsConnected(true);
        };

        const onError = (error) => {
            console.error("WebSocket connection error:", error);
            setStompClient(null);
        };

        if (stompClient) {
            console.log("Already connected to WebSocket");
            return;
        }

        const socket = new SockJS("http://15.164.3.171:8080/ws");
        const stomp = StompJS.over(socket);

        const connectOptions = {
            brokerURL: "ws://15.164.3.171:8080/ws",
            reconnectDelay: 5000,
            heartbeatIncoming: 5000,
            heartbeatOutgoing: 5000,
        };

        stomp.connect(connectOptions, onConnect, onError);
        setStompClient(stomp); // stompClient를 useState로 관리
    };

    //채팅방 연결
    const subscribeToRoom = () => {
        if (!stompClient) {
            console.error("Stomp client is not connected");
            return;
        }

        stompClient.subscribe(`/room/${chatRoomId}`, (message) => {
            try {
                const receivedMessage = JSON.parse(message.body);
                receivedMessage.isSentByMe = receivedMessage.sender === sender;
                setMessages((prevMessages) => [...prevMessages, receivedMessage]);
                console.log(message);
            } catch (error) {
                console.error("Failed to parse message body:", error);
            }
        });
    };

    //메세지 전송
    const sendMessage = () => {
        if (!newMessage || newMessage.trim() === "") {
            console.error("Message cannot be empty.");
            return;
        }

        if (!stompClient || stompClient.ws.readyState !== WebSocket.OPEN) {
            console.error(
                "WebSocket connection is not established. Trying to reconnect..."
            );
            return;
        }

        const chatMessage = {
            chatRoomId: parseInt(chatRoomId),
            sender: parseInt(sender),
            content: newMessage.trim(),
        };

        stompClient.send(`/send/${chatRoomId}`, {}, JSON.stringify(chatMessage));

        const sentMessage = {
            id: Date.now(),
            content: newMessage,
            isSentByMe: true,
            timestamp: new Date().toISOString(),
            sender: parseInt(sender),
        };

        setMessages((prevMessages) => [...prevMessages, sentMessage]);
        console.log(messages);
        setNewMessage("");
    };

    //채팅 기록 가져오기
    const fetchChatHistory = () => {
        axios
            .get(`/chatting/${chatRoomId}`)
            .then((response) => {
                if (response.data && response.data.responseDto) {
                    setMessages(response.data.responseDto.ChatLog);
                } else {
                    console.error("Invalid server response:", response);
                }
            })
            .catch((error) => {
                console.error("Failed to fetch chat history:", error);
            });
    };
    useEffect(() => {
        fetchChatHistory();
    }, [messages]);

    useEffect(()=> {
        connectToWebSocket();
    }, []);

    return (
        <div className={styles.page}>
            <div className={styles.goBack} onClick={() => navigate('/chat_list')}>
                <img src="/backIcon.svg" alt={'뒤로가기'}></img>
                <button>목록</button>
            </div>
            <div className={styles.targetInfo}>
                <img className={styles.targetImg} src={image || "/defaultProfile.svg"} alt={"프로필사진"}/>
                <label className={styles.targetName}>{targetName} 과의 대화</label>
            </div>
            {isConnected ?
                <ChatLog chatLog={messages}
                         setNewMessage={setNewMessage}
                         submit={sendMessage}
                /> : <></> }
        </div>
    );
};

export default ChatRoom2;