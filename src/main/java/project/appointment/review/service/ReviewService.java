package project.appointment.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.review.dto.ReviewCreateDto;
import project.appointment.review.dto.ReviewDto;
import project.appointment.review.dto.ReviewResponseDto;
import project.appointment.review.dto.ReviewUpdateDto;

import java.util.List;

@Service
public interface ReviewService {
    @Transactional
    ReviewDto createReview(ReviewCreateDto reviewCreateDto);

    @Transactional(readOnly = true)
    ReviewDto getReviewById(Long id);

    @Transactional(readOnly = true)
    List<ReviewDto> getAllReviews();

    @Transactional
    ReviewDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto);

    @Transactional
    void deleteReview(Long id);

    @Transactional(readOnly = true)
    ReviewResponseDto getReviewResponseById(Long id);

    @Transactional(readOnly = true)
    List<ReviewResponseDto> getAllReviewResponses();

    @Transactional(readOnly = true)
    List<ReviewResponseDto> getReviewsByClientId(Long clientId);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getAvailableAppointmentsForReview(Long clientId);
}
