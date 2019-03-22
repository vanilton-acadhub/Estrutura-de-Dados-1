package fila;

import lista.ListaCircular;
import lista.ListaIterator;
import lista.No;

public class FilaDePrioridadeCircular<Item extends Comparable<Item>> extends ListaCircular<Item>
        implements I2Fila<Item> {

    private FilaDePrioridade.TipoPrioridade tipoPrioridade;

    public FilaDePrioridadeCircular() {
        setTipoPrioridade(FilaDePrioridade.TipoPrioridade.ALTA);
    }

    public FilaDePrioridadeCircular(FilaDePrioridade.TipoPrioridade tipoPrioridade) {
        setTipoPrioridade(tipoPrioridade);
    }

    public FilaDePrioridade.TipoPrioridade getTipoPrioridade() {
        return tipoPrioridade;
    }

    public void setTipoPrioridade(FilaDePrioridade.TipoPrioridade tipoPrioridade) {
        if (getTamanho() > 0) {
            ListaCircular<Item> listaSEncadeada = (ListaCircular<Item>) cloneList(this);
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
        No<Item> noAtual = this.getInicio();
        if (noAtual == null) {
            novoNo.setProximo(novoNo);
            this.setInicio(novoNo);
        } else if (noAtual.getItem().compareTo(novoNo.getItem()) >= 0) {
            while (noAtual.getProximo() != this.getInicio()) {
                noAtual = noAtual.getProximo();
            }
            noAtual.setProximo(novoNo);
            novoNo.setProximo(this.getInicio());
            this.setInicio(novoNo);
        } else {
            while (noAtual.getProximo() != this.getInicio() &&
                    noAtual.getProximo().getItem().compareTo(novoNo.getItem()) < 0) {
                noAtual = noAtual.getProximo();
            }
            novoNo.setProximo(noAtual.getProximo());
            noAtual.setProximo(novoNo);
        }

        this.setTamanho(this.getTamanho() + 1);
    }

    @Override
    public void enqueue(Item item) {
        if (getTipoPrioridade() == FilaDePrioridade.TipoPrioridade.ALTA) {
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
