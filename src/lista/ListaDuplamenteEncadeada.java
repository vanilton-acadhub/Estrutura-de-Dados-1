package lista;

public class ListaDuplamenteEncadeada<Item extends Comparable<Item>> implements I2Lista<Item>, Conjunto<Item> {
    private NoDuplo<Item> inicio;
    private NoDuplo<Item> ultimo;
    private int tamanho;

    public NoDuplo<Item> getUltimo() {
        return ultimo;
    }

    public void setUltimo(NoDuplo<Item> ultimo) {
        this.ultimo = ultimo;
    }

    public NoDuplo<Item> getInicio() {
        return inicio;
    }

    public void setInicio(NoDuplo<Item> inicio) {
        this.inicio = inicio;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public I2Lista<Item> union(I2Lista<Item> outraLista) {
        I2Lista<Item> lista1 = cloneList(this);
        I2Lista<Item> lista2 = cloneList(outraLista);
        I2Lista<Item> uniao = new ListaDuplamenteEncadeada<>();

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
        I2Lista<Item> interseccao = new ListaDuplamenteEncadeada<>();

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
        I2Lista<Item> diferenca = new ListaDuplamenteEncadeada<>();

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
        ListaDuplamenteEncadeada<Item> listaVetor = (ListaDuplamenteEncadeada<Item>) lista;
        ListaIterator<Item> iterator = listaVetor.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void inserir(Item item) {
        if (getTamanho() == 0) {
            this.inserirInicio(item);
        } else {
            NoDuplo<Item> ultimoEncadeado = new NoDuplo<>(item);
            this.ultimo.setProximo(ultimoEncadeado);
            ultimoEncadeado.setAnterior(this.ultimo);
            this.ultimo = ultimoEncadeado;

            this.tamanho++;
        }
    }

    @Override
    public void inserir(int posicao, Item item) {
        if (!(posicao >= 0 && posicao <= getTamanho())) {
            throw new IllegalArgumentException("Posiçâo inexistente");
        }

        NoDuplo<Item> novoNo = new NoDuplo<>(item);
        if (posicao == 0) {
            this.inserirInicio(item);
        } else if (posicao == getTamanho()) {
            this.inserir(item);
        } else {
            NoDuplo<Item> atual = getInicio();
            for (int i = 0; i < posicao; i++) {
                atual = atual.getProximo();
            }
            NoDuplo<Item> anterior = atual.getAnterior();
            anterior.setProximo(novoNo);
            novoNo.setAnterior(anterior);
            novoNo.setProximo(atual);
            atual.setAnterior(novoNo);

            this.tamanho++;
        }
    }

    @Override
    public void inserirInicio(Item item) {
        if (this.getTamanho() == 0) {
            NoDuplo<Item> ultimoEncadeado = new NoDuplo<>(item);
            setInicio(ultimoEncadeado);
            setUltimo(ultimoEncadeado);
        } else {
            NoDuplo<Item> ultimoEncadeado = new NoDuplo<>(item);
            ultimoEncadeado.setProximo(getInicio());
            ultimoEncadeado.setAnterior(null);
            getInicio().setAnterior(ultimoEncadeado);
            setInicio(ultimoEncadeado);
        }
        this.tamanho++;
    }

    public void inserirOrdenado(Item item) {
        NoDuplo<Item> novoNo = new NoDuplo<>(item);
        NoDuplo<Item> atual = this.getInicio();

        if (atual == null) {
            setInicio(novoNo);
        } else if (atual.getItem().compareTo(item) >= 0) {
            novoNo.setProximo(atual);
            novoNo.getProximo().setAnterior(novoNo);
            setInicio(novoNo);
        } else {
            while (atual.getProximo() != null &&
                    atual.getProximo().getItem().compareTo(item) < 0) {
                atual = atual.getProximo();
            }
            novoNo.setProximo(atual.getProximo());
            if (atual.getProximo() != null) {
                novoNo.getProximo().setAnterior(novoNo);
            }
            atual.setProximo(novoNo);
            novoNo.setAnterior(atual);
        }

        this.tamanho++;
    }

    protected NoDuplo<Item> pegaNo(int posicao) {
        if (!(posicao >= 0 && posicao < getTamanho())) {
            throw new IllegalArgumentException("Posiçâo inexistente");
        }

        NoDuplo<Item> atual = getInicio();
        for (int indice = 0; indice < posicao; indice++) {
            atual = atual.getProximo();
        }

        return atual;
    }

    @Override
    public void replace(int posicao, Item item) {
        if (!(posicao >= 0 && posicao < getTamanho())) {
            throw new IllegalArgumentException("Posição inválida");
        }

        this.remover(posicao);
        this.inserir(posicao, item);
    }

    @Override
    public void remover(int posicao) {
        if (posicao < 0 || posicao >= getTamanho()) {
            throw new IndexOutOfBoundsException();
        } else if (posicao == 0) {
            setInicio(getInicio().getProximo());
            getInicio().setAnterior(null);
            this.tamanho--;
        } else if (posicao == getTamanho() - 1) {
            setUltimo(ultimo.getAnterior());
            getUltimo().setProximo(null);
            this.tamanho--;
        } else {
            NoDuplo<Item> atual = getInicio();
            for (int i = 0; i < posicao; i++) {
                atual = atual.getProximo();
            }
            NoDuplo<Item> anterior = atual.getAnterior();
            NoDuplo<Item> proximo = atual.getProximo();
            anterior.setProximo(atual.getProximo());
            proximo.setAnterior(anterior);
            this.tamanho--;
        }
    }

    @Override
    public Item obter(int posicao) {
        if (posicao < 0 || posicao >= getTamanho()) {
            throw new IndexOutOfBoundsException();
        } else {
            NoDuplo<Item> atual = getInicio();
            for (int i = 0; i < posicao; i++) {
                atual = atual.getProximo();
            }
            return atual.getItem();
        }
    }

    @Override
    public Item obter(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        Item itemBuscado;
        while (iterator.hasNext()) {
            itemBuscado = iterator.next();
            if (itemBuscado.equals(item)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean contem(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public I2Lista<Item> ordenar() {
        I2Lista<Item> novaReferencia = this.cloneList(this);
        return quicksort(novaReferencia, 0, novaReferencia.getTamanho() - 1);
    }

    protected I2Lista<Item> quicksort(I2Lista<Item> itens, int indiceEsquerdo, int indiceDireito) {
        int indicePivot;
        if (itens.getTamanho() > 1) {
            indicePivot = particionar(itens, indiceEsquerdo, indiceDireito);

            if (indiceEsquerdo < indicePivot - 1) {
                quicksort(itens, indiceEsquerdo, indicePivot - 1);
            }

            if (indicePivot < indiceDireito) {
                quicksort(itens, indicePivot, indiceDireito);
            }
        }

        return itens;
    }

    protected int particionar(I2Lista<Item> itens, int indiceEsquerdo, int indiceDireito) {
        Item pivot = itens.obter((indiceEsquerdo + indiceDireito) / 2);
        while (indiceEsquerdo <= indiceDireito) {
            while (itens.obter(indiceEsquerdo).compareTo(pivot) < 0) {
                indiceEsquerdo++;
            }

            while (itens.obter(indiceDireito).compareTo(pivot) > 0) {
                indiceDireito--;
            }

            if (indiceEsquerdo <= indiceDireito) {
                Item temporario = itens.obter(indiceEsquerdo);
                itens.replace(indiceEsquerdo, itens.obter(indiceDireito));
                itens.replace(indiceDireito, temporario);
                indiceEsquerdo++;
                indiceDireito--;
            }
        }

        return indiceEsquerdo;
    }

    public I2Lista<Item> cloneList(I2Lista<Item> lista) {
        I2Lista<Item> referencia = lista;
        ListaIterator<Item> iterator = referencia.iterator();

        I2Lista<Item> novaLista = new ListaDuplamenteEncadeada<>();
        while (iterator.hasNext()) {
            novaLista.inserir(iterator.next());
        }
        return novaLista;
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    @Override
    public void limpar() {
        this.setInicio(null);
        this.setTamanho(0);
    }

    @Override
    public int indexOf(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        int indice = 0;
        while (iterator.hasNext()) {
            if (iterator.next().compareTo(item) == 0) {
                return indice;
            }
            indice++;
        }

        return -1; // significa que já percorremos a lista inteira e não achamos o item
    }

    public int lastIndexOf(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        int indice = 0;
        int indiceEncontrado = -1;
        while (iterator.hasNext()) {
            if (iterator.next().compareTo(item) == 0) {
                indiceEncontrado = indice;
            }
            indice++;
        }

        return indiceEncontrado;
    }

    // o fim é um índice exclusivo, isto é, o valor deste índice não é considerado
    public I2Lista<Item> sublist(int inicio, int fim) {
        ListaIterator<Item> iterator = this.cloneList(this).iterator();
        I2Lista<Item> subconjunto = new ListaDuplamenteEncadeada<>();

        while (iterator.hasNext() && inicio < fim) {
            subconjunto.inserir(iterator.next());
            inicio++;
        }

        return subconjunto;
    }


    @Override
    public boolean isVazia() {
        return this.tamanho == 0;
    }

    @Override
    public ListaIterator iterator() {
        return new ListaIterator<Item>() {
            private NoDuplo<Item> ponteiro = getInicio();

            @Override
            public Item next() {
                Item item = ponteiro.getItem();
                ponteiro = ponteiro.getProximo();
                return item;
            }

            @Override
            public boolean hasNext() {
                return ponteiro != null;
            }
        };
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

    public I2Lista<Item> inverter() {
        I2Lista<Item> referencia = this.cloneList(this);
        ListaIterator<Item> iterator = referencia.iterator();
        I2Lista<Item> listaInvertida = new ListaDuplamenteEncadeada<>();
        while (iterator.hasNext()) {
            listaInvertida.inserirInicio(iterator.next());
        }

        return listaInvertida;
    }
}
