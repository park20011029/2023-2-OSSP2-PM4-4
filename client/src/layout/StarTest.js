import React, { useState } from 'react';
import './StarTest.css'; // 별점 스타일링을 위한 CSS 파일을 import 합니다.

const StarTest = ({rating, setRating}) => {
    const handleRatingChange = (value) => {
        setRating(value); // 선택된 별점을 변경합니다.
    };
    return(
        <div className="flex">
            <fieldset className="rate">
                <input type="radio" id="rating10" name="rating" value="10" onClick={()=> handleRatingChange(5)}/><label htmlFor="rating10" title="5점"></label>
                    <input type="radio" id="rating9" name="rating" value="9" onClick={()=> handleRatingChange(4.5)}/><label className="half" htmlFor="rating9" title="4.5점"></label>
                        <input type="radio" id="rating8" name="rating" value="8" onClick={()=> handleRatingChange(4)}/><label htmlFor="rating8" title="4점"></label>
                            <input type="radio" id="rating7" name="rating" value="7" onClick={()=> handleRatingChange(3.5)}/><label className="half" htmlFor="rating7" title="3.5점"></label>
                                <input type="radio" id="rating6" name="rating" value="6" onClick={()=> handleRatingChange(3)}/><label htmlFor="rating6" title="3점"></label>
                                    <input type="radio" id="rating5" name="rating" value="5" onClick={()=> handleRatingChange(2.5)}/><label className="half" htmlFor="rating5" title="2.5점"></label>
                                        <input type="radio" id="rating4" name="rating" value="4" onClick={()=> handleRatingChange(2)}/><label htmlFor="rating4" title="2점"></label>
                                            <input type="radio" id="rating3" name="rating" value="3" onClick={()=> handleRatingChange(1.5)}/><label className="half" htmlFor="rating3" title="1.5점"></label>
                                                <input type="radio" id="rating2" name="rating" value="2" onClick={()=> handleRatingChange(1)}/><label htmlFor="rating2" title="1점"></label>
                                                    <input type="radio" id="rating1" name="rating" value="1" onClick={()=> handleRatingChange(0.5)}/><label className="half" htmlFor="rating1" title="0.5점"></label>

            </fieldset>
            <p className="ml-[20px]">현재 별점: {rating} / 5</p>
        </div>
    )
};

export default StarTest;
