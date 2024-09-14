package project.appointment;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.entity.Client;
import project.appointment.services.dto.SServiceResponseDto;
import project.appointment.services.entity.SService;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.entity.Specialist;

import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configureCommonMappings(modelMapper);
        configureSpecialistMapping(modelMapper);
        configureClientMapping(modelMapper);

        return modelMapper;
    }

    private void configureCommonMappings(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    private void configureSpecialistMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Specialist.class, SpecialistResponseDto.class)
                .addMappings(mapper -> mapper.map(src -> Optional.ofNullable(src.getServices())
                        .map(services -> services.stream()
                                .map(service -> convertToDto(service, SServiceResponseDto.class, modelMapper))
                                .collect(Collectors.toSet()))
                        .orElse(null), SpecialistResponseDto::setServices));

        modelMapper.createTypeMap(SpecialistRequestDto.class, Specialist.class)
                .addMappings(mapper -> mapper.map(src -> Optional.ofNullable(src.getServices())
                        .map(services -> services.stream()
                                .map(serviceDto -> convertToEntity(serviceDto, SService.class, modelMapper))
                                .collect(Collectors.toSet()))
                        .orElse(null), Specialist::setServices));
    }

    private void configureClientMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(Client.class, ClientResponseDto.class);

        modelMapper.typeMap(ClientRequestDto.class, Client.class)
                .addMappings(mapper -> {
                    mapper.skip(Client::setId);
                    mapper.skip(Client::setCreatedAt);
                    mapper.skip(Client::setStatus);
                });

        modelMapper.typeMap(ClientUpdateDto.class, Client.class)
                .addMappings(mapper -> {
                    mapper.skip(Client::setId);
                    mapper.skip(Client::setEmail);
                    mapper.skip(Client::setPassword);
                    mapper.skip(Client::setStatus);
                    mapper.skip(Client::setCreatedAt);
                });
    }

    private <D, E> D convertToDto(E entity, Class<D> dtoClass, ModelMapper modelMapper) {
        return entity != null ? modelMapper.map(entity, dtoClass) : null;
    }

    private <D, E> E convertToEntity(D dto, Class<E> entityClass, ModelMapper modelMapper) {
        return dto != null ? modelMapper.map(dto, entityClass) : null;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}