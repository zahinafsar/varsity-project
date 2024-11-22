"use client";

import { v4 as uuid } from "uuid";
import { api } from "@/utils/api";
import { connectWebSocket } from "@/utils/socket";
import { Client } from "@stomp/stompjs";
import { useState, useEffect, useRef, useCallback } from "react";

type Message = {
  id: string;
  username: string;
  content: string;
};

let currentMessageId = uuid();

export function ChatForm({ username }: { username: string }) {
  const clientRef = useRef<Client | null>(null);
  const [trigger, setTrigger] = useState("");
  const callTrigger = () => setTrigger(uuid());
  const [messages, setMessages] = useState<Message[]>([]);
  const [realtimeMessages, setRealtimeMessages] = useState<Message[]>([]);

  const disconnect = () => {
    if (clientRef.current) clientRef.current?.deactivate();
  };

  useEffect(() => {
    api
      .get("/messages")
      .then((data) => setMessages(data.reverse()))
      .catch((err) => console.error("Error fetching messages:", err));

    const client = connectWebSocket((c) => {
      c.subscribe("/topic/messages", (message) => {
        const msg = JSON.parse(message.body) as Message;
        setMessages((prev) => [msg, ...prev]);
        callTrigger();
        setRealtimeMessages((prev) =>
          prev.filter((p) => p.username !== msg.username)
        );
      });
      c.subscribe("/topic/typing", (message) => {
        callTrigger();
        const msg = JSON.parse(message.body) as Message;
        if (msg.username === username) return;
        setRealtimeMessages((prev) => {
          if (msg.content.length === 0) {
            return prev.filter((p) => p.username !== msg.username);
          }
          const existingMessage = prev.findIndex(
            (p) => p.username === msg.username
          );
          if (existingMessage !== -1) {
            prev[existingMessage] = msg;
            return prev;
          }
          return [msg, ...prev];
        });
      });
    });
    clientRef.current = client;
    return () => disconnect();
  }, []);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!username.trim()) {
      alert("Please enter a username");
      return;
    }

    const formData = new FormData(e.currentTarget);
    const message = formData.get("message")?.toString();

    if (clientRef.current && message?.trim()) {
      clientRef.current.publish({
        destination: "/app/send",
        body: JSON.stringify({
          id: currentMessageId,
          username: username,
          content: message,
        }),
      });
      e.currentTarget.reset();
    }
    currentMessageId = uuid();
  };

  const handleTyping = (message: string) => {
    if (clientRef.current && clientRef.current.connected) {
      clientRef.current.publish({
        destination: "/app/typing",
        body: JSON.stringify({
          id: currentMessageId,
          username: username,
          content: message,
        }),
      });
    }
  };

  const getActiveTyping = useCallback(() => {
    return realtimeMessages;
  }, [trigger]);

  return (
    <div>
      <h1 className="text-xl font-bold text-center text-gray-800 mb-4">
        {username}
      </h1>
      <div className="flex flex-col-reverse h-[400px] overflow-y-scroll border border-gray-300 rounded-lg p-4 bg-gray-50">
        {getActiveTyping().map((msg) => (
          <div
            key={msg.id}
            className={`p-2 pr-5 rounded-lg mb-2 bg-gray-100 self-start text-gray-800`}
          >
            <div className="text-sm text-gray-500 italic flex items-end gap-1">
              <span>{msg.username}</span>
              <span>is typing</span>
              <TypingAnimation />
            </div>
            {/* <span className="font-semibold">{msg.username}:</span>{" "} */}
            {msg.content}
          </div>
        ))}
        {messages.map((msg, index) => (
          <div
            key={msg.id}
            className={`p-2 rounded-lg mb-2 ${
              msg.username === username
                ? "bg-blue-100 self-end text-blue-800"
                : "bg-gray-100 self-start text-gray-800"
            }`}
          >
            <span className="font-semibold">{msg.username}:</span> {msg.content}
          </div>
        ))}
      </div>
      <form onSubmit={handleSubmit} className="flex mt-4">
        <input
          type="text"
          name="message"
          placeholder="Type your message..."
          onChange={(v) => handleTyping(v.target.value)}
          className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <button
          type="submit"
          className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
        >
          Send
        </button>
      </form>
    </div>
  );
}

const TypingAnimation = () => {
  return (
    <div className="flex gap-1 mb-1">
      <div
        style={{
          animationDelay: "100ms",
        }}
        className="w-1 h-1 rounded-full bg-gray-400 animate-bounce delay-0"
      ></div>
      <div
        style={{
          animationDelay: "200ms",
        }}
        className="w-1 h-1 rounded-full bg-gray-400 animate-bounce delay-300"
      ></div>
      <div
        style={{
          animationDelay: "300ms",
        }}
        className="w-1 h-1 rounded-full bg-gray-400 animate-bounce delay-700"
      ></div>
    </div>
  );
};
