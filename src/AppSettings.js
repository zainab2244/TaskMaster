import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import './AppSettings.css';

const UserSettings = () => {

    useEffect(() => {
        populateTimeSelects();
    }, []);

    const populateTimeSelects = () => {
        const hourSelects = document.querySelectorAll('.hour-select');
        const minuteSelects = document.querySelectorAll('.minute-select');

        hourSelects.forEach(select => {
            for (let i = 0; i <= 12; i++) {
                const option = document.createElement('option');
                option.value = i < 10 ? '0' + i : i;
                option.textContent = i < 10 ? '0' + i : i;
                select.appendChild(option);
            }
        });

        minuteSelects.forEach(select => {
            for (let i = 0; i < 60; i++) {
                const option = document.createElement('option');
                option.value = i < 10 ? '0' + i : i;
                option.textContent = i < 10 ? '0' + i : i;
                select.appendChild(option);
            }
        });
    };

    const handleAmPmClick = (e) => {
        const button = e.target;
        const isStartTime = button.classList.contains('start-time');
        const siblings = button.parentElement.querySelectorAll(isStartTime ? '.start-time' : '.end-time');
        siblings.forEach(sibling => sibling.classList.remove('active'));
        button.classList.add('active');
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
                    <button className="menu-button">Notification</button>
                </Link>
                <Link to="/AppSettings">
                    <button className="menu-button active">App Setting</button>
                </Link>
                <Link to="/Dashboard">
                    <button className="menu-button">Back to Main</button>
                </Link>
                <button className="menu-button logout-button">Log Out</button>
            </div>
            <div className="main">
                <div className="settings-group">
                    <h4>Theme</h4>
                    <div className="row switch-container">
                        <span>Light</span>
                        <label className="switch">
                            <input type="checkbox" />
                            <span className="slider"></span>
                        </label>
                        <span>Dark</span>
                    </div>
                </div>
                <div className="settings-group">
                    <h4>View Default</h4>
                    <div className="row switch-container">
                        <span>Calendar</span>
                        <label className="switch">
                            <input type="checkbox" />
                            <span className="slider"></span>
                        </label>
                        <span>Agenda</span>
                    </div>
                </div>
                <div className="settings-group">
                    <h4>Hours Settings</h4>
                    {['Work Hours'].map(day => (
                        <div className="row" key={day}>
                            <label>{day}</label>
                            <div className="time-setting">
                                <select className="hour-select"></select>
                                :
                                <select className="minute-select"></select>
                                <button className="am-pm-button start-time active" onClick={handleAmPmClick}>AM</button>
                                <button className="am-pm-button start-time" onClick={handleAmPmClick}>PM</button>
                                <span className="vertical-line"></span>
                                <select className="hour-select"></select>
                                :
                                <select className="minute-select"></select>
                                <button className="am-pm-button end-time" onClick={handleAmPmClick}>AM</button>
                                <button className="am-pm-button end-time active" onClick={handleAmPmClick}>PM</button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default UserSettings;
