package lista;

public interface I2Lista<Item> extends ListaIteravel<Item> {

    /**
     * Insere um item no final da lista.
     * @param item O item a ser inserido.
     * @throws ListFullException Caso a lista se encontre cheia.
     */
    void inserir(Item item);

    /**
     * Insere um item na lista na posição informada por parâmetro.
     * @param posicao A posição da lista onde será inserido o item.
     * @param item O item a ser inserido.
     * @throws ListFullException Caso a lista se encontre cheia.
     */
    void inserir(int posicao, Item item);

    void inserirInicio(Item item);

    /**
     * Substitue o item da lista na respectiva posição pelo item passado por parâmetro.
     * @param posicao A posição do item a ser substituído.
     * @param item O item novo a ser colocado na lista.
     */
    void replace(int posicao, Item item);

    /**
     * Remove o item da lista com base em sua posição.
     * @param posicao A posição do item a ser removido.
     */
    void remover(int posicao);

    /**
     * Obtém um item da lista conforme a posição passada por parâmetro.
     * @param posicao A posição do item a ser obtida.
     * @return O item a ser obtido.
     */
    Item obter(int posicao);

    /**
     * Obtém um item da lista.
     * @param item O item a ser obtido da lista.
     * @return O item a ser obtido. Retorna null caso o elemento não se encontre na lista.
     */
    Item obter(Item item);

    /**
     * Verifica se o item está contido na lista.
     * @param item O item a ser buscado.
     * @return Retorna true caso a lista contenha o item, false caso contrário.
     */
    boolean contem(Item item);

    /**
     * Cria uma representação ordenada da respectiva lista e retorna
     * a mesma como resultado. Não altera a lista original.
     * @return Uma nova representação da lista ordenada.
     */
    I2Lista<Item> ordenar();

    /**
     * Obtém o tamanho da lista.
     * @return O tamanho da lista.
     */
    int getTamanho();

    /**
     * Remove todos os itens da lista.
     */
    void limpar();


    int indexOf(Item item);

    /**
     * Verifica se a lista está vazia.
     * @return Retorna true se a lista estiver vazia, false caso contrário.
     */
    boolean isVazia();
}

