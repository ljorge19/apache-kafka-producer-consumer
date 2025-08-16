package com.kafka.producer.api.mapper;

import com.kafka.producer.api.dto.CustomerDTO;
import com.kafka.producer.api.dto.ShippingAddressDTO;
import com.kafka.producer.api.model.Customer;
import com.kafka.producer.api.model.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "shippingAddress", target = "shippingAddress")
    CustomerDTO toCustomerDTO(Customer customer);

    @Mapping(source = "shippingAddress", target = "shippingAddress")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    Iterable<CustomerDTO> customersToCustomerAllDtos(Iterable<Customer> customers);

    // Métodos auxiliares para ShippingAddress ↔ ShippingAddressDTO
    ShippingAddressDTO shippingAddressToShippingAddressDTO(ShippingAddress shippingAddress);

    ShippingAddress shippingAddressDTOToShippingAddress(ShippingAddressDTO shippingAddressDTO);
}
