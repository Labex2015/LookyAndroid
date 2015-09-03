package labex.feevale.br.looky.service.utils;

/**
 * Created by 0126128 on 22/06/2015.
 */
public interface TaskExtraAction<Entity> {

    public void onPostExecuteSuccess(Entity... params);
    public void onPostExecuteError(Entity param);
}
