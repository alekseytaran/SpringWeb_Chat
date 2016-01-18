package com.teamdev.controller;

import com.teamdev.dto.MessageDto;
import com.teamdev.dto.wrappers.MessageId;
import com.teamdev.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public MessageId signUp(@RequestParam String token, @RequestBody MessageDto messageDto) {
        return messageService.postMessage(token, messageDto);
    }
}
