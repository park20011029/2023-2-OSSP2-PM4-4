//페이지 번호 렌더링
import styles from "../forum/css/List_PageNumber.module.css";
import { useEffect, useState } from "react";

/*
 * 현재 페이지를 기준으로 페이지 번호 렌더링
 * ex) 현재 13페이지이고 한 화면에 10개의 페이지를 표시한다면 11 - 20까지 표시
 * left : 1, 11, 21...을 계산
 * right: 10, 20, 30...을 계산
 * <<클릭 시 이전 단위의 마지막 페이지로 이동
 * 이전 단위가 없다면 1페이지로 이동
 * ex) 13페이지에서 <<클릭 시 10페이지로 이동함
 * >>클릭 시 다음 단위의 첫 페이지로 이동
 * ex) 13페이지에서 >>클릭 시 21페이지로 이동함
 */
const List_PageNumber = ({pageInfo, setPageInfo}) => {
    const [pageNumbers, setPageNumbers] = useState([]);
    useEffect(() => {
        const calculatePageNumbers = () => {
            const newPageNumbers = [];
            const pageNumber = pageInfo.pageNumber;
            const pageLength = pageInfo.pageLength;
            const pageCount = pageInfo.pageCount;

            let left = 1;
            while (pageNumber - left >= pageLength) left += pageLength;

            let goLeft = left - 1;
            if (left === 1) goLeft = 1;

            let right = left + pageLength - 1;
            if (right > pageCount) right = pageCount;

            let goRight = right + 1;
            if (right === pageCount) goRight = pageCount;

            newPageNumbers.push(
                <button key={"goLeft"} onClick={() => setPageInfo({ ...pageInfo, pageNumber: goLeft })}>
                    {"<"}
                </button>
            );

            for (let i = left; i <= right; i++) {
                newPageNumbers.push(
                    <button key={i} onClick={() => setPageInfo({ ...pageInfo, pageNumber: i })}>
                        {i}
                    </button>
                );
            }

            newPageNumbers.push(
                <button key={"goRight"} onClick={() => setPageInfo({ ...pageInfo, pageNumber: goRight })}>
                    {">"}
                </button>
            );

            setPageNumbers(newPageNumbers);
        };

        calculatePageNumbers();
    }, [pageInfo]);

    return <div className={styles.pageNumber}>{pageNumbers}</div>;
}

export default List_PageNumber;