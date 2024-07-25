import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './LoginPage.css';


  const Login = () => {
    const [buttonClicked, setButtonClicked] = useState(false);

    const handleMouseDown = () => {
      setButtonClicked(true); // Set state to indicate button is being clicked
    };
  
    const handleMouseUp = () => {
      setButtonClicked(false); // Set state to indicate button click is released
    };
    return(
      <>
      <div className="blue-rectangle"></div>
        <div className="orange-rectangle">
          <h1 className='welcome'>Welcome Back!</h1>
            <input type="text" placeholder="Username/Email" className="user-input" />
            <input type="password" placeholder="Password" className="user-input" />
            <div className="link-container">
              <a href="/reset-password" className="reset-link">Forgot Password? Click here to reset it.</a>
            </div>
            <div className="button-container">
            <Link to="/dashboard">
            <button
              className={`button ${buttonClicked ? 'clicked' : ''}`}
              onMouseDown={handleMouseDown}
              onMouseUp={handleMouseUp}
              onMouseLeave={handleMouseUp}
            >
              Dashboard
            </button>
            </Link>
          </div>
          <div className="signUp-container">
              <a href="/SignUpPage" className="signUp-link">Don’t Have an Account? Sign up Here!</a>
          </div>
        </div>
      <div className='grey-rectangle'></div>
      </>
    );
  };


export default Login; 