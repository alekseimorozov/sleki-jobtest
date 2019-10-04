package ru.jobtest.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.jobtest.chat.entities.ChatRoom;
import ru.jobtest.chat.entities.Message;
import ru.jobtest.chat.entities.User;
import ru.jobtest.chat.services.ChatService;

@Controller
public class ChatController {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chatlist")
    public String openChatList(@RequestParam("userName") String userName, Model model) {
        User user = chatService.findOrCreate(userName);
        model.addAttribute("user", user);
        model.addAttribute("chatList", chatService.findAllChats());
        return "chatlist";
    }

    @PostMapping("/addchatroom")
    public String addChatRoom(@RequestParam("userId") Long userId,
                              @RequestParam("chatName") String chatName,
                              RedirectAttributes attr
    ) {
        User admin = chatService.findUserById(userId);
        chatService.addChatRoom(ChatRoom.builder().admin(admin).name(chatName).build());
        attr.addAttribute("userName", admin.getName());
        return "redirect:chatlist";
    }



    @GetMapping("/chatroom")
    public String openChatRoom(@RequestParam("userId") Long userId, @RequestParam("chatId") Long chatId, Model model) {
        model.addAttribute("user", chatService.findUserById(userId));
        ChatRoom chatRoom = chatService.findChatRoomById(chatId);
        model.addAttribute("chatroom", chatRoom);
        model.addAttribute("messages", chatService.findAllMessagesForChatRoom(chatRoom));
        return "chatroom";
    }

    @PostMapping("/postmessage")
    public String postMessage(@RequestParam("userId") Long userId,
                              @RequestParam("chatId") Long chatId,
                              @RequestParam("text") String text,
                              RedirectAttributes attr) {
        Message message = Message.builder()
                .author(chatService.findUserById(userId))
                .chatRoom(chatService.findChatRoomById(chatId))
                .text(text)
                .build();
        chatService.saveMessage(message);
        attr.addAttribute("userId", userId);
        attr.addAttribute("chatId", chatId);
        return "redirect:chatroom";
    }

    @PostMapping("/deletechat")
    public String deleteChat(@RequestParam("chatId") Long chatId, Model model) {
        ChatRoom chatRoom = chatService.findChatRoomById(chatId);
        chatService.deleteChatRoom(chatRoom);
        model.addAttribute("user", chatRoom.getAdmin());
        model.addAttribute("chatList", chatService.findAllChats());
        return "chatlist";
    }
}
