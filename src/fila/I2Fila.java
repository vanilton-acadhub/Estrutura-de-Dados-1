package fila;

public interface I2Fila<Item> {
    void enqueue(Item item);
    Item dequeue();
    Item peek();
    boolean isVazia();
}
