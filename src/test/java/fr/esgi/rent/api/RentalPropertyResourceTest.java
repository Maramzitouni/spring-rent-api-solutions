package fr.esgi.rent.api;

import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.dto.request.RentalPropertyRequestDto;
import fr.esgi.rent.dto.response.RentalPropertyResponseDto;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.repository.RentalPropertyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.esgi.rent.samples.RentalPropertyDtoSample.*;
import static fr.esgi.rent.samples.RentalPropertyEntitySample.oneRentalPropertyEntity;
import static fr.esgi.rent.samples.RentalPropertyEntitySample.rentalPropertyEntities;
import static fr.esgi.rent.utils.TestUtils.readResource;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentalPropertyResource.class)
class RentalPropertyResourceTest {

    @Value("classpath:/json/rentalProperties.json")
    private Resource rentalProperties;

    @Value("classpath:/json/rentalProperty.json")
    private Resource rentalProperty;

    @Value("classpath:/json/rentalPropertyRequest.json")
    private Resource rentalPropertyRequest;

    @Value("classpath:/json/invalidRentalPropertyRequest.json")
    private Resource invalidRentalPropertyRequest;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalPropertyRepository rentalPropertyRepository;

    @MockBean
    private RentalPropertyDtoMapper rentalPropertyDtoMapper;

    @Test
    void shouldGetRentalProperties() throws Exception {
        List<RentalPropertyEntity> rentalPropertyEntities = rentalPropertyEntities();
        List<RentalPropertyResponseDto> rentalPropertyResponseList = rentalPropertyResponseList();

        when(rentalPropertyRepository.findAll()).thenReturn(rentalPropertyEntities);
        when(rentalPropertyDtoMapper.mapToDtoList(rentalPropertyEntities)).thenReturn(rentalPropertyResponseList);

        mockMvc.perform(get("/rent-properties-api/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(content().json(readResource(rentalProperties)));

        verify(rentalPropertyRepository).findAll();
        verify(rentalPropertyDtoMapper).mapToDtoList(rentalPropertyEntities);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void shouldGetRentalPropertyById() throws Exception {
        RentalPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();
        RentalPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();

        String id = "1a8ed763-928c-4155-bee9-fdbaaadc15f3";

        when(rentalPropertyRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(rentalPropertyEntity));
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(get("/rent-properties-api/rental-properties/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(readResource(rentalProperty)));

        verify(rentalPropertyRepository).findById(UUID.fromString(id));
        verify(rentalPropertyDtoMapper).mapToDto(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void givenNoExistentRentalPropertyId_shouldThrowNotFoundRentalPropertyException() throws Exception {
        String id = "1a8ed763-928c-4155-bee9-fdbaaadc15f3";

        when(rentalPropertyRepository.findById(UUID.fromString(id))).thenReturn(Optional.empty());

        mockMvc.perform(get("/rent-properties-api/rental-properties/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Le bien immobilier " + id + " est introuvable\"}"));

        verify(rentalPropertyRepository).findById(UUID.fromString(id));
        verifyNoInteractions(rentalPropertyDtoMapper);
        verifyNoMoreInteractions(rentalPropertyRepository);
    }

//    @Test
//    void shouldCreateRentalProperty() throws Exception {
//        RentalPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequest();
//        RentalPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();
//        RentalPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();
//
//        when(rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto)).thenReturn(rentalPropertyEntity);
//        when(rentalPropertyRepository.save(rentalPropertyEntity)).thenReturn(rentalPropertyEntity);
//        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);
//
//        mockMvc.perform(post("/rent-properties-api/rental-properties")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(readResource(rentalPropertyRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(readResource(rentalProperty)));
//
//        verify(rentalPropertyDtoMapper).mapToEntity(rentalPropertyRequestDto);
//        verify(rentalPropertyRepository).save(rentalPropertyEntity);
//        verify(rentalPropertyDtoMapper).mapToDto(rentalPropertyEntity);
//        verifyNoMoreInteractions(rentalPropertyDtoMapper, rentalPropertyRepository);
//    }

    @Test
    void givenInvalidRequestBody_shouldReturn404HttpStatusCode() throws Exception {
        mockMvc.perform(post("/rent-properties-api/rental-properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(readResource(invalidRentalPropertyRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"La requête envoyée est invalide\"}"));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyRepository);
    }

}
