package project.appointment.exception;

public class SpecializationNotFoundException extends RuntimeException {
  public SpecializationNotFoundException(Long id) {
    super("Specialization not found with id: " + id);
  }

  public SpecializationNotFoundException(String message) {
    super(message);
  }
}
