package com.gbg.userservice.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private UUID userId;
    private String username;
    private List<OrderResponse> orders;


}
