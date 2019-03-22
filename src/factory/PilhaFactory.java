package factory;

import fila.I2Fila;
import lista.I2Lista;
import pilha.I2Pilha;
import pilha.Pilha;
import pilha.PilhaEncadeada;

public class PilhaFactory extends EstruturaAbstractFactory {
    @Override
    I2Lista getLista(TipoLista tipoLista) {
        return null;
    }

    @Override
    I2Fila getFila(TipoFila tipoFila) {
        return null;
    }

    @Override
    I2Pilha getPilha(TipoPilha tipoPilha) {
        if (tipoPilha == null) {
            return null;
        }

        if (tipoPilha.equals(TipoPilha.PILHA_SEQUENCIAL)) {
            return new Pilha();
        } else if (tipoPilha.equals(TipoPilha.PILHA_ENCADEADA)) {
            return new PilhaEncadeada();
        }

        return null;
    }
}
