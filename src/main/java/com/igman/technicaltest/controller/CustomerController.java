package com.igman.technicaltest.controller;

import com.igman.technicaltest.dto.request.customer.CustomerRequest;
import com.igman.technicaltest.dto.request.customer.SearchCustomerRequest;
import com.igman.technicaltest.dto.request.customer.UpdateCustomerRequest;
import com.igman.technicaltest.dto.response.CommonResponse;
import com.igman.technicaltest.dto.response.CustomerResponse;
import com.igman.technicaltest.dto.response.PagingResponse;
import com.igman.technicaltest.entity.Customer;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new customer")
                        .data(customerResponse)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);

        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .build();
        Page<CustomerResponse> customers = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("successfully get all customer")
                .statusCode(HttpStatus.OK.value())
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable String customerId) {
        Customer customer = customerService.getById(customerId);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully update customer")
                .statusCode(HttpStatus.OK.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
