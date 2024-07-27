// src/components/Calendar.js
import React, { useState, useEffect } from 'react';
import './Calendar.css';

const Calendar = () => {
    const [currentMonth, setCurrentMonth] = useState(6);
    const [currentYear, setCurrentYear] = useState(2024);
    const [tasks, setTasks] = useState({});
    const today = new Date();

    useEffect(() => {
        updateCalendar();
        populateDueDateSelects();
        populateTimeSelects();
    }, [currentMonth, currentYear]);

    const updateCalendar = () => {
        const monthYearElement = document.getElementById('month-year');
        const calendarGrid = document.querySelector('.calendar-grid');
        const isCurrentMonth = today.getFullYear() === currentYear && today.getMonth() === currentMonth;
        const currentDate = today.getDate();

        monthYearElement.textContent = new Date(currentYear, currentMonth).toLocaleString('default', { month: 'long', year: 'numeric' });
        calendarGrid.innerHTML = `
            <div class="day-box">Sun</div><div class="day-box">Mon</div><div class="day-box">Tue</div><div class="day-box">Wed</div>
            <div class="day-box">Thu</div><div class="day-box">Fri</div><div class="day-box">Sat</div>
        `;

        const firstDay = new Date(currentYear, currentMonth).getDay();
        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

        for (let i = 0; i < 42; i++) {
            const day = i - firstDay + 1;
            if (i < firstDay || day > daysInMonth) {
                calendarGrid.insertAdjacentHTML('beforeend', `<div class="date-box"></div>`);
            } else {
                const isToday = isCurrentMonth && day === currentDate;
                const dateClass = isToday ? 'current-date' : '';
                const dateKey = `${currentYear}-${String(currentMonth + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
                const taskClass = tasks[dateKey] ? 'black-dot' : '';
                const dot = taskClass ? `<div class="dot ${taskClass}"></div>` : '';
                calendarGrid.insertAdjacentHTML('beforeend', `<div class="date-box ${dateClass}"><span>${day}</span>${dot}</div>`);
            }
        }
    };

    const prevMonth = () => {
        if (currentMonth === 0) {
            setCurrentMonth(11);
            setCurrentYear(currentYear - 1);
        } else {
            setCurrentMonth(currentMonth - 1);
        }
    };

    const nextMonth = () => {
        if (currentMonth === 11) {
            setCurrentMonth(0);
            setCurrentYear(currentYear + 1);
        } else {
            setCurrentMonth(currentMonth + 1);
        }
    };

    const openPopup = () => {
        document.getElementById('popupForm').style.display = 'block';
    };

    const closePopup = () => {
        document.getElementById('popupForm').style.display = 'none';
        resetForm();
    };

    const resetForm = () => {
        document.getElementById('taskName').value = '';
        document.getElementById('taskDescription').value = '';
        document.getElementById('taskCategory').value = '';
        document.getElementById('taskLocation').value = '';
        document.getElementById('taskDueDateDay').value = 'Day';
        document.getElementById('taskDueDateMonth').value = 'Month';
        document.getElementById('taskDueDateYear').value = 'Year';
        document.getElementById('taskHour').value = 'Hour';
        document.getElementById('taskMinute').value = 'Minute';
        document.getElementById('taskWeight').value = '';
        document.getElementById('taskStatus').value = '';
        document.getElementById('taskPercentage').value = '';
    };

    const addTask = async () => {
        const task = {
            name: document.getElementById('taskName').value,
            description: document.getElementById('taskDescription').value,
            category: document.getElementById('taskCategory').value,
            location: document.getElementById('taskLocation').value,
            dueDate: `${document.getElementById('taskDueDateYear').value}-${document.getElementById('taskDueDateMonth').value}-${document.getElementById('taskDueDateDay').value}`,
            estimatedTime: parseFloat(document.getElementById('taskHour').value) + parseFloat(document.getElementById('taskMinute').value) / 60,
            percentage: parseFloat(document.getElementById('taskPercentage').value),
        };

        await fetch('/api/tasks', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(task),
        });

        closePopup();
        updateCalendar();
        const response = await fetch('/api/tasks/count');
        const count = await response.json();
        document.querySelector('.task-count').textContent = `${count} Tasks`;
    };

    const populateDueDateSelects = () => {
        const daySelect = document.getElementById('taskDueDateDay');
        daySelect.innerHTML = '<option>Day</option>';
        const monthSelect = document.getElementById('taskDueDateMonth');
        monthSelect.innerHTML = '<option>Month</option>';
        const yearSelect = document.getElementById('taskDueDateYear');
        yearSelect.innerHTML = '<option>Year</option>';

        for (let i = 1; i <= 31; i++) {
            const option = document.createElement('option');
            option.value = i < 10 ? '0' + i : i;
            option.textContent = i < 10 ? '0' + i : i;
            daySelect.appendChild(option);
        }

        for (let i = 1; i <= 12; i++) {
            const option = document.createElement('option');
            option.value = i < 10 ? '0' + i : i;
            option.textContent = i < 10 ? '0' + i : i;
            monthSelect.appendChild(option);
        }

        const currentYear = new Date().getFullYear();
        for (let i = currentYear; i <= currentYear + 5; i++) {
            const option = document.createElement('option');
            option.value = i;
            option.textContent = i;
            yearSelect.appendChild(option);
        }
    };

    const populateTimeSelects = () => {
        const hourSelect = document.getElementById('taskHour');
        hourSelect.innerHTML = '<option>Hour</option>';
        const minuteSelect = document.getElementById('taskMinute');
        minuteSelect.innerHTML = '<option>Minute</option>';

        for (let i = 0; i < 24; i++) {
            const option = document.createElement('option');
            option.value = i < 10 ? '0' + i : i;
            option.textContent = i < 10 ? '0' + i : i;
            hourSelect.appendChild(option);
        }

        for (let i = 0; i < 60; i += 1) {
            const option = document.createElement('option');
            option.value = i < 10 ? '0' + i : i;
            option.textContent = i < 10 ? '0' + i : i;
            minuteSelect.appendChild(option);
        }
    };

    return (
        <div className="calendar-container">
            <div className="calendar-header">
                <h2 id="month-year">July 2024</h2>
                <div className="calendar-navigation">
                    <button onClick={prevMonth}>Previous</button>
                    <button onClick={nextMonth}>Next</button>
                </div>
            </div>
            <div className="calendar-grid">
                <div className="day-box">Sun</div>
                <div className="day-box">Mon</div>
                <div className="day-box">Tue</div>
                <div className="day-box">Wed</div>
                <div className="day-box">Thu</div>
                <div className="day-box">Fri</div>
                <div className="day-box">Sat</div>
            </div>
            <button className="addTask-button" onClick={openPopup}>+</button>
            <div className="popup-form" id="popupForm">
                <h3>Add New Task</h3>
                <label htmlFor="taskName">Task Name:</label>
                <input type="text" id="taskName" required />

                <label htmlFor="taskDescription">Description:</label>
                <textarea id="taskDescription" required></textarea>

                <label htmlFor="taskCategory">Category:</label>
                <input type="text" id="taskCategory" required />

                <label htmlFor="taskLocation">Location:</label>
                <input type="text" id="taskLocation" required />

                <label htmlFor="taskDueDateDay">Due Date:</label>
                <div style={{ display: 'flex' }}>
                    <select id="taskDueDateDay" className="due-date-select" required></select>
                    <select id="taskDueDateMonth" className="due-date-select" required></select>
                    <select id="taskDueDateYear" className="due-date-select" required></select>
                </div>

                <label htmlFor="taskHour">Estimated Time:</label>
                <div style={{ display: 'flex' }}>
                    <select id="taskHour" className="hour-select" required></select> :
                    <select id="taskMinute" className="minute-select" required></select>
                </div>

                <label htmlFor="taskWeight">Weight of Task:</label>
                <input type="text" id="taskWeight" required />

                <label htmlFor="taskStatus">Status of Task:</label>
                <input type="text" id="taskStatus" required />

                <label htmlFor="taskPercentage">Percentage:</label>
                <input type="text" id="taskPercentage" required />

                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <button className="cancel-button" onClick={closePopup}>Cancel</button>
                    <button className="add-button" onClick={addTask}>Add Task</button>
                </div>
            </div>
        </div>
    );
};

export default Calendar;
