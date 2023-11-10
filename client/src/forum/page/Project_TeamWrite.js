import React from "react";
import {useLocation} from "react-router-dom";

const Project_TeamWrite = () => {
    const {state} = useLocation();
    //const {number} = state;

    return (
        <div>
            글 상세 페이지<br/>
        </div>
    );
}

export default  Project_TeamWrite;