package fr.esgi.rent.dto.response;

import lombok.Builder;

@Builder
public record RentalPropertyResponseDto(String address,
                                        double area,
                                        String description,
                                        String propertyType,
                                        double rentAmount,
                                        double securityDepositAmount,
                                        String town) {
}