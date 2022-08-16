package com.gt.express.service.util.feign;

import com.gt.express.model.Platform;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "database-service")
public interface FeignService {
    @GetMapping(value = "/database", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Platform> getDatabase();

    @PutMapping("/database/update")
    void updatePlatform(@RequestBody Platform newData);
}
