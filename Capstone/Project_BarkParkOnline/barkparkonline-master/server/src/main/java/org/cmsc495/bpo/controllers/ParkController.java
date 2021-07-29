package org.cmsc495.bpo.controllers;

import javax.validation.Valid;

import org.cmsc495.bpo.controllers.response.ApiResponse;
import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.repositories.BarkParkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkController {
    protected static final Logger log = LoggerFactory.getLogger(ParkController.class);

    private BarkParkRepository repo;

    @Autowired
    public ParkController(BarkParkRepository repo) {
        this.repo = repo;
    }
    
    @PostMapping(path = "/park/add")
    public ResponseEntity<?> addNewPark(
        @RequestBody @Valid BarkPark park
    ) {
        repo.create(park);
        return ResponseEntity.ok().body(park);
    }

    @GetMapping(path = "/park/all")
    public ResponseEntity<?> getAllParks(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "_id") String sortField

    ) {
        return ResponseEntity.ok().body(repo.getAll(offset, limit, sortField));
    }

    @GetMapping(path = "/park/{parkId}")
    public ApiResponse getPark(@PathVariable String parkId) {
        Park park = repo.getOne(parkId);
        if (park != null) {
            return ApiResponse.ok(park);
        }
        return ApiResponse.error(404, "Park Not Found");
    }
}
