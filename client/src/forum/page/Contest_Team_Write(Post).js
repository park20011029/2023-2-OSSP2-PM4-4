//공모전 팀원 모집글(쓰기)
import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Write_Title from "../component/Write_Title";
import Write_Category from "../component/Write_Category";
import Write_Body from "../component/Write_Body";
import styles from "../css/Contest_Team_Write(Post).module.css";


//dummy data
const categoryList = {
    FE:"Front-end",
    BE:"Back-end",
    AI:"AI",
    EX:"기타"
}
const categoryDetail = {
    FE:["React", "Vue.js", "Angular"],
    BE:["Spring", "Django", "Ruby"],
    AI:["Tensorflow", "Keras", "PyTorch"],
    EX:["디자인", "기획"]
}
//dummy data end



const Contest_Team_WritePost = () => {
    const [editorContent, setEditorContent] = useState('');
    const [selected, setSelected] = useState({
        FE:{"React":0, "Vue.js":0, "Angular":0},
        BE:{"Spring":0, "Django":0, "Ruby":0},
        AI:{"Tensorflow":0, "Keras":0, "PyTorch":0},
        EX:{"디자인":0, "기획":0}
    });
    const [useReward, setUseReward] = useState(false);
    const navigate = useNavigate();

    const handleRewardChange = (value) => {
        setUseReward(value);
    };

    const handleSubmit = (content, reward) => {
        /* Todo: 본문내용, 리워드 처리 */
        console.log(content);

        navigate('/contestInfoPostPage');
    }

    return(
        <div>
            <Nav />
            <div className={styles.page}>
                <div className={styles.title}>
                    {/*게시글 작성*/}
                    <label>게시글 작성</label>
                    {/*제목 입력 창*/}
                    {Write_Title()}
                </div>
                <div className={"post_Category"}>
                    {/*모집 카테고리 선택*/}
                    <Write_Category
                        categoryList={categoryList}
                        categoryDetail={categoryDetail}
                        selected={selected}
                        setSelected={setSelected}
                    />
                    {/*모집 카테고리(추가버튼) ?? */}
                </div>
                <Write_Body
                    editorContent={editorContent}
                    setEditorContent={setEditorContent}
                    onRewardChange={handleRewardChange}
                    onSubmit={handleSubmit}
                />
            </div>
            <Footer />
        </div>
    )
}

export default Contest_Team_WritePost;