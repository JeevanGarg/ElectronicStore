package com.ElectronicStore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("electronic")
public class ElectronicController
{
    @GetMapping("/name")
    public String getName()
    {
        return "Jeevan Garg";
    }

}
