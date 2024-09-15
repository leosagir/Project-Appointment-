package project.appointment.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(Long id) {
        super("Service not found with id: " + id);
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }
}
