package lista;

/**
 * Esta classe vetor representa uma estrutura de dados dinâmica. Ela
 * cresce de acordo com a demanda.
 * @param <Item> Um item que implemente a interface Comparable
 */
public class Vetor<Item extends Comparable<Item>> implements I2Lista<Item>, Conjunto<Item> {
    private Item[] itens;
    private int tamanho;
    private int capacidadeMaxima;
    private static final int CAPACIDADE_MAXIMA_PADRAO = 3;

    public Vetor() {
        init(Vetor.CAPACIDADE_MAXIMA_PADRAO);
    }

    public Vetor(int capacidadeMaxima) {
        init(capacidadeMaxima);
    }

    public Item[] getItens() {
        return itens;
    }

    public void setItens(Item[] itens) {
        this.itens = itens;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    private void init(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
        this.itens = (Item[]) new Comparable[this.capacidadeMaxima];
        this.tamanho = 0;
    }

    @Override
    public void inserir(Item item) {
        if (this.tamanho == this.capacidadeMaxima) {
            //throw new ListFullException();
            this.aumentarCapacidade();
        }

        this.itens[this.tamanho++] = item;
    }

    @Override
    public void inserir(int posicao, Item item) {
        if (this.tamanho == this.capacidadeMaxima) {
            //throw new ListFullException();
            this.aumentarCapacidade();
        }

        if (!(posicao >= 0 && posicao <= this.tamanho)) {
            throw new IllegalArgumentException("Posição inválida");
        }

        for (int indice = this.tamanho - 1; indice >= posicao; indice--) {
            this.itens[indice + 1] = this.itens[indice];
        }

        this.itens[posicao] = item;
        this.tamanho++;
    }

    public void inserirInicio(Item item) {
        this.inserir(0, item);
    }

    @Override
    public void replace(int posicao, Item item) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição inválida");
        }

        this.itens[posicao] = item;
    }

    @Override
    public void remover(int posicao) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição inválida");
        }

        int indice;
        for (indice = posicao; indice < this.tamanho - 1; indice++) {
            this.itens[indice] = this.itens[indice + 1];
        }

        this.itens[indice] = null;
        this.tamanho--;
    }

    @Override
    public Item obter(int posicao) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição inválida");
        }

        return this.itens[posicao];
    }

    @Override
    public Item obter(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            Item itemAtual = iterator.next();
            if (itemAtual.equals(item)) {
                return itemAtual;
            }
        }

        return null;
    }

    @Override
    public boolean contem(Item item) {
        Vetor<Item> listaVetor = clone(this);
        ListaIterator<Item> iterator = listaVetor.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public I2Lista<Item> ordenar() {
        Vetor<Item> novaReferencia = clone(this);
        return quicksort(novaReferencia, 0, novaReferencia.tamanho - 1);
    }

    @Override
    public boolean isVazia() {
        return this.getTamanho() == 0;
    }

    // Estamos considerando nesta implementação aumentar a capacidade da lista pelo
    // dobro do que ela já possuía.
    private void aumentarCapacidade() {
        this.capacidadeMaxima *= 2;
        Item[] temporario = (Item[]) new Comparable[this.capacidadeMaxima];
        for (int indice = 0; indice < this.tamanho; indice++) {
            temporario[indice] = this.itens[indice];
        }
        this.itens = temporario;
    }

    @Override
    public ListaIterator<Item> iterator() {
        return new ListaIterator<Item>() {
            private int ponteiro = 0;
            @Override
            public Item next() {
                return itens[ponteiro++];
            }

            @Override
            public boolean hasNext() {
                return ponteiro < tamanho && itens[ponteiro] != null;
            }
        };
    }

    @Override
    public I2Lista<Item> uniao(I2Lista<Item> outraLista) {
        Vetor<Item> lista1 = clone(this);
        Vetor<Item> lista2 = clone(outraLista);
        Vetor<Item> uniao = new Vetor<>(this.getTamanho() + lista1.getTamanho());

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
    public I2Lista<Item> interseccao(I2Lista<Item> outraLista) {
        Vetor<Item> lista1 = clone(this);
        Vetor<Item> lista2 = clone(outraLista);
        Vetor<Item> interseccao = new Vetor<>(this.getTamanho() + lista1.getTamanho());

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
    public I2Lista<Item> diferenca(I2Lista<Item> outraLista) {
        Vetor<Item> lista1 = clone(this);
        Vetor<Item> lista2 = clone(outraLista);
        Vetor<Item> diferenca = new Vetor<>(this.getTamanho() + lista2.getTamanho());

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

    private boolean find(Item item, I2Lista<Item> lista) {
        Vetor<Item> listaVetor = (Vetor<Item>) lista;
        ListaIterator<Item> iterator = listaVetor.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }

        return false;
    }

    protected Vetor<Item> clone(I2Lista<Item> lista) {
        Vetor<Item> vetor = (Vetor<Item>) lista;
        ListaIterator<Item> iterator = vetor.iterator();

        Vetor<Item> novoVetor = new Vetor<>(vetor.getTamanho());
        while (iterator.hasNext()) {
            novoVetor.inserir(iterator.next());
        }
        return novoVetor;
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    public I2Lista<Item> inverter() {
        ListaIterator<Item> iterator = clone(this).iterator();
        Vetor<Item> vetorInvertido = new Vetor<>(this.getTamanho());
        while (iterator.hasNext()) {
            vetorInvertido.inserirInicio(iterator.next());
        }

        return vetorInvertido;
    }

    private Vetor<Item> quicksort(Vetor<Item> itens, int indiceEsquerdo, int indiceDireito) {
        int indicePivot;
        if (itens.tamanho > 1) {
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

    private int particionar(Vetor<Item> itens, int indiceEsquerdo, int indiceDireito) {
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

    @Override
    public void limpar() {
        for (int indice = 0; indice < this.getTamanho(); indice++) {
            this.itens[indice] = null;
        }

        this.tamanho = 0;
    }

    @Override
    public int indexOf(Item item) {
        return 0;
    }

    @Override
    public String toString() {
        ListaIterator<Item> iterator = iterator();
        StringBuilder lista = new StringBuilder("[");
        int count = 0;
        while (iterator.hasNext() && count < tamanho - 1) {
            lista.append(iterator.next());
            lista.append(", ");
            count++;
        }

        // Precisamos depois desenvolver um modo mais econômico para realizar esta parte...
        Item ultimo = iterator.next();
        if (ultimo != null) {
            lista.append(ultimo);
        }

        lista.append("]");
        return lista.toString();
    }
}

