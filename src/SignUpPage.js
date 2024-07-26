import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { createUser } from './services/apiService';
import './SignUpPage.css';

const SignUp = () => {
  const [buttonClicked, setButtonClicked] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    verificationCode: '',
    verifyVerificationCode: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    dateOfBirth: '',
    address: '',
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
    console.log(`${event.target.name}: ${event.target.value}`); // Log input changes
    event.target.setCustomValidity('');
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    if (formData.verificationCode !== formData.verifyVerificationCode) {
      setError('Verification codes do not match');
      return;
    }

    const newUser = {
      username: formData.username,
      email: formData.email,
      password: formData.password,
      verificationCode: formData.verificationCode,
      firstName: formData.firstName,
      lastName: formData.lastName,
      phoneNumber: formData.phoneNumber,
      dateOfBirth: formData.dateOfBirth,
      address: formData.address,
      settings: {
        workHoursThreshold: 8,
        startTime: '09:00',
        endTime: '17:00',
        breakDuration: 15,
      },
    };

    console.log('New user data:', newUser); // Log user data before submission

    try {
      await createUser(newUser);
      navigate('/');
    } catch (error) {
      setError('An error occurred while creating the user');
    }
  };

  return (
    <>
      <div className="blue-rectangle2">      
        <h1 className='signup-header'>Create Account </h1>
        <h2 className='signup-subheader'>Sign Up to Get Started </h2>
      </div>
      <div className="white-rectangle2"></div>
      <div className="orange-rectangle2">
        <form onSubmit={handleSubmit}>
          {error && <p className="error">{error}</p>}
          <input
            type="text"
            name="username"
            placeholder="Username"
            className="user-input2"
            value={formData.username}
            onChange={handleInputChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            className="user-input2"
            value={formData.email}
            onChange={handleInputChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            className="user-input2"
            value={formData.password}
            onChange={handleInputChange}
            required
          />
          <input
            type="password"
            name="verificationCode"
            placeholder="Verification Code"
            className="user-input2"
            value={formData.verificationCode}
            onChange={handleInputChange}
            required
          />
          <input
            type="password"
            name="verifyVerificationCode"
            placeholder="Re-Enter Verification Code"
            className="user-input2"
            value={formData.verifyVerificationCode}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            className="user-input2"
            value={formData.firstName}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            className="user-input2"
            value={formData.lastName}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"  
            name="phoneNumber" 
            placeholder="Phone Number"
            className="user-input2"
            value={formData.phoneNumber}
            onChange={handleInputChange}
            required
          />
          <input
            type="date"
            name="dateOfBirth" 
            placeholder="Birth Date"
            className="user-input2"
            value={formData.dateOfBirth}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="address"
            placeholder="Address"
            className="user-input2"
            value={formData.address}
            onChange={handleInputChange}
            required
          />
          <div className="button-container2">
            <Link to="/">
              <button
                className={`button2 ${buttonClicked ? 'clicked' : ''}`}
                onMouseDown={handleMouseDown}
                onMouseUp={handleMouseUp}
                onMouseLeave={handleMouseUp}
              >
                Back
              </button>
            </Link>
            <button
              type="submit"
              className={`button2 ${buttonClicked ? 'clicked' : ''}`}
              onMouseDown={handleMouseDown}
              onMouseUp={handleMouseUp}
              onMouseLeave={handleMouseUp}
            >
              Submit
            </button>
          </div>
        </form>
      </div>
    </>
  );
};

export default SignUp;
