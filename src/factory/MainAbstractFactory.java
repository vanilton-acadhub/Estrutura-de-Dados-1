package factory;

import fila.I2Fila;
import lista.I2Lista;
import pilha.I2Pilha;

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

        estruturaFactory = FactoryProducer.getFactory(TipoEstrutura.FILA);
        I2Fila<String> fila = estruturaFactory.getFila(TipoFila.FILA_SEQUENCIAL);

        fila.enqueue("João");
        fila.enqueue("Maria");
        fila.enqueue("José");
        System.out.println(fila);
        System.out.println("Primeiro da fila: " + fila.peek());
        System.out.println("Retiramos da fila: " + fila.dequeue());
        System.out.println("Primeiro da fila agora: " + fila.peek());

        estruturaFactory = FactoryProducer.getFactory(TipoEstrutura.PILHA);
        I2Pilha<Character> pilha = estruturaFactory.getPilha(TipoPilha.PILHA_SEQUENCIAL);

        pilha.push('1');
        pilha.push('+');
        pilha.push('2');
        System.out.println(pilha);
        System.out.println("Topo da pilha: " + pilha.top());
        System.out.println("Retirado do topo da pilha: " + pilha.pop());
        System.out.println("Topo da pilha agora: " + pilha.top());

        pilha = estruturaFactory.getPilha(TipoPilha.PILHA_ENCADEADA);

        pilha.push('v');
        pilha.push('a');
        pilha.push('n');
        System.out.println(pilha);
        System.out.println("Topo da pilha: " + pilha.top());
    }
}