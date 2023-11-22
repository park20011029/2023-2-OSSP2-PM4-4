import React, {useState} from "react";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import Search from "../component/Search";
import List_Category from "../component/List_Category";
import ProjectList from "../component/projectList";
import styles from "../css/ListPage.module.css";

const criteria = { FE: "Front-end", BE: "Back-end", AI: "AI", extra: "기타" };
const detailList = {
    FE: ["React", "Vue.js", "Angular"],
    BE: ["Spring", "Django", "Ruby"],
    AI: ["Tensorflow", "Keras", "PyTorch"],
    extra: ["디자인", "기획"],
};

const getCategory = () => {
    //Todo: 카테고리 리스트를 가져옴
    return [criteria, detailList];
}
const Project_ListPage = () => {
    const [criteria, detailList] = getCategory();
    const [selected, setSelected] = useState({ FE: [], BE: [], AI: [], extra: [] });

    function updateSelected(newSelected) {
        setSelected(newSelected);
    }

    return (
        <div>
            <Nav />
            <div className={styles.Page}>
                <Search />
                <List_Category
                    criteria={criteria}
                    detailList={detailList}
                    selected={selected}
                    updateSelected={updateSelected}
                />
                <ProjectList />
            </div>
            <Footer />
        </div>
    );
}

export default Project_ListPage;
