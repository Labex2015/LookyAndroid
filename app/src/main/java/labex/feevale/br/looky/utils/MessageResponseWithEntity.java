package labex.feevale.br.looky.utils;

/**
 * Created by 0126128 on 07/04/2015.
 */
public abstract class MessageResponseWithEntity<T> extends MessageResponse {

    protected T entity;

    protected MessageResponseWithEntity() {}

    protected MessageResponseWithEntity(String msg, Boolean status, T entity) {
        super(msg, status);
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
