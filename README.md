# Estrutura De Dados 1

O intuito deste projeto é de ser melhorado ao longo do tempo para que possibilite outras pessoas a aprenderem
estrutura de dados.

Como você pode utilizar a asbtract factory para criar estruturas de dados rapidamente, este exemplo utilizamos para
obtermos uma lista simplesmente encadeada:

```java
public class MainAbstractFactory {
    public static void main(String[] args) {
         EstruturaAbstractFactory estruturaFactory = FactoryProducer.getFactory(TipoEstrutura.LISTA);
         I2Lista<Integer> listaSimplesmenteEncadeada = estruturaFactory.getLista(TipoLista.LISTA_SIMPLESMENTE_ENCADEADA);
 
         listaSimplesmenteEncadeada.inserir(5);
         listaSimplesmenteEncadeada.inserir(3);
         listaSimplesmenteEncadeada.inserir(1996);
         listaSimplesmenteEncadeada.inserir(0);
         I2Lista<Integer> listaOrdenada = listaSimplesmenteEncadeada.ordenar();
         System.out.println(listaSimplesmenteEncadeada);
         System.out.println(listaOrdenada);
    }
}
```

Existem três tipos de `TipoEstrutura`:
+ `TipoEstrutura.LISTA`
+ `TipoEstrutura.FILA`
+ `TipoEstrutura.PILHA`

Tipos de listas disponíveis:
+ `TipoLista.LISTA_SIMPLESMENTE_ENCADEADA` (Lista simplesmente encadeada)
+ `TipoLista.LISTA_DUPLAMENTE_ENCADEADA` (Lista duplamente encadeada)
+ `TipoLista.LISTA_CIRCULAR` (Lista circular utilizando lista simplesmente encadeada)
+ `TipoLista.LISTA_CIRCULAR_DUPLAMENTE_ENCADEADA` (Lista circular utilizando lista duplamente encadeada)
+ `TipoLista.LISTA_SEQUENCIAL` (Lista utilizando vetores)

Tipos de filas disponíveis:
+ `TipoFila.FILA_SEQUENCIAL` (Fila utilizando vetores)
+ `TipoFila.FILA_SIMPLESMENTE_ENCADEADA` (Fila utilizando lista simplesmente encadeada)
+ `TipoFila.FILA_CIRCULAR_SIMPLESMENTE_ENCADEADA` (Fila circular utilizando lista circular com lista simplesmente encadeada)
+ `TipoFila.FILA_CIRCULAR_DUPLAMENTE_ENCADEADA` (Fila circular utilizando lista circular com lista duplamente encadeada)
+ `TipoFila.FILA_DE_PRIORIDADE` (Fila utilizando lista simplesmente encadeada, porém o método de inserção na fila é ordenado)
+ `TipoFila.FILA_DE_PRIORIDADE_CIRCULAR` (Fila utilizando lista circular com lista simplesmente encadeada, porém o método de inserção na fila é ordenado)
+ `TipoFila.FILA_DE_PRIORIDADE_CIRCULAR_DUPLAMENTE_ENCADEADA` (Fila utilizando fila circular com lista duplamente encadeada, porém o método de inserção na fila é ordenado)

Tipos de pilhas disponíveis:
+ `TipoPilha.PILHA_SEQUENCIAL` (Pilha utilizando vetores)
+ `TipoPilha.PILHA_ENCADEADA` (Pilha utilizando lista simplesmente encadeada)