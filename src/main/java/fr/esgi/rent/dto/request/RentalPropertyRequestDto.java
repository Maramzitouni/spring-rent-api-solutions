package fr.esgi.rent.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;

@Builder
public record RentalPropertyRequestDto(
        @Size(min = 1)
        @NotNull String description,
        @Size(min = 1)
        @NotNull String town,
        @Size(min = 1)
        @NotNull String address,
        @Size(min = 1)
        @NotNull String propertyType,
        @NotNull @Min(1) double rentAmount,
        @NotNull @Min(1) double securityDepositAmount,
        @NotNull @Min(1) double area,
        @Min(1) int numberOfBedrooms,
        int floorNumber,
        int numberOfFloors,
        @Min(1900) int constructionYear,
        @NotNull String energyClassification,
        @NotNull Boolean hasElevator,
        @NotNull Boolean hasIntercom,
        @NotNull Boolean hasBalcony,
        @NotNull Boolean hasParkingSpace) {

}
