import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './SignUpPage.css';

const SignUp = () => {
    const [buttonClicked, setButtonClicked] = useState(false);

    const handleMouseDown = () => {
      setButtonClicked(true); // Set state to indicate button is being clicked
    };
  
    const handleMouseUp = () => {
      setButtonClicked(false); // Set state to indicate button click is released
    };

    const handleSubmit = (event) => {
      event.preventDefault(); // Prevent default form submission
      // Add logic to handle form submission, e.g., validate inputs, send data to server
      // For now, just log the submission
      console.log('Form submitted!');
    };

    const handleInputChange = (event) => {
      // Reset custom validation message when input changes
      event.target.setCustomValidity('');
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
            <input
              type="text"
              placeholder="First Name"
              className="user-input2"
              onChange={handleInputChange}
              required
            />
            <input
              type="text"
              placeholder="Last Name"
              className="user-input2"
              onChange={handleInputChange}
              required
            />
            <input
              type="email"
              placeholder="Student Email"
              className="user-input2"
              onChange={handleInputChange}
              required
            />
            <input
              type="password"
              placeholder="Password"
              className="user-input2"
              onChange={handleInputChange}
              required
            />
            <input
              type="password"
              placeholder="Verify Password"
              className="user-input2"
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
