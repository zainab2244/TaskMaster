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

export const getUserFirstName = async () => {
  try {
    const response = await axios.get(`${API_URL}/user/firstname`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user first name', error);
    throw error;
  }
};
