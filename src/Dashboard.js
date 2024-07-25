import React, { useState } from 'react';
import Modal from 'react-modal';
import { Link } from 'react-router-dom';
import './Dashboard.css';

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
            <div className="welcome-text">Welcome Back, User 1!</div>
          </div>
          <div className="progress-container">
            <div className="tasks">
              <div className='taskMark'></div>
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
            <div className="task"></div>
          </div>
        </div>
        <div className="calendar-container">
          <div className="calendar-header">June</div>
          <div className="calendar">
            {Array.from({ length: 30 }, (_, i) => (
              <div key={i} className="day" onClick={() => openModal(i + 1)}>
                {i + 1}
              </div>
            ))}
          </div>
        </div>
      </div>
    <Modal
      isOpen={modalIsOpen}
      onRequestClose={closeModal}
      contentLabel="Tasks Modal"
      style={{
        overlay: {
          backgroundColor: 'rgba(0, 0, 0, 0.75)',
          zIndex: 1000,
        },
        content: {
          zIndex: 1001,
          position: 'absolute',
          top: '50%',
          left: '50%',
          right: 'auto',
          bottom: 'auto',
          marginRight: '-50%',
          transform: 'translate(-50%, -50%)',
          width: '50%',
          padding: '20px',
          borderRadius: '10px',
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
        },
      }}
    >
      <h2>Tasks for Today</h2>
      <button onClick={closeModal}>Close</button>
      <ul>
        {tasks.map((task, index) => (
          <li key={index}>{task.description}</li>
        ))}
      </ul>
    </Modal>
  </div>
  );
};

Modal.setAppElement('#root');

export default Dashboard;
