package project.appointment.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.review.dto.*;
import project.appointment.review.service.ReviewService;
import project.appointment.security.AppUser;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController_new {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewCreateDto reviewCreateDto,
                                                  @AuthenticationPrincipal AppUser currentUser) {
        reviewCreateDto.setClientId(currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMINISTRATOR')")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id,
                                                  @RequestBody ReviewUpdateDto reviewUpdateDto,
                                                  @AuthenticationPrincipal AppUser currentUser) {
        ReviewDto existingReview = reviewService.getReviewById(id);
        if (currentUser.getRole().equals("CLIENT") && !existingReview.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(reviewService.updateReview(id, reviewUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMINISTRATOR')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             @AuthenticationPrincipal AppUser currentUser) {
        ReviewDto existingReview = reviewService.getReviewById(id);
        if (currentUser.getRole().equals("CLIENT") && !existingReview.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewResponseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviewResponses());
    }

    @GetMapping("/client")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReviewResponseDto>> getCurrentClientReviews(@AuthenticationPrincipal AppUser currentUser) {
        return ResponseEntity.ok(reviewService.getReviewsByClientId(currentUser.getId()));
    }

    @GetMapping("/available-appointments")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<AppointmentResponseDto>> getAvailableAppointmentsForReview(@AuthenticationPrincipal AppUser currentUser) {
        return ResponseEntity.ok(reviewService.getAvailableAppointmentsForReview(currentUser.getId()));
    }
}
