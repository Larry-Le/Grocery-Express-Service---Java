package com.express.database;

import com.express.database.model.Customer;
import com.express.database.model.DronePilot;
import com.express.database.model.Platform;
import com.express.database.model.Store;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class Controller {
    Map<String, Store> stores = new TreeMap<>();
    Map<String, DronePilot> pilots = new TreeMap<>();
    Map<String, Customer> customers = new TreeMap<>();

    Platform platform = new Platform(stores,pilots,customers);

    @GetMapping(value = "/database",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Platform> getDatabase() {
        return ResponseEntity.ok().body(platform);
    }

    @PutMapping("/database/update")
    public void updatePlatform(@RequestBody Platform newData) {
        platform = newData;
        System.out.println("success");
    }
}
