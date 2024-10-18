package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.response.UserResponse;
import com.snapp.snapppay.club.domain.response.UserScoreResponse;
import com.snapp.snapppay.club.mapper.UserResponseMapper;
import com.snapp.snapppay.club.mapper.UserScoreResponseMapper;
import com.snapp.snapppay.club.service.user.UserService;
import com.snapp.snapppay.club.service.userScore.UserScoreLoader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(
        name = "User Api",
        description = "search, score"
)

public class UserApi {

    private final UserService userService;
    private final UserScoreLoader userScoreLoader;
    private final UserResponseMapper userResponseMapper;
    private final UserScoreResponseMapper userScoreResponseMapper;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "search",
            description = "search for users (admin)"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "search successful",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing page)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    public ResponseEntity<Page<UserResponse>> search(
            @Parameter(description = "search parameter (username)")
            @RequestParam(value = "search", required = false) String search,
            @Valid PageRequest pageRequest) {
        Page<User> users = userService.search(search, pageRequest);
        return ResponseEntity.ok(users.map(userResponseMapper::map));
    }

    @GetMapping("/score")
    @Operation(
            summary = "get current user score",
            description = "gives current user score and if its not created returns a new one"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content = {@Content(mediaType = "text/plain")})
            }
    )
    public ResponseEntity<UserScoreResponse> getUserScore() {
        return ResponseEntity.ok(userScoreResponseMapper.map(userScoreLoader.current()));
    }
}
