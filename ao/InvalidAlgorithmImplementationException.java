package ao;

public class InvalidAlgorithmImplementationException extends RuntimeException {
    private static final long serialVersionUID = 0L;
    
    public InvalidAlgorithmImplementationException() {
        super();
    }

    public InvalidAlgorithmImplementationException(String m) {
        super(m);
    }
}