package by.bsuir.makogon.foodtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
    @GetMapping("/products")
    public String products(Model model){
        return "products";
    }
}
