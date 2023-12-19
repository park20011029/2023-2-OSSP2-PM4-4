// SignInPage.js
import React from 'react';
import { useNavigate } from "react-router-dom";
import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import "./SignInPage.css";
import {Google} from "../Google";

function SignInPage() {
    const navigate = useNavigate();
    const loginUrl = `http://15.164.3.171:8080/oauth2/authorization/google`;

    const handleSignIn = () => {
        window.location.href = loginUrl;
    };

    return (
        <div>
            <Nav />
            <div id="SignInBody">
                {/*<button id='SignInButton' onClick={handleSignIn}>Sign in with Google</button>*/}
                <div onClick={handleSignIn}><Google width={300} height={67}/></div>
                <div id='SignUp'>
                    <p>계정이 없다면 ? </p>
                    <button id='SignUpButton' onClick={() => navigate("/sign_up")}>
                        Sign Up
                    </button>
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default SignInPage;