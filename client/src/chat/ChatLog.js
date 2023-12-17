//채팅 로그 컴포넌트
import {useEffect, useState} from "react";
import styles from './ChatPage.module.css';

const ChatLog = ({ chatLog, setNewMessage, submit }) => {
    const [chatInput, setChatInput] = useState("");

    //스크롤 처리
    useEffect(() => {
        window.scrollTo(0, document.body.scrollHeight);
        console.log("chatLog:", chatLog);
    }, []);

    const parseDate = (timestamp) => {
        const dateObject = new Date(timestamp);
        // 각 부분 추출
        const year = dateObject.getFullYear();
        const month = String(dateObject.getMonth() + 1).padStart(2, '0');
        const day = String(dateObject.getDate()).padStart(2, '0');
        const hours = String(dateObject.getHours()).padStart(2, '0');
        const minutes = String(dateObject.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    const renderLog = () => {
        let today = '1000-01-01';
        const log = [];

        chatLog.map((element, index) => {
            try {
                console.log("element:", element);
                let temp = element.timestamp.split('T');
                const date = temp[0];
                temp = temp[1].split(':');
                const time = temp[0] + ':' + temp[1];
                //날짜 변경 처리
                if (date !== today) {
                    log.push(<div className={styles.chatDate}>{date}</div>);
                    today = date;
                }

                //채팅
                let chatClassName = element.isSentByMe ? styles.chatMyChat : styles.chatNotMyChat;
                log.push(<div className={chatClassName}>{element.text}</div>);

                //시간 처리
                if (
                    (chatLog[index + 1] === undefined) //마지막 채팅이거나
                    || (chatLog[index + 1].isSentByMe !== element.isSentByMe) //다음 채팅의 화자가 변경되거나
                    || (time !== chatLog[index + 1].time.split(" ")[1]) //다음 채팅의 시간이 변경되거나
                ) {
                    chatClassName = element.isSentByMe ? styles.chatMyTime : styles.chatNotMyTime;
                    log.push(<div className={chatClassName}>{time}</div>);
                }
            }catch(error) {
                console.log(error);
                console.log(element);
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
        submit();
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