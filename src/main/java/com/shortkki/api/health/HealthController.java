package com.shortkki.api.health;

import com.shortkki.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<BaseResponse<Void>> health() {
        return ResponseEntity.ok(BaseResponse.success());
    }
}
