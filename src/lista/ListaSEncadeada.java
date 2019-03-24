package lista;

public class ListaSEncadeada<Item extends Comparable<Item>> implements I2Lista<Item>, Conjunto<Item> {
    private No<Item> inicio;
    private No<Item> ultimo;
    private int tamanho;

    @Override
    public void inserir(Item item) {
        if (this.tamanho == 0) {
            this.inserirInicio(item);
        } else {
            No<Item> novo = new No<>(item);
            this.ultimo.setProximo(novo);
            this.ultimo = novo;
            this.tamanho++;
        }
    }

    @Override
    public void inserir(int posicao, Item item) {
        if (!(posicao >= 0 && posicao <= this.tamanho)) {
            throw new IllegalArgumentException("Posição não existe");
        }

        if (posicao == 0) {
            this.inserirInicio(item);
        } else if (posicao == this.tamanho) {
            this.inserir(item);
        } else {
            No<Item> anterior = this.pegaNo(posicao - 1);
            No<Item> novo = new No<>(item);
            novo.setProximo(anterior.getProximo());
            anterior.setProximo(novo);
            this.tamanho++;
        }
    }

    public void inserirInicio(Item item) {
        No<Item> novo = new No<>(item);
        novo.setProximo(this.inicio);
        this.inicio = novo;

        if (this.tamanho == 0) {
            this.ultimo = this.inicio;
        }
        this.tamanho++;
    }

    // Está função insere ordenado de modo crescente
    public void inserirOrdenado(Item item) {
        No<Item> novoNo = new No<>(item);
        No<Item> atual = this.getInicio();

        if (atual == null || this.getInicio().getItem().compareTo(item) >= 0) {
            novoNo.setProximo(this.getInicio());
            this.setInicio(novoNo);
        } else {
            while (atual.getProximo() != null &&
                    atual.getProximo().getItem().compareTo(item) < 0) {
                atual = atual.getProximo();
            }
            novoNo.setProximo(atual.getProximo());
            atual.setProximo(novoNo);
        }

        this.tamanho++;
    }

    public void inserir(Item[] itens) {
        for (int indice = 0; indice < itens.length; indice++) {
            this.inserir(itens[indice]);
        }
    }

    public void inserir(I2Lista<Item> lista) {
        ListaSEncadeada<Item> referenciaParam = (ListaSEncadeada<Item>) this.cloneLista(lista);
        ListaIterator<Item> iterator = referenciaParam.iterator();
        while (iterator.hasNext()) {
            this.inserir(iterator.next());
        }
    }

