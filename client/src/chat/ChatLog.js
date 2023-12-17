//채팅 로그 컴포넌트
import {useEffect, useState} from "react";
import styles from './ChatPage.module.css';

const ChatLog = ({ chatLog, setNewMessage, submit }) => {
    const [chatInput, setChatInput] = useState("");

    //스크롤 처리
    useEffect(() => {
        window.scrollTo(0, document.body.scrollHeight);
    }, []);

    const renderLog = () => {
        let today = '1000-01-01';
        let time = chatLog[0].time.split(" ")[1];

        const log = [];

        chatLog.map((element, index) => {
            const temp = element.time.split(" ");
            const date = temp[0];
            const time = temp[1];
            //날짜 변경 처리
            if(date !== today) {
                log.push(<div className={styles.chatDate}>{date}</div>);
                today = date;
            }

            //채팅
            let chatClassName = element.isMe ? styles.chatMyChat : styles.chatNotMyChat;
            log.push(<div className={chatClassName}>{element.text}</div>);

            //시간 처리
            if(
                (chatLog[index+1] === undefined) //마지막 채팅이거나
                || (chatLog[index+1].isMe !== element.isMe) //다음 채팅의 화자가 변경되거나
                || (time !== chatLog[index+1].time.split(" ")[1]) //다음 채팅의 시간이 변경되거나
            )
            {
                chatClassName = element.isMe ? styles.chatMyTime : styles.chatNotMyTime;
                log.push(<div className={chatClassName}>{time}</div>);
            }
        });

        return log;
    }

    const handleEnter = (e) => {
        if(e.key === 'Enter') {
            onSubmit();
        }
    }
    const onSubmit = () => {
        submit(chatInput);
        setChatInput("");
        setNewMessage("");
    }

    return(
        <div className={styles.chatLog}>
            {renderLog()}
            <div className={styles.chatInput}>
                <input type={"text"} value={chatInput}
                       onChange={(e) => {
                           setChatInput(e.target.value);
                           setNewMessage(e.target.value);
                       }}
                       onKeyPress={handleEnter}
                       placeholder={"채팅을 입력하세요"}/>
                <button onClick={onSubmit}>전송</button>
            </div>
        </div>
    );
}

export default ChatLog;