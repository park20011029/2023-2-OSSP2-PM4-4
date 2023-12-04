//팀원 모집글 본문 컴포넌트
import React from 'react';
import ReactQuill from 'react-quill';
import {useNavigate} from "react-router-dom";
import 'react-quill/dist/quill.snow.css';
import styles from '../css/Team_Write(Post).module.css';

const Write_Body = ({ editorContent, setEditorContent, onRewardChange, onSubmit }) => {
    const useReward = false;
    const navigate = useNavigate();

    const handleRewardChange = () => {
        onRewardChange(!useReward);
    };

    return (
        <div className={styles.body}>
            {/* 본문 입력 창 */}
            <ReactQuill
                className={styles.input}
                value={editorContent}
                onChange={(value) => setEditorContent(value)}
            />
            {/* 리워드 사용버튼 */}
            <div className={styles.reward}>
                <input type="checkbox" checked={useReward} onChange={handleRewardChange} />
                <div className={styles.description}>
                    <label>리워드 사용</label>
                    <label className={styles.des}>n일간 게시판 상단에 고정됩니다.</label>
                </div>
            </div>
            {/* 작성,취소 버튼 */}
            <div className={styles.submitCancel}>
                <button className={styles.submit}
                        onClick={() => onSubmit(editorContent, useReward)}>작성</button>
                <button onClick={() => {
                            if(window.confirm('취소하시겠습니까?')) {
                                alert("취소되었습니다.");
                                navigate('/contestInfoPostPage');
                            }
                }}>취소</button>
            </div>
        </div>
    );
};

export default Write_Body;
