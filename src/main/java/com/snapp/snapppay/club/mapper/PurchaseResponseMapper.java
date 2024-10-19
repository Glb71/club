package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.Purchase;
import com.snapp.snapppay.club.domain.response.PurchaseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseResponseMapper {

    @Mapping(source = "product.title", target = "product")
    @Mapping(source = "insertDateTime", target = "date")
    PurchaseResponse map(Purchase purchase);

}
