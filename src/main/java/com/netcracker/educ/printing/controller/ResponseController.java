package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.model.representationModel.ResponseRepresent;
import com.netcracker.educ.printing.service.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/response")
@AllArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<String> createResponse(@RequestBody ResponseRepresent responseRepresent) {
        try {
            responseService.createResponse(responseRepresent);
        } catch (CreatingResponseException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Заказ успешно отправлен исполнителю.");
    }
}
