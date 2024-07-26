import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';
import { Link } from 'react-router-dom';
import { getUserFirstNames } from './services/apiService';
import './Dashboard.css';
import Calendar from './Calendar';

const fetchTasks = async (day) => {
  const response = await fetch(`/tasks/2024-06-${String(day).padStart(2, '0')}`);
  if (response.ok) {
    return response.json();
  }
  return [];
};

const Dashboard = () => {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [tasks, setTasks] = useState([]);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const openModal = async (day) => {
    const tasks = await fetchTasks(day);
    setTasks(tasks);
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
    setTasks([]);
  };

  return (
    <div className="dashboard">
      <div className="sidebarD">
        <div className="avatar"></div>
        <div className='buttonCont'>
          <Link to="">
            <button className="buttonD1"></button>
          </Link>
          <Link to="/ProfileSettings">
            <button className="buttonD1"></button>
          </Link>
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
              <div className="task-count">20 Tasks</div>
              <div className="progress-bar">
                <div className="progress"></div>
              </div>
              <div className="completion-text">60% completed</div>
            </div>
          </div>
        </div>
        <div className="content">
          <div className="task-list">
          </div>
        </div>
        <Calendar />
      </div>
    </div>
  );
};

Modal.setAppElement('#root');

export default Dashboard;
