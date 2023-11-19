//페이지 번호 렌더링
import styles from "../css/RenderPageNumber.module.css";

function renderPageNumber(totalWrite, pageSize) {
    const pageCount = Math.ceil(totalWrite / pageSize);
    const pageNumbers = [];
    pageNumbers.push(<button>{"<"}</button>)
    for (let i = 1; i <= pageCount; i++) {
        pageNumbers.push(
            <button key={i}>{i}</button>
        );
    }
    pageNumbers.push(<button>{">"}</button>)
    return (
        <div className={styles.pageNumber}>
            {pageNumbers}
        </div>);
}

export default renderPageNumber;