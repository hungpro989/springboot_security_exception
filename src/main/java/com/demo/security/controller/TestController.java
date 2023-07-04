package com.demo.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess(){
        return "Public content";
    }
    @GetMapping("/sale")
    @PreAuthorize("hasAuthority('SALE') or hasAuthority('ADMIN') ")
    public String saleAccess(){
        return "Sale content";
    }
    @GetMapping("/marketing")
    @PreAuthorize("hasAuthority('MARKETING') or hasAuthority('ADMIN') ")
    public String marketingAccess(){
        return "Marketing content";
    }
    @GetMapping("/operator")
    @PreAuthorize("hasAuthority('OPERATOR') or hasAuthority('ADMIN') ")
    public String operatorAccess(){
        return "Operator content";
    }
    @GetMapping("/three")
    @PreAuthorize("hasAuthority('OPERATOR') or hasAuthority('SALE') or hasAuthority('MARKETING') ")
    public String threeAccess(){
        return "Three content";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminAccess(){
        return "Admin content";
    }
}
