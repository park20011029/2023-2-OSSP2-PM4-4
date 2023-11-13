import React, { Component } from "react";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Search from "../component/Search";
import List_Category from "../component/List_Category";
import ProjectList from "../component/projectList";

const criteria = { FE: "Front-end", BE: "Back-end", AI: "AI", extra: "기타" };
const detailList = {
    FE: ["React", "Vue.js", "Angular"],
    BE: ["Spring", "Django", "Ruby"],
    AI: ["Tensorflow", "Keras", "PyTorch"],
    extra: ["디자인", "기획"],
};
const selected = { FE: [], BE: [], AI: [], extra: [] };

class Project_ForumPage extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div>
                <Nav />
                <div className='ProjectPage'>
                    <Search />
                    <List_Category criteria={criteria} detailList={detailList} selected={selected} />
                    <ProjectList />
                </div>
                <Footer />
            </div>
        );
    }
}

export default Project_ForumPage;
