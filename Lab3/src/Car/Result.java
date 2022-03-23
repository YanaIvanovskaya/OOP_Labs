package Car;

interface Result {

    final class Success implements Result {
        String message;

        Success(String message) {
            this.message = message;
        }
    }

    class Failure implements Result {
        String message;

        Failure(String message) {
            this.message = message;
        }
    }
}
