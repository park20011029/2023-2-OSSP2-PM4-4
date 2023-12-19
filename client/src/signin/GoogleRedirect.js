// GoogleRedirect.js
import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import axios from 'axios';

const href = window.location.href;
let params = new URL(window.location.href).searchParams;
let code = params.get("code");
function GoogleRedirect() {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (code) {
            sendCodeToServer(code);
        }
    }, [location, navigate]);

    const sendCodeToServer = async (code) => {
        try {
            console.log(code);
            const response = await axios.put(`/auth/signIn?authCode=${code}`);

            if(response.data.responseDto.success === false){
                localStorage.setItem('email',response.data.responseDto.email);
                localStorage.setItem('socialId', response.data.responseDto.socialId);
                navigate(`/sign_up`);
            }
            else{
                localStorage.setItem('userId', response.data.responseDto.userId);
                navigate("/");
            }
            console.log(response.data);
        } catch (error) {
            navigate("/");
        }
    };

    return (
        <div className="mx-auto my-auto text-center">
            Processing Google login...
        </div>
    );
}

export default GoogleRedirect;