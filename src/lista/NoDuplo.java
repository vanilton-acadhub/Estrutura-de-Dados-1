package lista;

public class NoDuplo<Item> {
    private NoDuplo<Item> anterior;
    private NoDuplo<Item> proximo;
    private Item item;

    public NoDuplo(Item item) {
        setAnterior(null);
        setItem(item);
        setProximo(null);
    }

    public NoDuplo<Item> getAnterior() {
        return anterior;
    }

    public void setAnterior(NoDuplo<Item> anterior) {
        this.anterior = anterior;
    }

    public NoDuplo<Item> getProximo() {
        return proximo;
    }

    public void setProximo(NoDuplo<Item> proximo) {
        this.proximo = proximo;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
