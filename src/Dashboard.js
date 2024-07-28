import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Calendar from './Calendar';
import './Dashboard.css';

const Dashboard = () => {
  const [tasks, setTasks] = useState([]);
  const [taskCount, setTaskCount] = useState(0);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const loadTasks = async () => {
      const tasks = await fetchTasks();
      setTasks(tasks.slice(0, 10)); // Limit to 4 tasks
    };

    const loadTaskCount = async () => {
      const count = await fetchTaskCount();
      setTaskCount(count);
    };

    loadTasks();
    loadTaskCount();

    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/tasks');
      if (!response.ok) {
        throw new Error(`Error fetching tasks: ${response.statusText}`);
      }
      const data = await response.json();
      console.log('Fetched tasks: ', data); // Log fetched tasks
      return data;
    } catch (error) {
      console.error('Error fetching tasks:', error);
      return [];
    }
  };

  const fetchTaskCount = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/tasks/count');
      if (!response.ok) {
        throw new Error(`Error fetching task count: ${response.statusText}`);
      }
      const data = await response.json();
      console.log('Fetched task count: ', data); // Log fetched task count
      return data;
    } catch (error) {
      console.error('Error fetching task count:', error);
      return 0;
    }
  };

  return (
    <div className="dashboard">
      <div className="sidebarD">
        <div className="avatar"></div>
        <div className='buttonCont'>
          <Link to="">
            <button className="buttonD1"><p>Coming Soon</p></button>
          </Link>
          <Link to="/ProfileSettings">
            <button className="buttonD1"><p>Settings</p></button>
          </Link>
          <Link to="/">
            <button className="buttonD2"><p>Log Out</p></button>
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
              <div className="task-count">{taskCount} Tasks</div>
              <div className="progress-bar">
                <div className="progress"></div>
              </div>
              <div className="completion-text">60% completed</div>
            </div>
          </div>
        </div>
        <div className="content">
          <div className="task-list">
            {tasks.map((task, index) => (
              <div key={index} className="task">
                <h3>{task.name}</h3>
                <div className="divider"></div>
                <p className='task1'>{task.description}</p>
                <p className='task1'>Due: {task.dueDate}</p>
                <p className='task1'>Status: {task.status}</p>
              </div>
            ))}
          </div>
        </div>
        <Calendar />
      </div>
    </div>
  );
};

export default Dashboard;
