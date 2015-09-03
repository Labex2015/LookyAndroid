package labex.feevale.br.looky.service.utils;

/**
 * Created by pablo on 7/16/15.
 */
public interface Observer {

    public void update();
    public void setSubject(Subject sub);
    public void removeSubject(Subject sub);
}
