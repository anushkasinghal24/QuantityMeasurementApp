package com.qma.qma_service.controller;

import com.qma.qma_service.entity.ConversionHistory;
import com.qma.qma_service.service.QMAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qma")
public class QMAController {

    @Autowired
    private QMAService service;

    @PostMapping("/convert")
    public double convert(@RequestBody Map<String, Object> req) {

        String type = (String) req.get("type");
        double value = Double.parseDouble(req.get("value").toString());
        String from = (String) req.get("from");
        String to = (String) req.get("to");

        return service.convert(type, value, from, to);
    }

    @GetMapping("/history")
    public List<ConversionHistory> history() {
        return service.getHistory();
    }
    @GetMapping("/test")
    public String test() {
        return "QMA Service Working";
    }
}
