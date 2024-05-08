package com.peak.security;


import com.peak.Util.Role;
import com.peak.model.Customer;
import com.peak.repository.CustomerRepository;
import com.peak.security.model.RegisterRequest;
import com.peak.security.model.AuthenticationRequest;
import com.peak.security.model.AuthenticationResponse;
import com.peak.security.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Response register(RegisterRequest request) {
        var foundcustomer = customerRepository.findByEmail(request.getEmail());
        if (foundcustomer.isPresent()) return Response.builder().Body("Email already register").build();

        var customer = new Customer(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.USER);


        customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        return new AuthenticationResponse(jwtToken);
    }

    public Response authenticate(AuthenticationRequest request) {
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
