import { useState } from "react";
import "./App.css";

export default function App() {
  const [todos, setTodos] = useState([]);
  const [input, setInput] = useState("");

  const addTodo = () => {
    const trimmed = input.trim();
    if (!trimmed) return;
    setTodos([{ id: Date.now(), text: trimmed, done: false }, ...todos]);
    setInput("");
  };

  const toggleTodo = (id) => {
    setTodos(todos.map((t) => (t.id === id ? { ...t, done: !t.done } : t)));
  };

  const deleteTodo = (id) => {
    setTodos(todos.filter((t) => t.id !== id));
  };

  return (
    <div className="page">
      <div className="orb orb1" />
      <div className="orb orb2" />

      <div className="card">
        <div className="card-header">
          <span className="icon">✦</span>
          <h1>My Tasks</h1>
          <span className="count">{todos.filter((t) => !t.done).length} remaining</span>
        </div>

        <div className="input-row">
          <input
            className="todo-input"
            placeholder="What needs to be done?"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && addTodo()}
          />
          <button className="add-btn" onClick={addTodo}>
            Add
          </button>
        </div>

        <ul className="todo-list">
          {todos.length === 0 && (
            <li className="empty-state">No tasks yet — add one above ↑</li>
          )}
          {todos.map((todo) => (
            <li key={todo.id} className={`todo-item ${todo.done ? "done" : ""}`}>
              <button className="check-btn" onClick={() => toggleTodo(todo.id)}>
                {todo.done ? "✓" : ""}
              </button>
              <span className="todo-text">{todo.text}</span>
              <button className="delete-btn" onClick={() => deleteTodo(todo.id)}>
                ✕
              </button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}