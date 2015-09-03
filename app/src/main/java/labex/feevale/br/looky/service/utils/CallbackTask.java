package labex.feevale.br.looky.service.utils;

import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by grimmjowjack on 8/27/15.
 */
public interface CallbackTask<Entity> {

    public void success(Entity object);
    public void error(MessageResponse response);

}
