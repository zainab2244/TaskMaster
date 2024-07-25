import React from 'react';
import { Link } from 'react-router-dom';
import './Notifications.css';

const Notifications = () => {
    const handleSwitchChange = (e) => {
        const slider = e.target.nextElementSibling;
        slider.style.transform = e.target.checked ? 'translateX(65px)' : 'translateX(0)';
    };

    return (
        <div className="container">
            <div className="sidebar">
                <h2>
                    <img src="settings-icon.png" alt="Settings Icon" />
                    Settings
                </h2>
                <div className="separator"></div>
                <Link to="/ProfileSettings">
                    <button className="menu-button">Edit Profile</button>
                </Link>
                <Link to="/Notifications">
                    <button className="menu-button active">Notification</button>
                </Link>
                <Link to="/AppSettings">
                    <button className="menu-button">App Setting</button>
                </Link>
                <Link to="/Dashboard">
                    <button className="menu-button">Back to Main</button>
                </Link>
                <button className="menu-button logout-button">Log Out</button>
            </div>
            <div className="main">
                <div className="notification-settings">
                    <h3>Notification Settings</h3>
                    {['Notification', 'Email Notification', 'Text Notification'].map((setting) => (
                        <div className="row" key={setting}>
                            <label>{setting}</label>
                            <div>
                                <div className="switch-container">
                                    <span>Enable</span>
                                    <label className="switch">
                                        <input type="checkbox" onChange={handleSwitchChange} />
                                        <span className="slider"></span>
                                    </label>
                                    <span>Disable</span>
                                </div>
                            </div>
                        </div>
                    ))}
                    <div className="row">
                        <label>Time Setting</label>
                        <div>
                            <select>
                                <option>Hour</option>
                                {[...Array(12).keys()].map(i => (
                                    <option key={i + 1} value={i + 1}>{i + 1}</option>
                                ))}
                            </select>
                            :
                            <select>
                                <option>Minute</option>
                                {[...Array(60).keys()].map(i => (
                                    <option key={i} value={i < 10 ? `0${i}` : i}>
                                        {i < 10 ? `0${i}` : i}
                                    </option>
                                ))}
                            </select>
                            <div className="switch-container">
                                <span>AM</span>
                                <label className="switch">
                                    <input type="checkbox" id="ampmSwitch" onChange={handleSwitchChange} />
                                    <span className="slider"></span>
                                </label>
                                <span>PM</span>
                            </div>
                        </div>
                    </div>
                    <div className="add-alarm-line">Add Alarm</div>
                </div>
            </div>
        </div>
    );
};

export default Notifications;
