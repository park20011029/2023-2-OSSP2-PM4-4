//공모전 팀원모집 게시글 리스트
import React from "react";
import { useNavigate } from 'react-router-dom';
import renderPageNumber from '../component/RenderPageNumber';
import "../css/Contest_Team_ListTab.module.css"
import ProjectList from "../component/projectList";

//dummy data
let write = {
    number: "123", //글번호
    title: "팀 구합니다",
    name : "김코딩",
    date : "2023.11.10"
};
const writeList = [write, write, write, write, write, write];
const totalWrite = 100;
const pageSize = 6;

//Todo: 페이지 정보 get
const getList = () => {

};

const Contest_Team_ListTab = () => {
    const navigate = useNavigate();

    // 상세페이지 이동
    function moveToWrite(index) {
        navigate(`/contestTeamWriteView`, { state: { number: index } });
    }

    return (
        <ProjectList />

    );
};

export default Contest_Team_ListTab;