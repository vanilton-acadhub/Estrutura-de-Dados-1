package fila;

import lista.ListaDuplamenteCircular;

public class FilaCircularDEncadeada<Item extends Comparable<Item>> extends ListaDuplamenteCircular<Item>
        implements I2Fila<Item> {
    @Override
    public void enqueue(Item item) {
        inserir(item);
    }

    @Override
    public Item dequeue() {
        Item itemDesenfileirado = obter(0);
        remover(0);
        return itemDesenfileirado;
    }

    @Override
    public Item peek() {
        return obter(0);
    }
}
