// src/components/Dashboard.js
import React, { useState, useEffect } from 'react';
import Calendar from './Calendar';
import { getUserFirstNames } from './services/apiService';
import './Dashboard.css';

const Dashboard = () => {
    const [taskCount, setTaskCount] = useState(0);
    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchTaskCount = async () => {
            const response = await fetch('/api/tasks/count');
            const count = await response.json();
            setTaskCount(count);
        };

        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }

        fetchTaskCount();
    }, []);

    return (
        <div className="dashboard">
            <div className="sidebarD">
                <div className="avatar"></div>
                <div className="buttonCont">
                    <button className="buttonD1"></button>
                    <button className="buttonD1"></button>
                </div>
            </div>
            <div className="main-content">
                <div className="header-container">
                    <div className="header">
                        <div className="welcome-text">Welcome Back, {user ? user.firstName : 'User'}!</div>
                    </div>
                    <div className="progress-container">
                        <div className="tasks">
                            <div className="taskMark"></div>
                            <div className="task-count">{taskCount} Tasks</div>
                            <div className="progress-bar">
                                <div className="progress"></div>
                            </div>
                            <div className="completion-text">60% completed</div>
                        </div>
                    </div>
                </div>
                <div className="content">
                    <div className="task-list"></div>
                </div>
                <Calendar />
            </div>
        </div>
    );
};

export default Dashboard;
