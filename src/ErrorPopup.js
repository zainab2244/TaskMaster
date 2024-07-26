import React from 'react';
import './ErrorPopup.css';

const ErrorPopup = ({ message, onClose }) => {
  if (!message) return null;

  return (
    <div className="error-popup">
      <div className="error-popup-content">
        <span className="error-popup-message">{message}</span>
        <button className="error-popup-close" onClick={onClose}>X</button>
      </div>
    </div>
  );
};

export default ErrorPopup;
