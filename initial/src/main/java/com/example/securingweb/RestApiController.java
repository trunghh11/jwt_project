package com.example.securingweb;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private Map<String, String> data = new HashMap<>();

    public RestApiController() {
        // Initialize with some data
        data.put("1", "Data 1");
        data.put("2", "Data 2");
        data.put("3", "Data 3");
    }

    @GetMapping("/{id}")
    public String getData(@PathVariable String id) {
        return data.getOrDefault(id, "Data not found");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createData(@RequestParam String id, @RequestParam String value) {
        if (data.containsKey(id)) {
            return "Data already exists";
        }
        data.put(i@RequestParam String ideturn "Data created";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public String updateData(@PathVariable String id, @RequestParam String value) {
        if (data.containsKey(id)) {
            data.put(id, value);
            return "Data updated";
        } else {
            return "Data not found";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public String deleteData(@RequestParam String id) {
        if (data.containsKey(id)) {
            data.remove(id);
            return "Data deleted";
        } else {
            return "Data not found";
        }
    }
}