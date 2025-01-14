package com.example.mainservice.feign;

import com.example.mainservice.cfg.AuthCfg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "book-tracker-service", configuration = AuthCfg.class)
public interface BookTrackerServiceClient {
    @PostMapping("/keep/create/{id}")
    void create(@PathVariable("id") Long id);

    @DeleteMapping("/keep/delete/{id}")
    void delete(@PathVariable("id") Long id);

    @PostMapping("/keep/status/{id}/{status}")
    void changeStatus(@PathVariable("id") Long id,@PathVariable("status") String status);

    @GetMapping("/keep/free")
    List<Long> findFree();
}
