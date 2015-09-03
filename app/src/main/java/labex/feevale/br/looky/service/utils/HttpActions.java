package labex.feevale.br.looky.service.utils;

import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by pablo on 7/22/15.
 */
public interface HttpActions<Entity>{

    public void afterDelete(MessageResponse messageResponse);
    public void afterInsertUpdate(Entity entity);
    public void afterList(Entity entity);
    public void fail(MessageResponse messageResponse);
}
