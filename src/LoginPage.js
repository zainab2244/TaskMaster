import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from './services/apiService';
import './LoginPage.css';

const Login = () => {
  const [buttonClicked, setButtonClicked] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const success = await loginUser(username, password);
      if (success) {
        navigate('/dashboard');
      } else {
        setError('Invalid username or password');
      }
    } catch (err) {
      setError('An error occurred while logging in');
    }
  };

  const handleMouseDown = () => {
    setButtonClicked(true); // Set state to indicate button is being clicked
  };

  const handleMouseUp = () => {
    setButtonClicked(false); // Set state to indicate button click is released
  };

  return (
    <>
      <div className="blue-rectangle"></div>
      <div className="orange-rectangle">
        <h1 className='welcome'>Welcome Back!</h1>
        {error && <p className="error">{error}</p>}
        <form onSubmit={handleLogin}>
          <input
            type="text"
            placeholder="Username/Email"
            className="user-input"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            className="user-input"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <div className="link-container">
            <a href="/reset-password" className="reset-link">Forgot Password? Click here to use your verification .</a>
          </div>
          <div className="button-container">
            <button
              type="submit"
              className={`button ${buttonClicked ? 'clicked' : ''}`}
              onMouseDown={handleMouseDown}
              onMouseUp={handleMouseUp}
              onMouseLeave={handleMouseUp}
            >
              Login
            </button>
          </div>
        </form>
        <div className="signUp-container">
          <a href="/SignUpPage" className="signUp-link">Don’t Have an Account? Sign up Here!</a>
        </div>
      </div>
      <div className='grey-rectangle'></div>
    </>
  );
};

export default Login;
