import React from 'react';
import { Link } from 'react-router-dom';
import './ProfileSettings.css';

const ProfileSettings = () => {
    const formItems = [
        { label: 'User Name', type: 'text', id: 'username' },
        { label: 'Password', type: 'password', id: 'password' },
        { label: 'Date of Birth', type: 'select', id: 'dob' },
        { label: 'School', type: 'text', id: 'school' },
        { label: 'Email', type: 'email', id: 'email' },
        { label: 'Phone Number', type: 'text', id: 'phone' }
    ];

    return (
        <div className="container">
            <div className="sidebar">
                <h2>
                    <img src="settings-icon.png" alt="Settings Icon" />
                    Settings
                </h2>
                <div className="separator"></div>
                <Link to="/ProfileSettings">
                    <button className="menu-button active">Edit Profile</button>
                </Link>
                <Link to="/Notifications">
                    <button className="menu-button">Notification</button>
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
                <h2>
                    <img src="user.jpg" alt="User Picture" />
                    User Name
                </h2>
                <form>
                    {formItems.map((item) => (
                        <div key={item.id}>
                            <label htmlFor={item.id}>{item.label}</label>
                            {item.type === 'select' ? (
                                <div style={{ display: 'flex' }}>
                                    <select>
                                        <option>Day</option>
                                        {[...Array(31).keys()].map((i) => (
                                            <option key={i + 1} value={i + 1}>{i + 1}</option>
                                        ))}
                                    </select>
                                    <select>
                                        <option>Month</option>
                                        {["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"].map((month, index) => (
                                            <option key={index + 1} value={index + 1}>{month}</option>
                                        ))}
                                    </select>
                                    <select>
                                        <option>Year</option>
                                        {[...Array(31).keys()].map((i) => (
                                            <option key={1990 + i} value={1990 + i}>{1990 + i}</option>
                                        ))}
                                    </select>
                                </div>
                            ) : (
                                <input type={item.type} id={item.id} name={item.id} />
                            )}
                        </div>
                    ))}
                    <button type="button">Two-factor Authentication</button>
                    <button type="button">Delete your account</button>
                </form>
            </div>
        </div>
    );
};

export default ProfileSettings;
