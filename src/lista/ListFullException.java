package lista;

public class ListFullException extends RuntimeException {

    @Override
    public String toString() {
        return "ListFullException ~> list are full\n";
    }
}
