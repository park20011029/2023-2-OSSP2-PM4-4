import React from "react";
import { useNavigate } from 'react-router-dom';
import renderPageNumber from '../page/List';

// dummy data
let write = {
    number: "123", //글번호
    title: "프로젝트 글 제목"
};
const writeList = [write, write, write, write, write, write];
const totalWrite = 100;
const pageSize = 6;
// dummy data


//화면크기가 줄어들면 사진의 크기를 줄여 대응
const ProjectList = () => {
    const navigate = useNavigate();
    //상세페이지 이동
    function moveToWrite(index) {
        navigate(`/ProjectWrite`, {state:{number:index}});
    }
    return (
        <div>
            <div className="writeList">
                {writeList.map((write, index) => (
                    <div key={write.number} className="write">
                        <label onClick={()=>moveToWrite(write.number)}>
                            {write.title}</label>
                    </div>
                ))}
            </div>
            <div className="writeButton">
                <button>글쓰기</button>
            </div>
            <div className="pageNumber">
                {renderPageNumber(totalWrite, pageSize)}
            </div>
        </div>
    );
}


export default ProjectList;
