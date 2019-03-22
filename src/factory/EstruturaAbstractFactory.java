package factory;

import fila.I2Fila;
import lista.I2Lista;
import pilha.I2Pilha;

public abstract class EstruturaAbstractFactory {
    abstract I2Lista getLista(TipoLista tipoLista);
    abstract I2Fila getFila(TipoFila tipoFila);
    abstract I2Pilha getPilha(TipoPilha tipoPilha);
}
