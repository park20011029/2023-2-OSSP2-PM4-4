//채팅 로그 컴포넌트
import {useEffect, useState} from "react";
import styles from './ChatPage.module.css';

const ChatLog = ({ chatLog, setNewMessage, submit }) => {
    const [chatInput, setChatInput] = useState("");
    const userId = 1; //Todo: userId

    //스크롤 처리
    useEffect(() => {
        window.scrollTo(0, document.body.scrollHeight);
        console.log("chatLog:", chatLog);
    }, []);

    //날짜 처리
    const parseDate = (time) => {
        return time.split('T')[0];
    }
    const parseTime = (time) => {
        let temp = time.split('T');
        temp = temp[1].split(':');
        return temp[0] + ':' + temp[1];
    }
    const renderLog = () => {
        let today = '1000-01-01';
        const log = [];

        chatLog.map((element, index) => {
            try {
                console.log("element:", element);
                const date = parseDate(element.sendDate);
                const time = parseTime(element.sendDate);
                //날짜 변경 처리
                if (date !== today) {
                    log.push(<div className={styles.chatDate}>{date}</div>);
                    today = date;
                }

                //채팅
                let chatClassName = element.sender=== userId ? styles.chatMyChat : styles.chatNotMyChat;
                const chatPosition = element.sender=== userId ? styles.goRight : styles.goLeft;
                log.push(<div className={chatPosition}>
                    <label className={chatClassName}>{element.content}</label>
                </div>);

                //시간 처리
                if (
                    (chatLog[index + 1] === undefined) //마지막 채팅이거나
                    || (chatLog[index + 1].sender !== element.sender) //다음 채팅의 화자가 변경되거나
                    || (time !== parseTime(chatLog[index + 1].sendDate)) //다음 채팅의 시간이 변경되거나
                ) {
                    chatClassName = (element.sender === userId) ? styles.chatMyTime : styles.chatNotMyTime;
                    log.push(<div className={chatPosition}>
                        <label className={chatClassName}>{time}</label>
                    </div>);
                }
            } catch(error) {
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
                       placeholder={"채팅 입력"}/>
                <img src="/chatSubmit.svg" alt={"전송"} onClick={onSubmit}/>
            </div>
        </div>
    );
}

export default ChatLog;