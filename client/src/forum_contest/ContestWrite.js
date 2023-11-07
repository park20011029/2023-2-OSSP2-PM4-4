import React from "react";
import {useParams} from "react-router-dom";

const ContestWrite = () => {
    const params = useParams();
    const writeNumber = params.number;

    return (
      <div>
          글 상세 페이지<br/>
          글번호 : {params.number}
      </div>  
    );
}

export default  ContestWrite;