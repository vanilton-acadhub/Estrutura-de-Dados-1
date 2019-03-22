package pilha;

import lista.Vetor;

public class Pilha<Item extends Comparable> extends Vetor<Item> implements I2Pilha<Item> {
    @Override
    public void push(Item item) {
        if (this.getTamanho() == this.getCapacidadeMaxima()) {
            throw new RuntimeException("~> Pilha cheia");
        }
        inserir(item);
    }

    @Override
    public Item pop() {
        Item itemRetirado = obter(this.getTamanho() - 1); // obtemos do topo
        remover(this.getTamanho() - 1);
        return itemRetirado;
    }

    @Override
    public Item top() {
        return obter(this.getTamanho() - 1);
    }

    @Override
    public void pull(Item item) {
        replace(getTamanho() - 1, item);
    }

    // Embora o método abaixo já se encontre implementado, cuidamos de realizar novamente para fins ilustrativos;
    // Claramente o correto não seria isso.
    @Override
    public boolean isVazia() {
        return this.getTamanho() == 0;
    }

    @Override
    public String toString() {
        StringBuilder lista = new StringBuilder("[");
        int indice = getTamanho() - 1;
        while (indice > 0) {
            lista.append(obter(indice));
            lista.append(", ");
            indice--;
        }

        lista.append(obter(indice));

        lista.append("]");
        return lista.toString();
    }
}

