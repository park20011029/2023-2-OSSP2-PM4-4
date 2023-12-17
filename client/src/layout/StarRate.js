
import { useState, useEffect } from "react";

// SVG 아이콘 컴포넌트
const StarIcon = ({ filled }) => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill={filled ? "#FFD700" : "#CCCCCC"} // 점수에 따라 색상 변경
    >
        {/* SVG 별 아이콘 내용 */}
        <path d="M12 2L9.38 8.72L2 9.24L7.54 14.14L6.65 21.02L12 17.77L17.35 21.02L16.46 14.14L22 9.24L14.62 8.72L12 2Z" />
    </svg>
);
const HalfOneIcon = ({ filled }) => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="12"
        height="24"
        viewBox="0 0 12 24" // 왼쪽 절반만 보이도록 설정
        fill={filled ? "#FFD700" : "#CCCCCC"} // 점수에 따라 색상 변경
    >
        {/* SVG 별 아이콘 내용 */}
        <path d="M12 2L9.38 8.72L2 9.24L7.54 14.14L6.65 21.02L12 17.77L17.35 21.02L16.46 14.14L22 9.24L14.62 8.72L12 2Z" />
    </svg>
);
const HalfTwoIcon = ({ filled }) => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="12"
        height="24"
        viewBox="12 0 12 24"
        fill={filled ? "#FFD700" : "#CCCCCC"} // 점수에 따라 색상 변경
    >
        {/* SVG 별 아이콘 내용 */}
        <path d="M12 2L9.38 8.72L2 9.24L7.54 14.14L6.65 21.02L12 17.77L17.35 21.02L16.46 14.14L22 9.24L14.62 8.72L12 2Z" />
    </svg>
);
function StarRate({ value }) {
    const [AVR_RATE, setAVR_RATE] = useState(0);
    const STAR_IDX_ARR = ['first', 'second', 'third', 'fourth', 'last'];
    const [ratesResArr, setRatesResArr] = useState([0, 0, 0, 0, 0]);

    const calcStarRates = () => {
        let tempStarRatesArr = [0, 0, 0, 0, 0];
        let starVerScore = (AVR_RATE * 70) / 100;
        let idx = 0;
        while (starVerScore > 14) {
            tempStarRatesArr[idx] = 14;
            idx += 1;
            starVerScore -= 14;
        }
        tempStarRatesArr[idx] = starVerScore;
        return tempStarRatesArr;
    };

    useEffect(() => {
        setAVR_RATE(value);
        setRatesResArr(calcStarRates());
    }, [AVR_RATE, value]);

    return (
        <div className="flex">
            {ratesResArr.map((rate, index) => (
                <span key={index} className={`star ${STAR_IDX_ARR[index]}`}>
                    {rate === 14 ? (
                        <div>
                            <StarIcon filled={true} />
                        </div>
                    ) : rate === 7 ? (
                        <div className="flex">
                            <HalfOneIcon filled={true} />
                            <HalfTwoIcon filled={false} />
                        </div>
                    ) : (
                        <div>
                            <StarIcon filled={false} />
                        </div>
                    )}
        </span>
            ))}
        </div>
    );
}

export default StarRate;