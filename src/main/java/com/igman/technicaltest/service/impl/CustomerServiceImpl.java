package com.igman.technicaltest.service.impl;

import com.igman.technicaltest.dto.request.customer.CustomerRequest;
import com.igman.technicaltest.dto.request.customer.SearchCustomerRequest;
import com.igman.technicaltest.dto.response.CustomerResponse;
import com.igman.technicaltest.entity.Customer;
import com.igman.technicaltest.entity.UserCredential;
import com.igman.technicaltest.repository.CustomerRepository;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    @Override
    public CustomerResponse create(CustomerRequest request) {
        try {
            validationUtil.validate(request);
            UserCredential userCredential = UserCredential.builder().
                    id(request.getUserCredentialId())
                    .build();
            Customer customer = Customer.builder()
                    .name(request.getName())
                    .pin(request.getPin())
                    .address(request.getAddress())
                    .phoneNumber(request.getPhoneNumber())
                    .userCredential(userCredential)
                    .build();
            customerRepository.saveAndFlush(customer);
            return mapToResponse(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAll(SearchCustomerRequest request) {
        for (Field declaredField : Customer.class.getDeclaredFields()) {
            if (!declaredField.getName().equals(request.getSortBy())) {
                request.setSortBy("name");
            }
        }

        Sort.Direction direction = Sort.Direction.fromString(request.getDirection());
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                direction,
                request.getSortBy()
        );

        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> mapToResponse(customer));
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.getAccount();
                    return customer;
                })
                .orElse(null);
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .pin(customer.getPin())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
