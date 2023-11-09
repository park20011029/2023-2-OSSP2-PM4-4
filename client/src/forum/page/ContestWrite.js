import React from "react";
import {useLocation} from "react-router-dom";

const write = {

}
const ContestWrite = () => {
    const {state} = useLocation();
    const {number} = state;

    return (
      <div>
          글 상세 페이지<br/>
          글번호 : {number}
      </div>  
    );
}

export default  ContestWrite;