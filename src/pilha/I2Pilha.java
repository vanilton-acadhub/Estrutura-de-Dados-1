package pilha;

public interface I2Pilha<Item> {

    void push(Item item);
    Item pop();
    Item top();
    void pull(Item item);
    boolean isVazia();
}
