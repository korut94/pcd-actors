package it.unipd.math.pcd.actors;

import java.util.Map;

/**
 * Created by amantova on 16/12/15.
 */
public final class ImpActorSystem extends AbsActorSystem
{
    /**
     * Stops all actors of the system.
     */
    @Override
    public void stop()
    {
        Map<ActorRef<? extends Message>, Actor<? extends Message>> actors = getMap();

        for( Map.Entry<ActorRef<? extends Message>, Actor<? extends Message>> entry : actors.entrySet() )
        {
            ( ( LocalActorRef ) entry.getKey() ).stopSend();
        }
    }

    /**
     * Stops {@code actor}.
     *
     * @param actor The actor to be stopped
     */
    public void stop( ActorRef<?> actor )
    {
        if( actor != null )
        {
            ( ( LocalActorRef ) actor ).stopSend();
        }
    }

    @Override
    protected ActorRef createActorReference( ActorMode mode )
    {
        if( mode == ActorMode.LOCAL )
        {
            LocalActorRef ref = new LocalActorRef( this );
            /**
             * Note that thread is start but it will stop because the mailbox is empty
             */
            ref.start();

            return ref;
        }
        else
        {
            return null;
        }
    }
}
