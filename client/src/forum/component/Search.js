import React, { Component } from 'react';
import searchIcon from './searchIcon.svg'
import "../css/Contest_ListPage.css";

class Search extends Component {

    searchByInput = () => {
        //Todo : 검색 기능 추가
    }

    render() {
        return (
            <div className="Search">
                <input type={"text"} id={"searchInput"} placeholder={"검색어를 입력하세요"}></input>
                <img src={searchIcon} onClick={this.searchByInput()} alt={"검색"}></img>
            </div>
        );
    }
}

export default Search;