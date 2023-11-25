package com.example.my_kursach.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SupplierController
{
    @GetMapping("/catalog")
    public String getCatalog(Model model)
    {
        model.addAttribute("tittle","Каталог товаров");
        return "catalog";
    }
}
