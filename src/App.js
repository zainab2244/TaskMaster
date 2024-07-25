import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './LoginPage';
import ResetPassword from './ResetPassword';
import SignUp from './SignUpPage';
import Dashboard from './Dashboard';
import AppSettings from './AppSettings';
import Notifications from './Notifications';
import ProfileSettings from './ProfileSettings';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/SignUpPage" element={<SignUp />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/AppSettings" element={<AppSettings />} />
        <Route path="/Notifications" element={<Notifications />} />
        <Route path="/ProfileSettings" element={<ProfileSettings />} />
      </Routes>
    </Router>
  );
}

export default App;
