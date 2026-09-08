package org.example.dsvisualizationweb.controllers;

import org.example.dsvisualizationweb.dtos.request.TreeRequest;
import org.example.dsvisualizationweb.dtos.response.HeapResponse;
import org.example.dsvisualizationweb.services.HeapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heap")
public class HeapController {

    private final HeapService heapService;

    public HeapController(HeapService heapService) {
        this.heapService = heapService;
    }

    @GetMapping("/state")
    public ResponseEntity<HeapResponse> getState() {
        return ResponseEntity.ok(heapService.getState());
    }

    @PostMapping("/insert")
    public ResponseEntity<HeapResponse> insert(@RequestBody TreeRequest request) {
        return ResponseEntity.ok(heapService.insert(request.getValue()));
    }

    @DeleteMapping("/extract")
    public ResponseEntity<HeapResponse> extractRoot() {
        return ResponseEntity.ok(heapService.extractRoot());
    }

    @GetMapping("/peek")
    public ResponseEntity<HeapResponse> peek() {
        return ResponseEntity.ok(heapService.peek());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<HeapResponse> clear() {
        return ResponseEntity.ok(heapService.clear());
    }
}
