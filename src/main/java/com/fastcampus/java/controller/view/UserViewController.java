package com.fastcampus.java.controller.view;

import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.entity.User;
import com.fastcampus.java.repository.UserRepository;
import com.fastcampus.java.service.ItemApiLogicService;
import com.fastcampus.java.service.UserApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserViewController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list/user")
    public String list(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "list/user";
    }
}
