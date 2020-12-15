package com.fastcampus.java.controller.view;

import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.repository.ItemRepository;
import com.fastcampus.java.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemViewController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/list/item")
    public String list(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "list/item";
    }
}
