import React, { Component } from 'react';
import "./ContestForumPage.css";

class Category extends Component {
    constructor(props) {
        super(props);
        this.state = {
            criteria: { title: "응모 대상", host: "주최 기관", scale: "시상 규모" },
            title: ["청소년", "대학생", "직장인", "제한없음"],
            host: ["대기업", "공공기업", "외국계기업"],
            scale: ["1천 미만", "1천 ~ 3천", "3천 이상"],
            //전송할 객체
            selected: { title: [], host: [], scale: [] }
        };
    }

    checkBoxValue = (category, index) => (e) => {
        const selectedItems = { ...this.state.selected };
        const value = this.state[category][index];
        const isChecked = e.target.checked;
        //console.log('Category: ', category, 'Value: ', value, 'isChecked: ', isChecked);
        if (isChecked) {
            selectedItems[category].push(value);
        } else {
            selectedItems[category].splice(index, 1);
        }
        this.setState({ selected: selectedItems });
        //console.log(selectedItems)
    };

    renderCheckBox = (category) => {
        const selectedItems = this.state.selected[category];
        const checkList = this.state[category];

        return Object.keys(checkList).map((key, index) => {
            const isChecked = selectedItems.includes(checkList[index]);
            return (
                <label key={key}>
                    <input
                        type="checkbox"
                        value={checkList[index]}
                        checked={isChecked}
                        onChange={this.checkBoxValue(category, index)}
                    />
                    {checkList[index]}
                </label>
            );
        });
    };

    //체크박스 카테고리 초기화
    componentDidMount() {
        this.resetCheck();
    }

    resetCheck = () => {
        this.setState({
            selected:{title:[], host:[], scale:[]}
        });
    }

    //검색
    search = () => {
        //Todo : 검색 기능
    }
    render() {
        return (
            <div className="Category">
                <label className="title">카테고리</label>
                <div className="criteria">
                    {Object.keys(this.state.criteria).map((category) => (
                        <div key={category} className="checkbox">
                            <label>{this.state.criteria[category]}</label>
                            {this.renderCheckBox(category)}
                        </div>
                    ))}
                    <div className={"buttons"}>
                        <button onClick={this.resetCheck}>초기화</button>
                        <button onClick={this.search}>검색</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Category;
