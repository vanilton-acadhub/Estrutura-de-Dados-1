package lista;

public class ListaCircular<Item extends Comparable> extends ListaSEncadeada<Item> {
    @Override
    public void inserirInicio(Item item) {
        No<Item> novoNo = new No<>(item);
        if (getInicio() == null) {
            setInicio(novoNo);
            novoNo.setProximo(getInicio());
        } else {
            No<Item> temporario = pegaNo(this.getTamanho() - 1);
            novoNo.setProximo(getInicio());
            setInicio(novoNo);
            temporario.setProximo(getInicio());
        }
        setTamanho(this.getTamanho() + 1);
    }

    @Override
    public void inserir(Item item) {
        No<Item> novoNo = new No<>(item);
        if (getInicio() == null) {
            setInicio(novoNo);
            novoNo.setProximo(getInicio());
        } else {
            No<Item> temporario = pegaNo(this.getTamanho() - 1);
            temporario.setProximo(novoNo);
            novoNo.setProximo(getInicio());
        }
        this.setTamanho(this.getTamanho() + 1);
    }

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
    public void removerInicio() {
        if (getInicio() == null) {
            throw new RuntimeException("~> A lista se encontra vazia, nenhum elemento para ser removido.");
        }
        No<Item> temporario1 = getInicio();
        No<Item> temporario2 = getInicio();
        if (temporario1.getProximo() == getInicio()) {
            setInicio(null);
        } else {
            while (temporario1.getProximo() != getInicio()) {
                temporario1 = temporario1.getProximo();
            }
            setInicio(temporario2.getProximo());
            temporario1.setProximo(getInicio());
        }

        setTamanho(getTamanho() - 1);
    }

    @Override
    public void removerFim() {
        if (getInicio() == null) {
            throw new RuntimeException("~> A lista se encontra vazia, nenhum elemento para ser removido.");
        }
        No<Item> temporario1 = getInicio();
        No<Item> temporario2 = null;
        while (temporario1.getProximo() != getInicio()) {
            if (temporario1.getProximo() == getInicio()) {
                setInicio(null);
            } else {
                temporario2 = temporario1;
                temporario1 = temporario1.getProximo();
            }
        }
        temporario2.setProximo(getInicio());
        setTamanho(getTamanho() - 1);
    }

    @Override
    public I2Lista<Item> cloneList(I2Lista<Item> lista) {
        ListaIterator<Item> iterator = lista.iterator();

        I2Lista<Item> novaLista = new ListaCircular<>();
        while (iterator.hasNext()) {
            novaLista.inserir(iterator.next());
        }
        return novaLista;
    }

    @Override
    public I2Lista<Item> inverter() {
        I2Lista<Item> referencia = this.cloneList(this);
        ListaIterator<Item> iterator = referencia.iterator();
        I2Lista<Item> listaInvertida = new ListaCircular<>();
        while (iterator.hasNext()) {
            listaInvertida.inserirInicio(iterator.next());
        }

        return listaInvertida;
    }

    @Override
    public I2Lista<Item> intercalar(I2Lista<Item> lista) {
        I2Lista<Item> listaIntercalada = new ListaCircular<>();
        I2Lista<Item> referencia = this.cloneList(this);
        I2Lista<Item> referenciaParam = this.cloneList(lista);

        ListaIterator<Item> iteratorLista1 = referencia.iterator();
        ListaIterator<Item> iteratorLista2 = referenciaParam.iterator();

        if (this.getTamanho() == 0 || lista.getTamanho() == 0) {
            return listaIntercalada; // irá retornar uma lista vazia
        }

        while (iteratorLista1.hasNext() && iteratorLista2.hasNext()) {
            listaIntercalada.inserir(iteratorLista1.next());
            listaIntercalada.inserir(iteratorLista2.next());
        }

        // Consideramos os remanescentes de cada lista...

        while (iteratorLista1.hasNext()) {
            listaIntercalada.inserir(iteratorLista1.next());
        }

        while (iteratorLista2.hasNext()) {
            listaIntercalada.inserir(iteratorLista2.next());
        }

        return listaIntercalada;
    }

    @Override
    public I2Lista<Item> sublist(int inicio, int fim) {
        ListaIterator<Item> iterator = this.cloneList(this).iterator();
        I2Lista<Item> subconjunto = new ListaCircular<>();

        while (iterator.hasNext() && inicio < fim) {
            subconjunto.inserir(iterator.next());
            inicio++;
        }

        return subconjunto;
    }

    @Override
    public ListaIterator<Item> iterator() {
        return new ListaIterator<Item>() {
            private No<Item> ponteiro = getInicio();
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
        I2Lista<Item> uniao = new ListaCircular<>();

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
        I2Lista<Item> interseccao = new ListaCircular<>();

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
        I2Lista<Item> diferenca = new ListaCircular<>();

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

    @Override
    public String toString() {
        ListaIterator<Item> iterator = iterator();
        StringBuilder lista = new StringBuilder("[");
        int cont = 0;
        while (iterator.hasNext() && cont < getTamanho()) {
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
