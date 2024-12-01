import { useEffect, useRef, useState } from "react";

interface DraggableProps {
  children?: React.ReactNode;
  dragger?: React.ReactNode;
  className?: string;
  onClick?: (prev: boolean) => void;
}
const Draggable = ({
  children,
  dragger,
  className,
  onClick,
}: DraggableProps) => {
  const initialPosition = { x: 20, y: 20 };
  const [position, setPosition] = useState(initialPosition);
  const mouseTracker = useRef({ start: { x: 0, y: 0 }, end: { x: 0, y: 0 } });
  const isDragging = useRef(false);
  const offset = useRef({ x: 0, y: 0 });
  const elementRef = useRef(null);

  useEffect(() => {
    document.addEventListener("mousemove", handleMouseMove);
    document.addEventListener("mouseup", handleMouseUp);
    return () => {
      document.removeEventListener("mousemove", handleMouseMove);
      document.removeEventListener("mouseup", handleMouseUp);
    };
  }, []);

  const handleMouseDown = (e) => {
    isDragging.current = true;
    mouseTracker.current.start = {
      x: e.clientX,
      y: e.clientY,
    };
    offset.current = {
      x: e.clientX - position.x,
      y: e.clientY - position.y,
    };
  };

  const handleMouseMove = (e) => {
    if (isDragging.current) {
      const elementWidth = elementRef.current.offsetWidth;
      const elementHeight = elementRef.current.offsetHeight;

      const newX = e.clientX - offset.current.x;
      const newY = e.clientY - offset.current.y;

      const screenWidth = window.innerWidth;
      const screenHeight = window.innerHeight;

      const maxX = screenWidth - elementWidth;
      const maxY = screenHeight - elementHeight;

      setPosition({
        x: Math.min(Math.max(newX, 0), maxX),
        y: Math.min(Math.max(newY, 0), maxY),
      });
    }
  };

  const handleMouseUp = (e) => {
    mouseTracker.current.end = {
      x: e.clientX,
      y: e.clientY,
    };
    isDragging.current = false;
  };

  const handleMinimize = (e) => {
    const { start, end } = mouseTracker.current;
    if (start.x === end.x && start.y === end.y) {
      setPosition(initialPosition);
      onClick(false);
    }
  };

  return (
    <div
      onClick={handleMinimize}
      ref={elementRef}
      style={{ position: "fixed", top: position.y, left: position.x, zIndex: 9999999 }}
      className={className}
    >
      <div onMouseDown={handleMouseDown}>{dragger}</div>
      {children}
    </div>
  );
};

export default Draggable;
