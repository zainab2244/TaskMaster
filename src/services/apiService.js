// src/services/apiService.js

import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const getHelloMessage = async () => {
  try {
    const response = await axios.get(`${API_URL}/hello`);
    return response.data;
  } catch (error) {
    console.error('Error fetching hello message', error);
    throw error;
  }
};

export const sendData = async (data) => {
  try {
    const response = await axios.post(`${API_URL}/data`, data);
    return response.data;
  } catch (error) {
    console.error('Error sending data', error);
    throw error;
  }

};

export const getUserFirstNames = async () => {
  try {
    const response = await axios.get(`${API_URL}/user/firstnames`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user first names', error);
    throw error;
  }
};

export const loginUser = async (username, password) => {
  try {
    const response = await axios.post(`${API_URL}/login`, { username, password });
    if (response.data) {
      localStorage.setItem('user', JSON.stringify(response.data)); // Store user information in localStorage
      return true;
    } else {
      return false;
    }
  } catch (error) {
    console.error('Error logging in', error);
    return false;
  }
};