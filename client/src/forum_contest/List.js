import React from "react";
import { useNavigate } from 'react-router-dom';
//import ContestWrite from "./ContestWrite";

// dummy data
let write = {
    number: "123", //글번호
    title: "글 제목",
    image: "logo192.png",
};
const writeList = [write, write, write, write, write, write];
const totalWrite = 50;
const pageSize = 6;
// dummy data

//페이지 번호 렌더링
function renderPageNumber() {
    const pageCount = Math.ceil(totalWrite / pageSize);
    const pageNumbers = [];
    pageNumbers.push(<button>{"<"}</button>)
    for (let i = 1; i <= pageCount; i++) {
        pageNumbers.push(
            <button key={i}>{i}</button>
        );
    }
    pageNumbers.push(<button>{">"}</button>)
    return pageNumbers;
}


//화면크기가 줄어들면 사진의 크기를 줄여 대응
const List = () => {
    const navigate = useNavigate();
    //상세페이지 이동
    function moveToWrite(index) {
        navigate(`/ContestWrite/${index}`);
    }
    return (
        <div>
            <div className="writeList">
                {writeList.map((write, index) => (
                    <div key={write.number} className="write">
                        <img src={write.image} alt={write.title}
                            onClick={()=>moveToWrite(write.number)}/>
                        <label onClick={()=>moveToWrite(write.number)}>
                            {write.title}</label>
                    </div>
                ))}
            </div>
            <div className="writeButton">
                <button>글쓰기</button>
            </div>
            <div className="pageNumber">
                {renderPageNumber()}
            </div>
        </div>
    );
}


export default List;
