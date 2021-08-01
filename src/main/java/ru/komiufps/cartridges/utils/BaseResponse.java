package ru.komiufps.cartridges.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private String message;
}
