package lista;

public class ListaDuplamenteCircular<Item extends Comparable<Item>>
        extends ListaDuplamenteEncadeada<Item> {

    @Override
    public void inserirInicio(Item item) {
        NoDuplo<Item> novoNo = new NoDuplo<>(item);
        if (getInicio() == null) {
            novoNo.setAnterior(novoNo);
            novoNo.setProximo(novoNo);
            setInicio(novoNo);
            setUltimo(getInicio());
        } else {
            novoNo.setAnterior(getUltimo());
            getUltimo().setProximo(novoNo);
            getInicio().setAnterior(novoNo);
            novoNo.setProximo(getInicio());
            setInicio(novoNo);
        }
        setTamanho(this.getTamanho() + 1);
    }

    @Override
    public void inserir(Item item) {
        NoDuplo<Item> novoNo = new NoDuplo<>(item);
        if (getInicio() == null) {
            novoNo.setProximo(novoNo);
            novoNo.setAnterior(novoNo);
            setInicio(novoNo);
            setUltimo(getInicio());
        } else {
            novoNo.setAnterior(getUltimo());
            getUltimo().setProximo(novoNo);
            getInicio().setAnterior(novoNo);
            novoNo.setProximo(getInicio());
            setUltimo(novoNo);
        }
        setTamanho(this.getTamanho() + 1);
    }

    @Override
    public void inserir(int posicao, Item item) {
        if (!(posicao >= 0 && posicao <= getTamanho())) {
            throw new RuntimeException("~> Posição inexistente");
        }

        if (posicao == 0) {
            this.inserirInicio(item);
        } else if (posicao == this.getTamanho()) {
            this.inserir(item);
        } else {
            NoDuplo<Item> novoNo = new NoDuplo<>(item);
            NoDuplo<Item> atual = this.pegaNo(posicao);
            NoDuplo<Item> anterior = atual.getAnterior();

            novoNo.setAnterior(anterior);
            novoNo.setProximo(atual);
            atual.setAnterior(novoNo);
            anterior.setProximo(novoNo);
            setTamanho(this.getTamanho() + 1);
        }
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

        } else if (atual.getItem().compareTo(item) >= 0) {
            novoNo.setProximo(atual);
            novoNo.getProximo().setAnterior(novoNo);
            setInicio(novoNo);

            getInicio().setAnterior(getUltimo());
            getUltimo().setProximo(getInicio());
        } else {
            while (atual.getProximo() != getInicio() &&
                    atual.getProximo().getItem().compareTo(item) < 0) {
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
    public void remover(int posicao) {
        if (!(posicao >= 0 && posicao < getTamanho())) {
            throw new RuntimeException("~> Posição inexistente");
        }

        if (posicao == 0 && this.getTamanho() == 1) {
            setInicio(null);
            setUltimo(null);
            setTamanho(0);
        }

        // remover do inicio
        if (posicao == 0) {
            setInicio(getInicio().getProximo());
            getInicio().setAnterior(getUltimo());
            getUltimo().setProximo(getInicio());
            setTamanho(this.getTamanho() - 1);
        } else if (posicao == getTamanho() - 1) { // remover do final
            setUltimo(getUltimo().getAnterior());
            getUltimo().setProximo(getInicio());
            getInicio().setAnterior(getUltimo());
            setTamanho(this.getTamanho() - 1);
        } else {
            NoDuplo<Item> atual = this.pegaNo(posicao);
            NoDuplo<Item> anterior = atual.getAnterior();
            NoDuplo<Item> proximo = atual.getProximo();
            anterior.setProximo(proximo);
            proximo.setAnterior(anterior);
            setTamanho(this.getTamanho() - 1);
        }
    }

    @Override
    public I2Lista<Item> cloneList(I2Lista<Item> lista) {
        ListaIterator<Item> iterator = lista.iterator();

        I2Lista<Item> novaLista = new ListaDuplamenteCircular<>();
        while (iterator.hasNext()) {
            novaLista.inserir(iterator.next());
        }
        return novaLista;
    }

    @Override
    public ListaIterator iterator() {
        return new ListaIterator<Item>() {
            private NoDuplo<Item> ponteiro = getInicio();
            private int cont = 0;
            @Override
            public Item next() {
                Item item = ponteiro.getItem();
                ponteiro = ponteiro.getProximo();
                cont++;
                return item;
            }

            @Override
            public boolean hasNext() {
                return cont != getTamanho();
            }
        };
    }

    @Override
    public I2Lista<Item> union(I2Lista<Item> outraLista) {
        I2Lista<Item> lista1 = cloneList(this);
        I2Lista<Item> lista2 = cloneList(outraLista);
        I2Lista<Item> uniao = new ListaDuplamenteCircular<>();

        for (int indiceLista1 = 0; indiceLista1 < lista1.getTamanho(); indiceLista1++) {
            if (!find(lista1.obter(indiceLista1), uniao)) {
                uniao.inserir(lista1.obter(indiceLista1));
            }
        }

        for (int indiceLista2 = 0; indiceLista2 < lista2.getTamanho(); indiceLista2++) {
            if (!find(lista2.obter(indiceLista2), uniao)) {
                uniao.inserir(lista2.obter(indiceLista2));
            }
        }
        return uniao;
    }

    @Override
    public I2Lista<Item> intersection(I2Lista<Item> outraLista) {
        I2Lista<Item> lista1 = cloneList(this);
        I2Lista<Item> lista2 = cloneList(outraLista);
        I2Lista<Item> interseccao = new ListaDuplamenteCircular<>();

        for (int indiceLista1 = 0; indiceLista1 < lista1.getTamanho(); indiceLista1++) {
            Item item = lista1.obter(indiceLista1);
            for (int indiceLista2 = 0; indiceLista2 < lista2.getTamanho(); indiceLista2++) {
                if (find(item, lista2) && !(find(item, interseccao))) {
                    interseccao.inserir(item);
                }
            }
        }
        return interseccao;
    }

    @Override
    public I2Lista<Item> diference(I2Lista<Item> outraLista) {
        I2Lista<Item> lista1 = cloneList(this);
        I2Lista<Item> lista2 = cloneList(outraLista);
        I2Lista<Item> diferenca = new ListaDuplamenteCircular<>();

        for (int indiceLista1 = 0; indiceLista1 < lista1.getTamanho(); indiceLista1++) {
            Item item = lista1.obter(indiceLista1);
            for (int indiceLista2 = 0; indiceLista2 < lista2.getTamanho(); indiceLista2++) {
                if (!find(item, lista2) && !find(item, diferenca)) {
                    diferenca.inserir(item);
                }
            }
        }
        return diferenca;
    }

    protected boolean find(Item item, I2Lista<Item> lista) {
        ListaDuplamenteEncadeada<Item> listaVetor = (ListaDuplamenteCircular<Item>) lista;
        ListaIterator<Item> iterator = listaVetor.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }

        return false;
    }

    public I2Lista<Item> inverter() {
        I2Lista<Item> referencia = this.cloneList(this);
        ListaIterator<Item> iterator = referencia.iterator();
        I2Lista<Item> listaInvertida = new ListaDuplamenteCircular<>();
        while (iterator.hasNext()) {
            listaInvertida.inserirInicio(iterator.next());
        }

        return listaInvertida;
    }

    @Override
    public String toString() {
        ListaIterator<Item> iterator = iterator();
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
