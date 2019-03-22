package factory;

public class FactoryProducer {
    public static EstruturaAbstractFactory getFactory(TipoEstrutura tipoEstrutura) {
        if (tipoEstrutura.equals(TipoEstrutura.LISTA)) {
            return new ListaFactory();
        } else if (tipoEstrutura.equals(TipoEstrutura.FILA)) {
            return new FilaFactory();
        } else if (tipoEstrutura.equals(TipoEstrutura.PILHA)) {
            return new PilhaFactory();
        }
        return null;
    }
}
