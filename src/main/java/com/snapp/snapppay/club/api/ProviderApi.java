package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.AddScoreRequest;
import com.snapp.snapppay.club.domain.request.ProviderRegisterRequest;
import com.snapp.snapppay.club.service.provider.ProviderRegisterService;
import com.snapp.snapppay.club.service.score.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
@Tag(
        name = "Provider Api",
        description = "add , add score"
)
public class ProviderApi {

    private final ProviderRegisterService providerRegisterService;
    private final ScoreService scoreService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "add new provider",
            description = "creates a new provider (admin)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ProviderRegisterRequest for creating provider from it",
                    content = @Content(schema = @Schema(implementation = ProviderRegisterRequest.class))
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "provider created successfully",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing required fields in body or validations)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    public ResponseEntity<String> addProvider(@Valid @RequestBody ProviderRegisterRequest providerRegisterRequest) {
        providerRegisterService.register(providerRegisterRequest);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/addScore")
    @PreAuthorize("hasAuthority('PROVIDER')")
    @Operation(
            summary = "add score",
            description = "add score to user (provider)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "AddScoreRequest for creating score from it",
                    content = @Content(schema = @Schema(implementation = AddScoreRequest.class))
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "score added successfully",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing required fields in body or validations)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    public ResponseEntity<String> addScore(@Valid @RequestBody AddScoreRequest addScoreRequest) {
        scoreService.add(addScoreRequest);
        return ResponseEntity.ok("success");
    }


}
