package aa;

class InvalidSortAlgorithmImplementationException extends RuntimeException {
        private static final long serialVersionUID = 0L;
        
        public InvalidSortAlgorithmImplementationException() {
                super();
        }

        public InvalidSortAlgorithmImplementationException(String m) {
                super(m);
        }
}