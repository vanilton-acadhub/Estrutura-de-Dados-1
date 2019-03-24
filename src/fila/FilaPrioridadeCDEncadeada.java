package fila;

import lista.ListaDuplamenteCircular;
import lista.ListaIterator;
import lista.NoDuplo;

public class FilaPrioridadeCDEncadeada<Item extends Comparable<Item>> extends ListaDuplamenteCircular<Item>
        implements I2Fila<Item> {

    private FilaDePrioridade.TipoPrioridade tipoPrioridade;

    public FilaPrioridadeCDEncadeada() {
        setTipoPrioridade(FilaDePrioridade.TipoPrioridade.ALTA);
    }

    public FilaPrioridadeCDEncadeada(FilaDePrioridade.TipoPrioridade tipoPrioridade) {
        setTipoPrioridade(tipoPrioridade);
    }

    public FilaDePrioridade.TipoPrioridade getTipoPrioridade() {
        return tipoPrioridade;
    }

    public void setTipoPrioridade(FilaDePrioridade.TipoPrioridade tipoPrioridade) {
        if (getTamanho() > 0) {
            ListaDuplamenteCircular<Item> listaDuplamenteCircular = (ListaDuplamenteCircular<Item>) cloneLista(this);
            limpar();
            this.tipoPrioridade = tipoPrioridade;
            ListaIterator<Item> iterator = listaDuplamenteCircular.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                enqueue(item);
            }
        }

        this.tipoPrioridade = tipoPrioridade;
    }


    @Override
    public void inserirOrdenado(Item item) {
        NoDuplo<Item> novoNo = new NoDuplo<>(item);
        NoDuplo<Item> atual = this.getInicio();

        if (atual == null) {
            setInicio(novoNo);
            setUltimo(novoNo);

            getInicio().setAnterior(getUltimo());
            getUltimo().setProximo(getInicio());

        } else if (atual.getItem().compareTo(item) <= 0) {
            novoNo.setProximo(atual);
            novoNo.getProximo().setAnterior(novoNo);
            setInicio(novoNo);

            getInicio().setAnterior(getUltimo());
            getUltimo().setProximo(getInicio());
        } else {
            while (atual.getProximo() != getInicio() &&
                    atual.getProximo().getItem().compareTo(item) > 0) {
                atual = atual.getProximo(); // 3 4 7 8 9
            }

            novoNo.setAnterior(atual);
            novoNo.setProximo(atual.getProximo());
            atual.getProximo().setAnterior(novoNo);
            atual.setProximo(novoNo);

            if (novoNo.getProximo() == getInicio()) {
                setUltimo(novoNo);
            }
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
