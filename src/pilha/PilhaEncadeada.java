package pilha;

import lista.ListaIterator;
import lista.ListaSEncadeada;

public class PilhaEncadeada<Item extends Comparable<Item>> extends ListaSEncadeada<Item>
        implements I2Pilha<Item> {
    @Override
    public void push(Item item) {
        inserir(item);
    }

    @Override
    public Item pop() {
        Item itemRetirado = obter(getTamanho() - 1);
        remover(getTamanho() - 1);
        return itemRetirado;
    }

    @Override
    public Item top() {
        return obter(getTamanho() - 1);
    }

    @Override
    public void pull(Item item) {
        replace(getTamanho() - 1, item);
    }

    @Override
    public String toString() {
        ListaSEncadeada<Item> invertida = (ListaSEncadeada<Item>) cloneLista(this);
        ListaIterator<Item> iterator = invertida.inverter().iterator();
        StringBuilder lista = new StringBuilder("[");
        int cont = 0;
        while (iterator.hasNext()) {
            Item item = iterator.next();
            lista.append(item);
            cont++;
            if (cont != this.getTamanho()) {
                lista.append(", ");
            }
        }

        lista.append("]");
        return lista.toString();
    }
}
