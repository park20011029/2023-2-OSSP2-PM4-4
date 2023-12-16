//채팅 상세 페이지
import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import styles from "./ChatPage.module.css";
import {useEffect, useState} from "react";
import ChatLog from "./ChatLog";
import axios from "axios";

const dummyData = {
    image:"defaultProfile.png",
    name:"상대방",
    log:[
        {
            time:"2023-12-09 10:42",
            isMe:true,
            text:"안녕하세요"
        },
        {
            time:"2023-12-09 10:42",
            isMe:true,
            text:"문의할게 있어서요"
        },
        {
            time:"2023-12-10 11:42",
            isMe:false,
            text:"안녕하세요"
        },
        {
            time:"2023-12-10 11:43",
            isMe:false,
            text:"어떤 문의사항이신가요?"
        },
    ]
}

const ChatPage = () => {
    let currentUrl = window.location.href;
    const targetId = new URLSearchParams(currentUrl.split('?')[1]);
    const userId = 1 //Todo: userId
    const [data, setData] = useState({});

    const getChatLog = async() => {
        try {
            const response = await axios.get(`/chat/log/${userId}/${targetId}`);
            const jsonData = response.data.responseDto;
            setData(jsonData);
        } catch(error) {
            //더미데이터 처리
            setData(dummyData);
            console.log(error);
        }
    }
    useState(() => {
        getChatLog();
    });


    const submit = async(input) => {
        try {
            const response = await axios.post(
                `/chat/post/${userId}/${targetId}`,
                { chat:input });
            if(response.status === 200) {
                //채팅 로그 갱신
                getChatLog();
            }
        } catch(error) {
            console.log("error!", error);
        }
    }

    return (
        <div>
            <Nav />
            <div className={styles.page}>
                <img className={styles.targetImg} src={data.image} alt={"프로필사진"}/>
                <label className={styles.targetName}>{data.name} 과의 대화</label>
                <div className={styles.chat}>
                    <ChatLog chatLog={dummyData.log}
                             submit={submit}
                    />
                </div>
            </div>
            <Footer />
        </div>
    )
}

export default ChatPage;