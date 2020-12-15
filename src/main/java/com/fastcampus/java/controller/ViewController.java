package com.fastcampus.java.controller;

import com.fastcampus.java.controller.ifs.ViewInterface;

public abstract class ViewController implements ViewInterface {


    protected ViewInterface baseService;

    /*
    @Override
    @GetMapping("/list/item")
    public String list(Model model){
        List<Item> items = itemApiLogicService.findItems();
        model.addAttribute("items", items);

        return "list/item";
    }

     */
}
