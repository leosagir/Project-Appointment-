package project.appointment.specialist.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.review.dto.ReviewDto;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpecialistDetailedDto extends SpecialistResponseDto {
   private List<AppointmentResponseDto> appointments;
   private List<ReviewDto> reviews;
}
