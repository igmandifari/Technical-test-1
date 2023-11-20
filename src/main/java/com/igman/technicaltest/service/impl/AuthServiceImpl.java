package com.igman.technicaltest.service.impl;
import com.igman.technicaltest.constant.ERole;
import com.igman.technicaltest.dto.request.AuthRequest;
import com.igman.technicaltest.dto.request.customer.CustomerRequest;
import com.igman.technicaltest.dto.response.LoginResponse;
import com.igman.technicaltest.dto.response.RegisterResponse;
import com.igman.technicaltest.entity.AppUser;
import com.igman.technicaltest.entity.Role;
import com.igman.technicaltest.entity.UserCredential;
import com.igman.technicaltest.repository.UserCredentialRepository;
import com.igman.technicaltest.security.JwtUtil;
import com.igman.technicaltest.service.AuthService;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.service.RoleService;
import com.igman.technicaltest.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
//    private final StoreService storeService;
//    private final AdminService adminService;
//    private final StoreService storeService;
//    private final VendorService vendorService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginResponse login(AuthRequest request) {
        validationUtil.validate(request);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername().toLowerCase(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // object AppUser
        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }

    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            validationUtil.validate(request);
            // role
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());

            // usercredential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roleId(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            // customer
            CustomerRequest customer = CustomerRequest.builder()
                    .userCredentialId(userCredential.getId())
                    .build();
            customerService.create(customer);

            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRoleId().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

}
