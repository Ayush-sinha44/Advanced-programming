import React, { useState } from 'react';
import './App.css';
const CourseDashboard = () => {
  const [students, setStudents] = useState(new Map());
  const [name, setName] = useState('');
  const [gpa, setGpa] = useState('');
  const [courseInput, setCourseInput] = useState('');
  const [filterCourse, setFilterCourse] = useState('All');

  const addStudent = (e) => {
    e.preventDefault();
    if (!name || !gpa) return;

    const id = Date.now();
    const enrolledCourses = new Set(
      courseInput.split(',').map(c => c.trim()).filter(c => c)
    );

    const newStudent = { id, name, gpa: parseFloat(gpa), enrolledCourses };

    setStudents(new Map([...students, [id, newStudent]]));
    
    setName('');
    setGpa('');
    setCourseInput('');
  };

  const removeStudent = (id) => {
    const newMap = new Map(students);
    newMap.delete(id);
    setStudents(newMap);
  };

  const studentList = Array.from(students.values());

  const uniqueCourses = Array.from(
    studentList.reduce((acc, student) => {
      student.enrolledCourses.forEach(course => acc.add(course));
      return acc;
    }, new Set())
  );

  const displayedStudents = studentList
    .filter(s => filterCourse === 'All' || s.enrolledCourses.has(filterCourse))
    .sort((a, b) => b.gpa - a.gpa);

  return (
    <div className="dashboard-container">
      <h1>Course Enrollment Dashboard</h1>

      <form onSubmit={addStudent} className="student-form">
        <input 
          placeholder="Student Name" 
          value={name} 
          onChange={e => setName(e.target.value)} 
        />
        <input 
          placeholder="GPA" 
          type="number" 
          step="0.1" 
          value={gpa} 
          onChange={e => setGpa(e.target.value)} 
        />
        <input 
          placeholder="Courses (e.g. Math, CS)" 
          value={courseInput} 
          onChange={e => setCourseInput(e.target.value)} 
        />
        <button type="submit">Add Student</button>
      </form>

      <div className="filter-section">
        <label>Filter by Course: </label>
        <select value={filterCourse} onChange={(e) => setFilterCourse(e.target.value)}>
          <option value="All">All Courses</option>
          {uniqueCourses.map(course => (
            <option key={course} value={course}>{course}</option>
          ))}
        </select>
      </div>

      <div className="student-grid">
        {displayedStudents.map(student => (
          <div key={student.id} className="student-card">
          <div className="card-header">
            <div>
            <h3>{student.name}</h3>
            <p className="student-id">ID: {student.id}</p>
          </div>
          <span className="gpa-badge">{student.gpa}</span>
          </div>
            <div className="course-tags">
              {Array.from(student.enrolledCourses).map(c => (
                <span key={c} className="tag">{c}</span>
              ))}
            </div>
            <button onClick={() => removeStudent(student.id)} className="delete-btn">
              Remove
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseDashboard;