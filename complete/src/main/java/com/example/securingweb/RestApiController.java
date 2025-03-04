package com.example.securingweb;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class RestApiController {

    private Map<String, String> data = new HashMap<>();

    public RestApiController() {
        // Initialize with some data
        data.put("1", "Data 1");
        data.put("2", "Data 2");
        data.put("3", "Data 3");
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}") //get task by task id
    public String getData(@PathVariable String id) {
        return data.getOrDefault(id, "Data not found");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping //create task
    public String createData(@RequestParam String id, @RequestParam String value) {
        if (data.containsKey(id)) {
            return "Data already exists";
        }
        data.put(id, value);
        return "Data created";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") //update task
    public String updateData(@PathVariable String id, @RequestParam String value) {
        if (data.containsKey(id)) {
            data.put(id, value);
            return "Data updated";
        } else {
            return "Data not found";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}") //delete task
    public String deleteData(@PathVariable String id) {
        if (data.containsKey(id)) {
            data.remove(id);
            return "Data deleted";
        } else {
            return "Data not found";
        }
    }
}