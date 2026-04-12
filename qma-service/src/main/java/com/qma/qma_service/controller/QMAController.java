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

    // ================= PUBLIC API =================
    @GetMapping("/public/test")
    public String publicApi() {
        return "Public API working";
    }

    // ================= PRIVATE TEST =================
    @GetMapping("/private/test")
    public String privateApi() {
        return "Private API working";
    }

    // ================= CONVERT =================
    @PostMapping("/private/convert")
    public double convert(
            @RequestBody Map<String, Object> req,
            @RequestHeader("X-User-Email") String email
    ) {
        String type = (String) req.get("type");
        double value = Double.parseDouble(req.get("value").toString());
        String from = (String) req.get("from");
        String to = (String) req.get("to");

        return service.convert(type, value, from, to, email);
    }

    // ================= HISTORY =================
    @GetMapping("/private/history")
    public List<ConversionHistory> history(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getHistory(email);
    }

    // ================= COMPARE (FIXED) =================
//    @GetMapping("/private/compare")
//    public String compare(
//            @RequestParam String type,
//            @RequestParam Double value1,
//            @RequestParam String unit1,
//            @RequestParam Double value2,
//            @RequestParam String unit2,
//            @RequestHeader("X-User-Email") String email
//    ) {
//
//        if (value1 == null || value2 == null) {
//            return "Values missing";
//        }
//
//        double v1 = convertToBase(type, value1, unit1);
//        double v2 = convertToBase(type, value2, unit2);
//
//        if (v1 > v2) return "FIRST_GREATER";
//        else if (v1 < v2) return "SECOND_GREATER";
//        else return "EQUAL";
//    }

    // ================= HELPER =================
    private double convertToBase(String type, double value, String unit) {

        if ("LENGTH".equalsIgnoreCase(type)) {

            if ("METER".equalsIgnoreCase(unit)) {
                return value * 100; // convert to CM
            }

            if ("CM".equalsIgnoreCase(unit)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Unsupported unit");
    }

    // ================= HEALTH CHECK =================
    @GetMapping("/test")
    public String test() {
        return "QMA Service Working";
    }


    // =================== Compare =====================

    @PostMapping("/private/compare")
    public String compare(
            @RequestBody Map<String, Object> req,
            @RequestHeader("X-User-Email") String email
    ) {

        String type = (String) req.get("type");

        Double value1 = Double.parseDouble(req.get("value1").toString());
        String unit1 = (String) req.get("unit1");

        Double value2 = Double.parseDouble(req.get("value2").toString());
        String unit2 = (String) req.get("unit2");

        double v1 = convertToBase(type, value1, unit1);
        double v2 = convertToBase(type, value2, unit2);

        if (v1 > v2) return "FIRST_GREATER";
        else if (v1 < v2) return "SECOND_GREATER";
        else return "EQUAL";
    }
}