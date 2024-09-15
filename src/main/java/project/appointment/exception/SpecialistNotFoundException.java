package project.appointment.exception;

public class SpecialistNotFoundException extends RuntimeException {
  public SpecialistNotFoundException(Long id) {
    super("Specialist not found with id: " + id);
  }

  public SpecialistNotFoundException(String message) {
    super(message);
  }
}
