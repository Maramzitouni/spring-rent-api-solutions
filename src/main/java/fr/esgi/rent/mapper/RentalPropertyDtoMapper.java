package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.EnergyClassificationEntity;
import fr.esgi.rent.domain.PropertyTypeEntity;
import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.dto.request.RentalPropertyRequestDto;
import fr.esgi.rent.dto.response.RentalPropertyResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalPropertyDtoMapper {

    public List<RentalPropertyResponseDto> mapToDtoList(List<RentalPropertyEntity> rentalProperties) {
        return rentalProperties.stream()
                .map(this::mapToDto)
                .toList();
    }

    public RentalPropertyResponseDto mapToDto(RentalPropertyEntity rentalProperty) {
        return new RentalPropertyResponseDto(
                rentalProperty.getAddress(),
                rentalProperty.getArea(),
                rentalProperty.getDescription(),
                rentalProperty.getPropertyType().getDesignation(),
                rentalProperty.getRentAmount(),
                rentalProperty.getSecurityDepositAmount(),
                rentalProperty.getTown());
    }

    public RentalPropertyEntity mapToEntity(RentalPropertyRequestDto rentalPropertyRequestDto) {
        return new RentalPropertyEntity(
                rentalPropertyRequestDto.description(),
                rentalPropertyRequestDto.town(),
                rentalPropertyRequestDto.address(),
                new PropertyTypeEntity(rentalPropertyRequestDto.propertyType()),
                rentalPropertyRequestDto.rentAmount(),
                rentalPropertyRequestDto.securityDepositAmount(),
                rentalPropertyRequestDto.area(),
                rentalPropertyRequestDto.numberOfBedrooms(),
                rentalPropertyRequestDto.floorNumber(),
                rentalPropertyRequestDto.numberOfFloors(),
                rentalPropertyRequestDto.constructionYear(),
                new EnergyClassificationEntity(rentalPropertyRequestDto.energyClassification()),
                rentalPropertyRequestDto.hasElevator(),
                rentalPropertyRequestDto.hasIntercom(),
                rentalPropertyRequestDto.hasBalcony(),
                rentalPropertyRequestDto.hasParkingSpace());
    }

    public void updateEntity(RentalPropertyRequestDto rentalPropertyRequestDto, RentalPropertyEntity rentalProperty) {
        rentalProperty.setDescription(rentalPropertyRequestDto.description());
        rentalProperty.setTown(rentalPropertyRequestDto.town());
        rentalProperty.setAddress(rentalPropertyRequestDto.address());
        rentalProperty.setPropertyType(new PropertyTypeEntity(rentalPropertyRequestDto.propertyType()));
        rentalProperty.setRentAmount(rentalPropertyRequestDto.rentAmount());
        rentalProperty.setSecurityDepositAmount(rentalPropertyRequestDto.securityDepositAmount());
        rentalProperty.setArea(rentalPropertyRequestDto.area());
        rentalProperty.setNumberOfBedrooms(rentalPropertyRequestDto.numberOfBedrooms());
        rentalProperty.setFloorNumber(rentalPropertyRequestDto.floorNumber());
        rentalProperty.setNumberOfFloors(rentalPropertyRequestDto.numberOfFloors()); // Assuming numberOfFloors was a typo
        rentalProperty.setConstructionYear(rentalPropertyRequestDto.constructionYear());
        rentalProperty.setEnergyClassification(new EnergyClassificationEntity(rentalPropertyRequestDto.energyClassification()));
        rentalProperty.setHasElevator(rentalPropertyRequestDto.hasElevator());
        rentalProperty.setHasIntercom(rentalPropertyRequestDto.hasIntercom());
        rentalProperty.setHasBalcony(rentalPropertyRequestDto.hasBalcony());
        rentalProperty.setHasParkingSpace(rentalPropertyRequestDto.hasParkingSpace());
    }


}
