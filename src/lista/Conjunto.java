package lista;

public interface Conjunto<Item> {
    /**
     * Realiza a união da lista atual com a lista passada por parâmetro.
     * No fim teremos uma nova lista com os itens que ambas não possuem sem repetição com os itens
     * intersecção. A lista atual e a lista passada por parâmetro são tratadas como imutáveis.
     * @param outraLista A outra lista que vamos unir a lista atual.
     * @return A nova lista da união.
     */
    I2Lista<Item> union(I2Lista<Item> outraLista);

    /**
     * Realiza a operação de intersecção da lista atual com a lista passada por parâmetro.
     * A lista atual e a lista passada por arâmetro sã otratadas como imutáveis.
     * @param outraLista A outra lista que vamos tirar a intersecção com a lista atual.
     * @return A intersecção das duas listas.
     */
    I2Lista<Item> intersection(I2Lista<Item> outraLista);

    /**
     * Realiza a operação de diferença da lista atual com a lista passada por parâmetro.
     * @param outraLista A outra lista que vamos tirar a difernça.
     * @return A diferença da lista atual com a lista passada por parâmetro.
     */
    I2Lista<Item> diference(I2Lista<Item> outraLista);
}
