package com.peak.security.service;


import com.peak.Util.Role;
import com.peak.main.model.Customer;
import com.peak.main.repository.CustomerRepository;
import com.peak.security.model.RegisterRequest;
import com.peak.security.model.AuthenticationRequest;
import com.peak.security.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${ADMIN_KEY}")
    private String ADMIN_KEY;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Object register(RegisterRequest request) {
        var foundcustomer = customerRepository.findByEmail(request.getEmail());
        if (foundcustomer.isPresent()) return "Email already register";

        Customer customer = new Customer(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.USER);
        if (request.getKey() != null && request.getKey().equals(ADMIN_KEY)) customer.setRole(Role.ADMIN);


        customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        return new AuthenticationResponse(jwtToken);
    }

    public Object authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = customerRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
