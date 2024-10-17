package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.service.auth.LoginService;
import com.snapp.snapppay.club.service.user.UserRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication Api",
        description = "login and register new user"
)
public class AuthApi {

    private final LoginService loginService;
    private final UserRegisterService userRegisterService;

    @Operation(
            summary = "login end point",
            description = "Authenticates username and password and returns an access token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "LoginRequest for authenticate and create access token",
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "user logged in successfully",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "401", description = "invalid username and password",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing required fields in body)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

    @Operation(
            summary = "register new user end point",
            description = "registers a new user with default roles",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "UserRegisterRequest for adding new user",
                    content = @Content(schema = @Schema(implementation = UserRegisterRequest.class))
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "user registered successfully",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing required fields in body or validations)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userRegisterService.register(userRegisterRequest);
        return "success";
    }

}
