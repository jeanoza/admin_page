package com.fastcampus.java.controller;

import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @GetMapping("/item")
    public String list(Model model) {
        List<Item> items = itemApiLogicService.findItems();
        model.addAttribute("items", items);

        return "api/item";
    }

}
