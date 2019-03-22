package fila;

import lista.ListaIterator;
import lista.ListaSEncadeada;
import lista.No;

/* Ao utilizar o construtor padrão teremos uma fila, por default, de alta prioridade. */
public class FilaDePrioridade<Item extends Comparable<Item>> extends ListaSEncadeada<Item>
        implements I2Fila<Item> {
    enum TipoPrioridade {
        BAIXA,
        ALTA
    }

    private TipoPrioridade tipoPrioridade;

    public FilaDePrioridade() {
        setTipoPrioridade(TipoPrioridade.ALTA);
    }

    public FilaDePrioridade(TipoPrioridade tipoPrioridade) {
        setTipoPrioridade(tipoPrioridade);
    }

    public TipoPrioridade getTipoPrioridade() {
        return tipoPrioridade;
    }

    public void setTipoPrioridade(TipoPrioridade tipoPrioridade) {
        if (getTamanho() > 0) {
            ListaSEncadeada<Item> listaSEncadeada = (ListaSEncadeada<Item>) cloneList(this);
            limpar();
            this.tipoPrioridade = tipoPrioridade;
            ListaIterator<Item> iterator = listaSEncadeada.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                enqueue(item);
            }
        }

        this.tipoPrioridade = tipoPrioridade;
    }

    @Override
    public void inserirOrdenado(Item item) {
        No<Item> novoNo = new No<>(item);
        No<Item> atual = this.getInicio();

        if (atual == null || this.getInicio().getItem().compareTo(item) <= 0) {
            novoNo.setProximo(this.getInicio());
            this.setInicio(novoNo);
        } else {
            while (atual.getProximo() != null &&
                    atual.getProximo().getItem().compareTo(item) > 0) {
                atual = atual.getProximo();
            }
            novoNo.setProximo(atual.getProximo());
            atual.setProximo(novoNo);
        }

        setTamanho(getTamanho() + 1);
    }

    @Override
    public void enqueue(Item item) {
        if (getTipoPrioridade() == TipoPrioridade.ALTA) {
            this.inserirOrdenado(item);
        } else {
            super.inserirOrdenado(item);
        }
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
