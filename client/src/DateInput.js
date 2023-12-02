import React, {useCallback, useEffect, useRef, useState} from 'react';
import { FcCalendar } from 'react-icons/fc';
import Datetime from 'react-datetime';
import './DateInput.css';
const DateInput = ({date,setDate}) => {
    const [open, setOpen] = useState(false);
    const inputRef = useRef(null);
    const calendarRef = useRef(null);
    const [error, setError ] = useState(false)
    const checkValidDate = (e) =>{
        if(!date){
            setError(true);
        }
        let daysInMonth=[31,28,31,30,31,30,31,31,30,31,30,31];
        const year = parseInt(date.substring(0,4),10);
        if(isLeapYear(year)){
            daysInMonth[1]=29;
        }
        const month = parseInt(date.substring(5,7),10);
        const day = parseInt(date.substring(8,10),10);
        const isValidDate = /^(19\d{2}|20\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/.test(date);
        if(date.length > 0 && !isValidDate) {
            setDate('');
            setError(true);
        }
        else if(date.length > 0 && isValidDate){
            if(day <= daysInMonth[month-1]){
                setDate(date);
                setError(false);
            }
            else{
                setDate('');
                setError(true);
            }
        }
    }
    function isLeapYear(year){
        return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
    }
    const handleDateChange = (e) => {
        let input = e.target.value.replace(/[^\d-]/g, ''); // 숫자와 '-'만 허용

        if (input.length <= 10) {
            // YYYY-MM-DD를 넘어가는 입력 불가
            const formattedDate = input
                .replace(/^(\d{4})(\d{1,2})/, '$1-$2') // YYYY(-MM)
                .replace(/^(\d{4}-\d{2})(\d{1,2})/, '$1-$2') // YYYY-MM(-DD)
                .slice(0, 10); // 길이 제한
            setDate(formattedDate);
        }
    };
    const handleClickButton = () => {
        setOpen(!open);
    };

    const handleChangeCalendar = (selected) => {
        const formattedDate = selected.format('YYYY-MM-DD');
        setDate(formattedDate);
        setOpen(false);
        setError(false);
    };
    const handleClickOut = useCallback(
        (e) => {
            if (open && inputRef?.current && calendarRef?.current) {
                const inputArea = inputRef.current;
                const calendarArea = calendarRef.current;
                const { target } = e;
                const outArea =
                    !inputArea.contains(target) && !calendarArea.contains(target);

                if (outArea) {
                    setOpen(false);
                }
            }
        },
        [open, inputRef, calendarRef]
    );
    useEffect(() => {
        if (open && inputRef?.current && calendarRef?.current) {
            document.addEventListener('click', handleClickOut);
        }
    }, [open, inputRef, calendarRef, handleClickOut]);
    return (
        <div className="dateInput">
            <div className="input-wrapper" ref={inputRef}>
                <input className="input"
                       type="text"
                       onChange={handleDateChange}
                       placeholder="YYYY-MM-DD"
                       maxLength="10"
                       onBlur={checkValidDate}
                       value = {date}
                       required
                />
                {error ? <span style={{ color: 'red',fontSize: '12px',position:'absolute',bottom:'1px',left:'22px'}}>유효하지 않은 날짜입니다.</span> : <></>}
                <button className="calendarButton" type='button' onClick={handleClickButton}>
                    <FcCalendar size={25}/>
                </button>
            </div>
            {open && (
                <div className='calendar' ref={calendarRef}>
                    <Datetime
                        input={false}
                        timeFormat={false}
                        value={date}
                        dateFormat={'YYYY-MM-DD'}
                        onChange={handleChangeCalendar}
                    />
                </div>
            )}
        </div>
    );
};

export default DateInput;
