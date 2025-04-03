package com.neeko.cqrs.product.query.dto.response;

import com.neeko.cqrs.common.dto.Pagination;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductListResponse {
    private final List<ProductDto> products;
    private final Pagination pagination;
}
