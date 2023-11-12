package com.silovale.silovale_api.rest;

import com.silovale.silovale_api.model.PublicityDTO;
import com.silovale.silovale_api.service.PublicityService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/publicity", produces = MediaType.APPLICATION_JSON_VALUE)

public class PublicityResource {
    private final PublicityService publicityService;

    public PublicityResource(final PublicityService publicityService){
        this.publicityService = publicityService;
    }

    @GetMapping
    public ResponseEntity<List<PublicityDTO>> getAllPublicity(){
        return ResponseEntity.ok(publicityService.findAll());
    }

    @GetMapping("/{id}")
        public ResponseEntity<PublicityDTO> getPublicity(@PathVariable(name = "id") final Long id){
            return ResponseEntity.ok(publicityService.get(id));
        }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPublicity(@RequestBody @Valid final PublicityDTO publicityDTO){   
        final Long createId = publicityService.create(publicityDTO);
        return new ResponseEntity<>(createId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePublicity(@PathVariable(name = "id")final Long id,
            @RequestBody @Valid final PublicityDTO publicityDTO){
        publicityService.update(id, publicityDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePublicity(@PathVariable(name = "id")final Long id){
        publicityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
