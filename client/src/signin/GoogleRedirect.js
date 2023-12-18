// GoogleRedirect.js
import React, { useEffect } from 'react';
import { useLocation } from "react-router-dom";
import axios from 'axios';

function GoogleRedirect() {
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const code = queryParams.get('code');

        if (code) {
            sendCodeToServer(code);
        }
    }, [location]);

    const sendCodeToServer = async (code) => {
        try {
            console.log(code);
            const response = await axios.put(`/auth/signIn?authCode=${code}`, { code });
            // 서버 응답 처리
            console.log(response.data);
        } catch (error) {
            console.error('Error sending code to server:', error);
        }
    };

    return (
        <div>
            Processing Google login...
        </div>
    );
}

export default GoogleRedirect;