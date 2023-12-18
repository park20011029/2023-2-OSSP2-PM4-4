//날짜 선택 컴포넌트
import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styles from "../css/Team_Write(Post).module.css";
import { contest_CategoryList, contest_CategoryKOR } from "./axios_category";

const formatDate = (date) => {
    // 원하는 날짜 포맷에 맞게 조정
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
};

const Write_Calendar = ({keyName, setData}) => {
    const [selectedDate, setSelectedDate] = useState(null);
    const index = contest_CategoryList.indexOf(keyName);

    return (
        <tr className={styles.datePick}>
            <td className={styles.dropdownTitle}>{contest_CategoryKOR[index]} : </td>
            <td>
                <DatePicker
                    selected={selectedDate}
                    onChange={(date) => {
                        const newDate = formatDate(date);
                        setSelectedDate(date);
                        setData(keyName, newDate);
                    }}
                    dateFormat="yyyy-MM-dd"
                    placeholderText="날짜를 선택하세요"
                />
            </td>
        </tr>
    );
};

export default Write_Calendar;
