import { useEffect, useRef, useState } from "react";
import { _sendMessage } from "../api/chat_gpt";
import { rootElement } from "./app";
import Draggable from "./components/Draggable";
import TypingLoader from "./components/TypingLoader";
import { SVG } from "../icon";
import { IQuery } from "../interface/message";
import { generateQuery } from "../utils/parser";
import { ACTIONS } from "../../constant/index.mjs";

const Modal = () => {
  const [input, setInput] = useState("");
  const [loader, setLoader] = useState(false);
  const [minimize, setMinimize] = useState(false);
  const [query, setQuery] = useState<IQuery[]>([]);
  const messagesEndRef = useRef(null);

  useEffect(() => {
    const handleKeyDown = (e) => {
      if (e.key === "Enter") {
        send();
      }
    };
    document.addEventListener("keydown", handleKeyDown);
    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, [input]);

  useEffect(() => {
    messagesEndRef.current.scrollTo({
      top: messagesEndRef.current.scrollHeight,
      behavior: "smooth",
    });
  }, [query]);

  const handleInput = (e) => {
    setInput(e.target.value);
  };

  useEffect(() => {
    const action = sessionStorage.getItem("spm-action");
    // console.log({action});
    const selected = window.getSelection().toString();
    if (action === ACTIONS.ASK.id) {
      send(`What is "${selected}"?`);
    } else if (action === ACTIONS.DESCRIBE.id) {
      send(`Describe : "${selected}"`);
    } else if (action === ACTIONS.SUMMARIZE.id) {
      send(`Summarize : "${selected}"`);
    } else {
      setInput(selected);
    }
  }, []);

  const onClose = () => {
    document.body.removeChild(rootElement);
  };

  const onMinimize = () => {
    setMinimize((prev) => !prev);
  };

  const send = async (message?: string) => {
    if (!message && !input) return;
    const tempQuery = [...query, { question: input || message }];
    try {
      setQuery(tempQuery);
      setInput("");
      const questionQuery = generateQuery({
        query: tempQuery,
      });
      setLoader(true);
      const res = await _sendMessage(questionQuery);
      setLoader(false);
      const answer = res.choices?.[0]?.message?.content;
      setQuery([...tempQuery, { answer }]);
    } catch (error) {
      setLoader(false);
      setQuery([...tempQuery, { answer: error.error.message }]);
      console.log(error);
    }
  };
  if (!minimize) {
    return (
      <Draggable
        className={"spm-container"}
        dragger={
          <div className="spm-modal-bar">
            <h3 style={{ margin: 0, color: "white" }}>Quint</h3>
            <div style={{ display: "flex", gap: "10px" }}>
              <button className="spm-close-button" onClick={onMinimize}>
                _
              </button>
              <button className="spm-close-button" onClick={onClose}>
                x
              </button>
              {/* <div className="spm-icon-button" onClick={onClose}>
                <SVG.threeDots/>
              </div> */}
            </div>
          </div>
        }
      >
        <div className="spm-message-area" ref={messagesEndRef}>
          {query.map((data, index) => (
            <>
              {data.question && (
                <div key={`q-${index}`}>
                  <p className="spm-avatar spm-avatar-user">Me</p>
                  <p style={{ margin: "10px 0", color: "white" }}>
                    {data.question}
                  </p>
                </div>
              )}
              {data.answer && (
                <div key={`a-${index}`}>
                  <p className="spm-avatar">Q</p>
                  <p style={{ margin: "10px 0", color: "white" }}>
                    {data.answer}
                  </p>
                </div>
              )}
            </>
          ))}
        </div>
        <div
          style={{
            margin: "15px",
            display: "flex",
            position: "relative",
            alignItems: "center",
          }}
        >
          {loader && <TypingLoader />}
          <input
            type="text"
            className="spm-message-input"
            placeholder="Type your message here..."
            value={input}
            onChange={handleInput}
          />
          {!loader && (
            <div onClick={() => send()} className="spm-message-button">
              <SVG.send />
            </div>
          )}
        </div>
      </Draggable>
    );
  } else {
    return (
      <Draggable
        onClick={setMinimize}
        dragger={
          <div className="spm-float">
            <SVG.logo />
          </div>
        }
      />
    );
  }
};

export default Modal;
