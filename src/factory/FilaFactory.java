package factory;

import fila.*;
import lista.I2Lista;
import pilha.I2Pilha;

public class FilaFactory extends EstruturaAbstractFactory {
    @Override
    I2Lista getLista(TipoLista tipoLista) {
        return null;
    }

    @Override
    I2Fila getFila(TipoFila tipoFila) {
        if (tipoFila == null) {
            return null;
        }

        if (tipoFila.equals(TipoFila.FILA_SEQUENCIAL)) {
            return new Fila();
        } else if (tipoFila.equals(TipoFila.FILA_SIMPLESMENTE_ENCADEADA)) {
            return new FilaSimplesmenteEncadeada();
        } else if (tipoFila.equals(TipoFila.FILA_CIRCULAR_SIMPLESMENTE_ENCADEADA)) {
            return new FilaCircularSEncadeada();
        } else if (tipoFila.equals(TipoFila.FILA_CIRCULAR_DUPLAMENTE_ENCADEADA)) {
            return new FilaCircularDEncadeada();
        } else if (tipoFila.equals(TipoFila.FILA_DE_PRIORIDADE)) {
            return new FilaDePrioridade();
        } else if (tipoFila.equals(TipoFila.FILA_DE_PRIORIDADE_CIRCULAR)) {
            return new FilaDePrioridadeCircular();
        } else if (tipoFila.equals(TipoFila.FILA_DE_PRIORIDADE_CIRCULAR_DUPLAMENTE_ENCADEADA)) {
            return new FilaPrioridadeCDEncadeada();
        }

        return null;
    }

    @Override
    I2Pilha getPilha(TipoPilha tipoPilha) {
        return null;
    }
}
