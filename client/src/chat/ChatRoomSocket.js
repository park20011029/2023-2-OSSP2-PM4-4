import React, { useState, useEffect } from "react";
import * as StompJS from "stompjs";
import * as SockJS from "sockjs-client";
import axios from "../api/index";

const ChatRoomSocket = () => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");
    const [chatRoomId] = useState("1");
    const [sender] = useState("9");

    // stompClient를 useState로 관리
    const [stompClient, setStompClient] = useState(null);
    // 고유한 키를 생성하는 함수
    const generateUniqueKey = (message) => `${message.id}_${message.timestamp}`;

    const connectToWebSocket = () => {
        // If Socket is connected, we shouldn't connect socket again.
        if (stompClient) {
            console.log("Already connected to WebSocket");
            return;
        }

        const socket = new SockJS("http://localhost:8080/ws");
        const stomp = StompJS.over(socket);

        const connectOptions = {
            brokerURL: "ws://localhost:8080/ws",
            reconnectDelay: 500,
            heartbeatIncoming: 500,
            heartbeatOutgoing: 500,
        };

        stomp.connect(connectOptions, onConnect, onError);
        setStompClient(stomp); // stompClient를 useState로 관리
    };

    const onConnect = (frame) => {
        console.log("WebSocket connected:", frame);
        subscribeToRoom();
    };

    const onError = (error) => {
        console.error("WebSocket connection error:", error);
        setStompClient(null); // 연결 실패 시 stompClient를 null로 초기화
    };

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
            sender: parseInt(chatRoomId),
            content: newMessage.trim(),
        };

        stompClient.send(`/send/${chatRoomId}`, {}, JSON.stringify(chatMessage));

        const sentMessage = {
            id: Date.now(),
            text: newMessage,
            isSentByMe: true,
            timestamp: new Date().toISOString(),
            sender: parseInt(sender),
        };

        setMessages((prevMessages) => [...prevMessages, sentMessage]);
        console.log(messages);
        setNewMessage("");
    };

    const fetchChatHistory = () => {
        axios
            .get(`/chatting/${chatRoomId}`)
            .then((response) => {
                if (response.data && response.data.responseDto) {
                    setMessages(response.data.responseDto);
                } else {
                    console.error("Invalid server response:", response);
                }
            })//
            .catch((error) => {
                console.error("Failed to fetch chat history:", error);
            });//asdfsdaf
    };
    useEffect(() => {
        fetchChatHistory();
    }, [messages]);

    useEffect(()=> {
        connectToWebSocket();
    }, []);

    return (
        <div>
            <div>
                {messages.map((message) => (
                    <div key={generateUniqueKey(message)}>{message.content}</div>
                ))}
                <div>
          <textarea
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
              onKeyUp={(e) => e.key === "Enter" && sendMessage()}
              placeholder="메시지를 입력하세요"
          ></textarea>
                    <button onClick={sendMessage}>Send</button>
                </div>
            </div>
        </div>
    );
};

export default ChatRoomSocket;
