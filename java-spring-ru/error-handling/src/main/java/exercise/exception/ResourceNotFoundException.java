package exercise.exception;

// BEGIN
public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(Long id) {
        super(String.format("Product with id %d not found", id));
    }
}
// END
