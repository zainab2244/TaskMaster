import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const getUserData = async () => {
  try {
    const response = await axios.get(`${API_URL}/user/data`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user data', error);
    throw error;
  }
};

export const updateUserData = async (data) => {
  try {
    const response = await axios.put(`${API_URL}/user/data`, data);
    return response.data;
  } catch (error) {
    console.error('Error updating user data', error);
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

export const createUser = async (user) => {
  try {
    const response = await axios.post(`${API_URL}/users`, user);
    return response.data;
  } catch (error) {
    console.error('Error creating user', error);
    throw error;
  }
};

export const getHelloMessage = async () => {
  try {
    const response = await axios.get(`${API_URL}/hello`);
    return response.data;
  } catch (error) {
    console.error('Error fetching hello message', error);
    throw error;
  }
};
