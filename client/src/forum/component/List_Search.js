import React from 'react';
import searchIcon from '../assets/searchIcon.svg'
import styles from "../css/List.module.css";

const List_Search = ({setSearchWord, search}) => {
    const onSearchInput = e => {
        setSearchWord(e.target.value);
    }

    return (
        <div className={styles.smallPage}>
            <label className={styles.smallTitle}>검색</label>
            <div className={styles.SearchInput}>
                <input type={"text"} id={"searchInput"}
                       placeholder={"검색어를 입력하세요"}
                       onChange={onSearchInput}
                />
            </div>
        </div>
    );
}

export default List_Search;