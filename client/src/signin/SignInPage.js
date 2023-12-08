import { Link, useNavigate } from "react-router-dom";
import Nav from "../layout/Nav";
import Footer from "../layout/Footer";
import "./SignInPage.css";

function SignInPage() {
    const navigate = useNavigate();

    const handleSignIn = () => {
        // 로그인 로직 처리 후
        // ...
        // 로그인 성공 시
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userId", "48");
        navigate("/"); // 메인 페이지로 이동
    };
    return (
        <div>
            <Nav />
            <div id="SignInBody">
                <button id='SignInButton' onClick={handleSignIn}>Sign In</button>
                <div id='SignUp'>
                    <p>계정이 없다면 ? </p>
                    <button id='SignUpButton'
                        onClick={() => {
                            navigate("/sign_up");
                        }}
                    >
                        Sign Up
                    </button>
                </div>
            </div>

            <Footer />
        </div>
    );
}
export default SignInPage;
