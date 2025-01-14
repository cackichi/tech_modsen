package com.example.librarysevice.controllers;

import com.example.librarysevice.service.BookKeepingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keep")
@AllArgsConstructor
public class KeepController {
    private final BookKeepingService bookKeepingService;

    @PostMapping("/create/{id}")
    public void create(@PathVariable("id") Long id){
        bookKeepingService.save(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        bookKeepingService.delete(id);
    }

    @PostMapping("/status/{id}/{status}")
    public void changeStatus(@PathVariable("id") Long id,@PathVariable("status") String status){
        bookKeepingService.changeStatus(id,status);
    }

    @GetMapping("/free")
    public List<Long> findFree(){
        return bookKeepingService.findAllFree();
    }
}

