import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { verifyUserForReset } from './services/apiService'; // Assuming you have this function in apiService
import './ResetPassword.css';

const ResetPassword = () => {
  const [buttonClicked, setButtonClicked] = useState(false);
  const [formData, setFormData] = useState({
    email: '',
    verificationCode: '',
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleMouseDown = () => {
    setButtonClicked(true);
  };

  const handleMouseUp = () => {
    setButtonClicked(false);
  };

  const handleInputChange = (event) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
    event.target.setCustomValidity('');
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const success = await verifyUserForReset(formData.email, formData.verificationCode);
      if (success) {
        navigate('/dashboard'); // Redirect to the dashboard on successful verification
      } else {
        setError('Invalid email or verification code');
      }
    } catch (error) {
      setError('An error occurred during verification');
    }
  };

  return (
    <>
      <div className="blue-rectangle2">      
        <h1 className='signup-header'>Reset Password</h1>
        <h2 className='signup-subheader'>Verify Your Identity</h2>
      </div>
      <div className="white-rectangle2"></div>
      <div className="orange-rectangle2">
        <div className="forgot-password-container">
          <h2>Forgot Password</h2>
          <form className="forgot-password-form" onSubmit={handleSubmit}>
            {error && <p className="error">{error}</p>}
            <input
              type="email"
              className="forgot-password-input"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
            <input
              type="text"
              className="forgot-password-input"
              name="verificationCode"
              placeholder="Enter verification code"
              value={formData.verificationCode}
              onChange={handleInputChange}
              required
            />
            <button
              type="submit"
              className={`forgot-password-button ${buttonClicked ? 'clicked' : ''}`}
              onMouseDown={handleMouseDown}
              onMouseUp={handleMouseUp}
              onMouseLeave={handleMouseUp}
            >
              Submit
            </button>
          </form>
          <a className="forgot-password-link" href="/login">Back to Login</a>
        </div>
      </div>
    </>
  );
};

export default ResetPassword;
