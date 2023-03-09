package com.cieciak.web;

import com.cieciak.application.DneSequenceCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@Slf4j
public class DneSequenceController {

    private final DneSequenceCheckerService dneSequenceCheckerService;

    @PostMapping
    public ResponseEntity<Boolean> hasDneSequence(final @RequestBody @Validated InputRequestBody inputRequestBody) {
        log.info("Received POST request with body: {}", inputRequestBody);
        boolean hasDneSequence = dneSequenceCheckerService.containsDneSequence(inputRequestBody.getSeq());
        return ResponseEntity.ok(hasDneSequence);
    }
}
