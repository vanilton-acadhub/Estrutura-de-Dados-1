package factory;

import fila.I2Fila;
import lista.*;
import pilha.I2Pilha;

public class ListaFactory extends EstruturaAbstractFactory {
    @Override
    I2Lista getLista(TipoLista tipoLista) {
        if (tipoLista == null) {
            return null;
        }

        if (tipoLista.equals(TipoLista.LISTA_SIMPLESMENTE_ENCADEADA)) {
            return new ListaSEncadeada();
        } else if (tipoLista.equals(TipoLista.LISTA_DUPLAMENTE_ENCADEADA)) {
            return new ListaDuplamenteEncadeada();
        } else if (tipoLista.equals(TipoLista.LISTA_CIRCULAR)) {
            return new ListaCircular();
        } else if (tipoLista.equals(TipoLista.LISTA_CIRCULAR_DUPLAMENTE_ENCADEADA)) {
            return new ListaDuplamenteCircular();
        } else if (tipoLista.equals(TipoLista.LISTA_SEQUENCIAL)) {
            return new Vetor();
        }

        return null;
    }

    @Override
    I2Fila getFila(TipoFila tipoFila) {
        return null;
    }

    @Override
    I2Pilha getPilha(TipoPilha tipoPilha) {
        return null;
    }
}
