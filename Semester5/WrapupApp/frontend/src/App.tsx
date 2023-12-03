import { useEffect, useRef, useState } from "react";

type DeltaT = {
  x: number;
  y: number;
};

type CircleT = {
  id: number;
  left: number;
  top: number;
};

const screenWidth = window.innerWidth - 100;
const screenHeight = window.innerHeight - 100;

const App = () => {
  const [circles, setCircles] = useState<CircleT[]>([]);
  const [noOfCircle, setNoOfCircle] = useState(20);
  const [wrap, setWrap] = useState<CircleT[]>([]);
  const captured = useRef(-1);

  const handleDrag = (delta: DeltaT) => {
    if (captured.current !== -1) {
      setCircles((prevCircles) => {
        const updatedCircles = [...prevCircles];
        const currentCircle = updatedCircles[captured.current];
        if (currentCircle) {
          updatedCircles[captured.current].left = delta.x || 0;
          updatedCircles[captured.current].top = delta.y || 0;
        }
        return updatedCircles;
      });
    }
  };

  const createCircles = () => {
    const circles: CircleT[] = Array.from({ length: noOfCircle }).map(
      (_, index) => {
        const id = index + 1;
        const left = Math.random() * screenWidth;
        const top = Math.random() * screenHeight;
        return {
          id,
          left,
          top,
        };
      }
    );
    setCircles(circles);
  };

  useEffect(() => {
    createCircles();
    const handleMouseUp = () => {
      captured.current = -1;
    };
    const handleMouseMove = (e: MouseEvent) => {
      const delta: DeltaT = { x: e.clientX, y: e.clientY };
      handleDrag(delta);
    };
    window.addEventListener("mousemove", handleMouseMove);
    window.addEventListener("mouseup", handleMouseUp);
    return () => {
      window.removeEventListener("mousemove", handleMouseMove);
      window.removeEventListener("mouseup", handleMouseUp);
    };
  }, []);

  const handleWrap = () => {
    fetch("http://localhost:8080/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(circles),
    })
      .then((res) => res.json())
      .then((res) => {
        setWrap(res);
      });
  };

  const refresh = () => {
    createCircles();
    setWrap([]);
  };

  return (
    <div className="draggable-circles-page">
      <LineThroughPoints points={wrap} />
      {circles.map((circle, index) => (
        <div
          key={circle.id}
          className="circle"
          style={{
            left: circle.left,
            top: circle.top,
            transform: `translate(-50%, -50%)`,
            backgroundColor: wrap.find((c) => c.id === circle.id)
              ? "red"
              : "#3498db",
          }}
          onMouseDown={() => (captured.current = index)}
        >
          {circle.id}
        </div>
      ))}
      <div className="actions">
        <div className="no-of-circle">
          <input
            value={noOfCircle || 0}
            onChange={(e) => {
              setNoOfCircle(parseInt(e.target.value));
            }}
          />
        </div>
        <button className="refresh-button" onClick={refresh}>
          Refresh
        </button>
        <button className="wrap-button" onClick={handleWrap}>
          Wrap the area
        </button>
      </div>
    </div>
  );
};

const LineThroughPoints = ({ points }: { points: CircleT[] }) => {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    if (points.length) {
      points = [...points, points[0]];
      const canvas = canvasRef.current;
      const ctx = canvas?.getContext("2d");
      if (canvas && ctx) {
        // Clear the canvas
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Set line style
        ctx.strokeStyle = "red";
        ctx.lineWidth = 2;

        // Draw lines between points
        ctx.beginPath();
        ctx.moveTo(points[0].left, points[0].top);

        for (let i = 1; i < points.length; i++) {
          ctx.lineTo(points[i].left, points[i].top);
        }

        ctx.stroke();
      }
    }
  }, [points]);

  if (points.length === 0) return null;

  return <canvas ref={canvasRef} width={screenWidth} height={screenHeight} />;
};

export default App;
