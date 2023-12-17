import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import UserSideBar from "./UserSideBar";
import { Cash } from "../Cash";
import axios from "axios";
import "./MyPageReward.css";
import {useEffect, useState} from "react";

function MyPageReward() {
    const [reward, setReward]= useState(0);
    const getReward = async () => {
        try {
            const response = await axios.get(
                `/user/${localStorage.getItem("userId")}`);
            setReward(response.data.responseDto.point);
        } catch (error) {
            console.log("Error fetching reward data: ", error);
        }
    };
    useEffect(() => {
        getReward();
    }, []);
    return (
        <div>
            <Nav/>
            <main className="flex-element">
                <UserSideBar />
                <div className="main-container">
                    <div className="reward">
                        <div className="container-top">
                            <h2>리워드</h2>
                        </div>
                        <Cash width={165} height={165}/>
                        <div className="myReward">보유 리워드 : {reward}p</div>
                    </div>
                </div>
            </main>
            <Footer />
        </div>
    );
}
export default MyPageReward;
