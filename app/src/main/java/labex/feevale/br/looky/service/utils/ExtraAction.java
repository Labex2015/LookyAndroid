package labex.feevale.br.looky.service.utils;

/**
 * Created by 0126128 on 12/06/2015.
 */
public interface ExtraAction<Entity> {

    public void executeAction(Entity... params);
}
