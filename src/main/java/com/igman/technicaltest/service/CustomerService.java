package com.igman.technicaltest.service;

import com.igman.technicaltest.dto.request.customer.CustomerRequest;
import com.igman.technicaltest.dto.request.customer.SearchCustomerRequest;
import com.igman.technicaltest.dto.request.customer.UpdateCustomerRequest;
import com.igman.technicaltest.dto.response.CustomerResponse;
import com.igman.technicaltest.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
 CustomerResponse create(CustomerRequest request);
 Page<CustomerResponse> getAll(SearchCustomerRequest request);
 Customer getById(String id);

 CustomerResponse update(UpdateCustomerRequest request);

 void updateCustomer(Customer customer);
}
