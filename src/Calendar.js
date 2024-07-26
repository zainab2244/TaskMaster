import React, { useState, useEffect } from 'react';
import './Calendar.css';

const Calendar = () => {
  const [currentMonth, setCurrentMonth] = useState(6); // July is month 6 (0-based index)
  const [currentYear, setCurrentYear] = useState(2024);

  const updateCalendar = () => {
    const today = new Date();
    const isCurrentMonth = today.getFullYear() === currentYear && today.getMonth() === currentMonth;
    const currentDate = today.getDate();

    const monthYearElement = document.getElementById('month-year');
    const calendarGrid = document.querySelector('.calendar-grid');

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
        calendarGrid.insertAdjacentHTML('beforeend', `<div class="date-box ${dateClass}"><span>${day}</span></div>`);
      }
    }
  };

  useEffect(() => {
    updateCalendar();
  }, [currentMonth, currentYear]);

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

  const addTask = () => {
    alert('Insert New Task Here');
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
      <button className="addTask-button" onClick={addTask}>+</button>
    </div>
  );
};

export default Calendar;
