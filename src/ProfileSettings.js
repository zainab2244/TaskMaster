import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getUserData, updateUserData } from './services/apiService';
import './ProfileSettings.css';

const ProfileSettings = () => {
    const [userData, setUserData] = useState({
        username: '',
        password: '',
        dob: { day: '', month: '', year: '' },
        school: '',
        email: '',
        phone: ''
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getUserData();
                if (data) {
                    const [year, month, day] = data.dateOfBirth.split('-');
                    setUserData({
                        username: data.username,
                        password: data.password,
                        dob: { day, month, year },
                        school: data.school || '',
                        email: data.email,
                        phone: data.phoneNumber
                    });
                }
            } catch (error) {
                console.error('Error fetching user data', error);
            }
        };

        fetchData();
    }, []);

    const handleChange = (e) => {
        const { id, value } = e.target;
        setUserData(prevState => ({
            ...prevState,
            [id]: value
        }));
    };

    const handleDateChange = (e) => {
        const { name, value } = e.target;
        setUserData(prevState => ({
            ...prevState,
            dob: {
                ...prevState.dob,
                [name]: value
            }
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateUserData(userData);
            alert('Profile updated successfully');
        } catch (error) {
            console.error('Error updating user data', error);
            alert('Failed to update profile');
        }
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
                    {userData.username}
                </h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username">User Name</label>
                        <input type="text" id="username" value={userData.username} onChange={handleChange} />
                    </div>
                    <div>
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" value={userData.password} onChange={handleChange} />
                    </div>
                    <div>
                        <label htmlFor="dob">Date of Birth</label>
                        <div style={{ display: 'flex' }}>
                            <select name="day" value={userData.dob.day} onChange={handleDateChange}>
                                <option>Day</option>
                                {[...Array(31).keys()].map(i => (
                                    <option key={i + 1} value={i + 1}>{i + 1}</option>
                                ))}
                            </select>
                            <select name="month" value={userData.dob.month} onChange={handleDateChange}>
                                <option>Month</option>
                                {["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"].map((month, index) => (
                                    <option key={index + 1} value={index + 1}>{month}</option>
                                ))}
                            </select>
                            <select name="year" value={userData.dob.year} onChange={handleDateChange}>
                                <option>Year</option>
                                {[...Array(31).keys()].map(i => (
                                    <option key={1990 + i} value={1990 + i}>{1990 + i}</option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <div>
                        <label htmlFor="school">School</label>
                        <input type="text" id="school" value={userData.school} onChange={handleChange} />
                    </div>
                    <div>
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" value={userData.email} onChange={handleChange} />
                    </div>
                    <div>
                        <label htmlFor="phone">Phone Number</label>
                        <input type="text" id="phone" value={userData.phone} onChange={handleChange} />
                    </div>
                    <button type="submit">Save Changes</button>
                    <button type="button">Two-factor Authentication</button>
                    <button type="button">Delete your account</button>
                </form>
            </div>
        </div>
    );
};

export default ProfileSettings;
