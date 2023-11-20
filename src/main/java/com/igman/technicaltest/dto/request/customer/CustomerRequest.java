package com.igman.technicaltest.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String name;
    private String pin;
    private String address;
    private String phoneNumber;
    private String userCredentialId;
}
