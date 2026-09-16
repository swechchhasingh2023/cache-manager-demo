import { useState } from "react";
import "./App.css";

function App() {
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [response, setResponse] = useState("");

  const savePromotion = async () => {
    const response = await fetch("http://localhost:8080/promotions", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: Number(id),
        name: name,
      }),
    });

    if (response.ok) {
      setResponse("Promotion saved successfully");
    } else {
      setResponse("Failed to save promotion");
    }
  };

  const getPromotion = async () => {
    const response = await fetch(`http://localhost:8080/promotions/${id}`);

    if (response.ok) {
      const data = await response.json();
      setResponse(JSON.stringify(data));
    } else {
      setResponse("Promotion not found");
    }
  };

  const removeFromCache = async () => {
    const response = await fetch(`http://localhost:8080/promotions/${id}`, {
      method: "DELETE",
    });

    if (response.ok) {
      setResponse("Promotion removed from cache");
    } else {
      setResponse("Failed to remove from cache");
    }
  };

  const clearCache = async () => {
    const response = await fetch("http://localhost:8080/promotions/cache", {
      method: "DELETE",
    });

    if (response.ok) {
      setResponse("Cache cleared successfully");
    } else {
      setResponse("Failed to clear cache");
    }
  };

  return (
    <div className="app">
      <h1>Promotion Manager</h1>

      <div className="form">
        <label>Promotion ID</label>
        <input
          type="number"
          value={id}
          onChange={(e) => setId(e.target.value)}
          placeholder="Enter promotion ID"
        />

        <label>Promotion Name</label>
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Enter promotion name"
        />

        <div className="buttons">
          <button onClick={savePromotion}>Save Promotion</button>
          <button onClick={getPromotion}>Get Promotion</button>
          <button onClick={removeFromCache}>Remove From Cache</button>
          <button onClick={clearCache}>Clear Cache</button>
        </div>
      </div>

      <div className="response">
        <h2>Response</h2>
        <p>{response || "No response yet"}</p>
      </div>
    </div>
  );
}

export default App;
