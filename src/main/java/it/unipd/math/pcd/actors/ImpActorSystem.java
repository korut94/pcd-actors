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
        Map<ActorRef<? extends Message>, Actor<?>> actors = getMap();

        for( Map.Entry<ActorRef<? extends Message>, Actor<?>> entry : actors.entrySet() )
        {
            ( ( ImpActorRef ) entry.getKey() ).stopSend();
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
            ( ( ImpActorRef ) actor ).stopSend();
        }
    }

    @Override
    protected ActorRef createActorReference( ActorMode mode )
    {
        if( mode == ActorMode.LOCAL )
        {
            ImpActorRef ref = new ImpActorRef( this );
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
