package project.appointment.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.review.entity.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
