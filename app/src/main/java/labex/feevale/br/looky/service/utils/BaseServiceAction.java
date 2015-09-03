package labex.feevale.br.looky.service.utils;

/**
 * Created by PabloGilvan on 07/01/2015.
 */
public interface BaseServiceAction<T> {

    public void initAction();
    public void finalizeAction();
    public void finalize(T entity);
}
