package fila;

import lista.ListaSEncadeada;

public class FilaSimplesmenteEncadeada<Item extends Comparable<Item>> extends ListaSEncadeada<Item>
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

    // O método isVazia já está implementado
}
