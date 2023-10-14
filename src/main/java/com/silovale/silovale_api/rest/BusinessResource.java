package com.silovale.silovale_api.rest;

import com.silovale.silovale_api.model.BusinessDTO;
import com.silovale.silovale_api.service.BusinessService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/businesses", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessResource {

    private final BusinessService businessService;

    public BusinessResource(final BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public ResponseEntity<List<BusinessDTO>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessDTO> getBusiness(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(businessService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBusiness(@RequestBody @Valid final BusinessDTO businessDTO) {
        final Long createdId = businessService.create(businessDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBusiness(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final BusinessDTO businessDTO) {
        businessService.update(id, businessDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBusiness(@PathVariable(name = "id") final Long id) {
        businessService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
