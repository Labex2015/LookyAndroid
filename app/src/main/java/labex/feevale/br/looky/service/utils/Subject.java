package labex.feevale.br.looky.service.utils;

/**
 * Created by pablo on 7/16/15.
 */
public interface Subject {

    public void register(Observer obj);
    public void unregister(Observer obj);

    public void notifyObservers();

    public Object getUpdate(Observer obj);

}
