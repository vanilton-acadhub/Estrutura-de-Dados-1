package lista;

public class No<Item> {
    private Item item;
    private No<Item> proximo;

    public No() {
        this.setProximo(null);
    }

    public No(Item item) {
        this.setItem(item);
        this.setProximo(null);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public No<Item> getProximo() {
        return proximo;
    }

    public void setProximo(No<Item> proximo) {
        this.proximo = proximo;
    }
}
