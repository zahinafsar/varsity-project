package com.conversee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.conversee.entity.Message;
import com.conversee.repository.MessageRepository;

@Controller
public class ChatController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/app")
    @ResponseBody
    public String getAppInfo() {
        return "Conversee Chat Application v1.0";
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        messageRepository.save(message);
        return message;
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public Message notifyTyping(Message message) {
        return message;
    }
}