    protected No<Item> pegaNo(int posicao) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição não existe");
        }

        No<Item> atual = this.inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }
        return atual;
    }

    @Override
    public void replace(int posicao, Item item) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição inválida");
        }

        this.remover(posicao);
        this.inserir(posicao, item);
    }

    @Override
    public void remover(int posicao) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição inexistente");
        }

        if (posicao == 0) {
            this.removerInicio();
        } else if (posicao == this.tamanho - 1) {
            this.removerFim();
        } else {
            No<Item> anterior = this.pegaNo(posicao - 1);
            No<Item> atual = anterior.getProximo();
            No<Item> proxima = atual.getProximo();

            anterior.setProximo(proxima);
            this.tamanho--;
        }
    }

    /*
        Remove todas as ocorrências do item passado por parâmetro.
     */
    public void remover(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        int posicao = 0;
        while (iterator.hasNext()) {
            Item itemIterado = iterator.next();
            if (itemIterado.compareTo(item) == 0) { // se o item que existe na lista for igual ao passado por parâmetro
                this.remover(posicao);
                posicao--;
            }
            posicao++;
        }
    }

    public void removerPrimeiraOcorrencia(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        int posicao = 0;
        boolean achou = false;
        while (iterator.hasNext() && !achou) {
            Item itemIterado = iterator.next();
            if (itemIterado.compareTo(item) == 0) {
                this.remover(posicao);
                achou = true;
            }
            posicao++;
        }
    }

    public void removerInicio() {
        if (!(this.tamanho > 0)) {
            throw new RuntimeException("Não existem itens a serem removidos");
        }

        this.inicio = this.inicio.getProximo();
        this.tamanho--;

        if (this.tamanho == 0) {
            this.ultimo = null;
        }
    }

    public void removerFim() {
        if (!(this.tamanho > 0)) {
            throw new RuntimeException("Não existem itens a serem removidos");
        }

        if (this.tamanho == 1) {
            this.removerInicio();
        } else {
            No<Item> penultima = this.pegaNo(tamanho - 2);
            penultima.setProximo(null);
            this.ultimo = penultima;
            this.tamanho--;
        }
    }

    @Override
    public Item obter(int posicao) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição inexistente");
        }

        ListaIterator<Item> iterator = this.iterator();
        Item item = null;
        for (int i = 0; i <= posicao && iterator.hasNext(); i++) {
            item = iterator.next();
        }
        return item;
    }

    @Override
    public Item obter(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        Item itemBuscado = null;
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
        I2Lista<Item> novaReferencia = this.cloneLista(this);
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

    @Override
    public void limpar() {
        this.setInicio(null);
        this.setTamanho(0);
    }

    @Override
    public boolean isVazia() {
        return this.getTamanho() == 0;
    }

    @Override
    public ListaIterator<Item> iterator() {
        return new ListaIterator<Item>() {
            private No<Item> ponteiro = inicio;
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
    
    public I2Lista<Item> cloneLista(I2Lista<Item> lista) {
        I2Lista<Item> referencia = lista;
        ListaIterator<Item> iterator = referencia.iterator();

        I2Lista<Item> novaLista = new ListaSEncadeada<>();
        while (iterator.hasNext()) {
            novaLista.inserir(iterator.next());
        }
        return novaLista;
    }

    @Override
    public I2Lista<Item> uniao(I2Lista<Item> outraLista) {
        I2Lista<Item> lista1 = cloneLista(this);
        I2Lista<Item> lista2 = cloneLista(outraLista);
        I2Lista<Item> uniao = new ListaSEncadeada<>();

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
        I2Lista<Item> lista1 = cloneLista(this);
        I2Lista<Item> lista2 = cloneLista(outraLista);
        I2Lista<Item> interseccao = new ListaSEncadeada<>();

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
        I2Lista<Item> lista1 = cloneLista(this);
        I2Lista<Item> lista2 = cloneLista(outraLista);
        I2Lista<Item> diferenca = new ListaSEncadeada<>();

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
        ListaSEncadeada<Item> listaVetor = (ListaSEncadeada<Item>) lista;
        ListaIterator<Item> iterator = listaVetor.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }

        return false;
    }

    public I2Lista<Item> inverter() {
        I2Lista<Item> referencia = this.cloneLista(this);
        ListaIterator<Item> iterator = referencia.iterator();
        I2Lista<Item> listaInvertida = new ListaSEncadeada<>();
        while (iterator.hasNext()) {
            listaInvertida.inserirInicio(iterator.next());
        }

        return listaInvertida;
    }
    
    public I2Lista<Item> inverterComPonteiros() {
        No<Item> primeiro = getInicio();
        No<Item> ultimo = getUltimo();
        while (indexOf(primeiro.getItem()) < indexOf(ultimo.getItem())) {
            Item aux = primeiro.getItem();
            primeiro.setItem(ultimo.getItem());
            ultimo.setItem(aux);
            primeiro = primeiro.getProximo();
            ultimo = pegaNo(indexOf(ultimo.getItem()) - 1);
        }
        return this;
    }


    public I2Lista<Item> intercalar(I2Lista<Item> lista) {
        I2Lista<Item> listaIntercalada = new ListaSEncadeada<>();
        I2Lista<Item> referencia = this.cloneLista(this);
        I2Lista<Item> referenciaParam = this.cloneLista(lista);

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

    // o fim é um índice exclusivo, isto é, o valor deste índice não é considerado
    public I2Lista<Item> subLista(int inicio, int fim) {
        ListaIterator<Item> iterator = this.cloneLista(this).iterator();
        I2Lista<Item> subconjunto = new ListaSEncadeada<>();

        while (iterator.hasNext() && inicio < fim) {
            subconjunto.inserir(iterator.next());
            inicio++;
        }

        return subconjunto;
    }

    /*
        Retorna o índice da primeira ocorrência do item passado por parâmetro
     */
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

    public Object[] toArray() {
        Object[] arr = new Comparable[this.getTamanho()];
        ListaSEncadeada<Item> referencia = (ListaSEncadeada<Item>) this.cloneLista(this);
        ListaIterator<Item> iterator = referencia.iterator();

        for (int indice = 0; iterator.hasNext() && indice < this.getTamanho(); indice++) {
            arr[indice] = iterator.next();
        }

        return arr;
    }

    public int count(Item item) {
        ListaIterator<Item> iterator = this.iterator();
        int quantidadeAparece = 0;
        while (iterator.hasNext()) {
            if (iterator.next().compareTo(item) == 0) {
                quantidadeAparece++;
            }
        }

        return quantidadeAparece;
    }

    public No<Item> getInicio() {
        return inicio;
    }

    public void setInicio(No<Item> inicio) {
        this.inicio = inicio;
    }

    public No<Item> getUltimo() {
        return ultimo;
    }

    public void setUltimo(No<Item> ultimo) {
        this.ultimo = ultimo;
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
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
