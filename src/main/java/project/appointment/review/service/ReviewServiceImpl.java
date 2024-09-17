package project.appointment.review.service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.appointment.review.dto.*;
import project.appointment.review.entity.Review;
import project.appointment.review.ReviewMapper;
import project.appointment.review.repository.ReviewRepository;
import project.appointment.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final Validator validator;

    @Transactional
    @Override
    public ReviewDto createReview(ReviewCreateDto reviewCreateDto) {
        logger.info("Creating new review");
        Review review = reviewMapper.toEntity(reviewCreateDto);
        validateReview(review);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewDto getReviewById(Long id) {
        logger.info("Fetching review with id: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewMapper.toDto(review);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewDto> getAllReviews() {
        logger.info("Fetching all reviews");
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReviewDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto) {
        logger.info("Updating review with id: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        reviewMapper.updateReviewFromDto(reviewUpdateDto, review);
        validateReview(review);
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toDto(updatedReview);
    }

    @Transactional
    @Override
    public void deleteReview(Long id) {
        logger.info("Deleting review with id: {}", id);
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewResponseDto getReviewResponseById(Long id) {
        logger.info("Fetching review response with id: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewMapper.toResponseDto(review);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewResponseDto> getAllReviewResponses() {
        logger.info("Fetching all review responses");
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private void validateReview(Review review) {
        var violations = validator.validate(review);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid review: " + violations);
        }
    }
}
