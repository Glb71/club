package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.response.AdminProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface AdminProductResponseMapper {

    @Mapping(source = "insertUser", target = "creator", qualifiedByName = "userIdToUsername")
    @Mapping(source = "updateUser", target = "updater", qualifiedByName = "userIdToUsername")
    @Mapping(source = "insertDateTime", target = "creationTime")
    @Mapping(source = "updateDateTime", target = "lastUpdateTime")
    AdminProductResponse map(Product product);

}
