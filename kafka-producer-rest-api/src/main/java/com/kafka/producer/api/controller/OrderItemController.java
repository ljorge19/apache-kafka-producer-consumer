package com.kafka.producer.api.controller;


import com.kafka.producer.api.dto.CustomerDTO;
import com.kafka.producer.api.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemController.class);

    private OrderItemService orderItemService;

    @Autowired
    public void setCustomerService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "Create a new order item record")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "order item created", content = { @Content(mediaType = "application/json")} ),
            @ApiResponse(responseCode = "404", description = "Bad request") })
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO response = orderItemService.save(customerDTO);
        return new ResponseEntity<CustomerDTO>( response, HttpStatus.OK );
    }
}