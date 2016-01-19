package it.unipd.math.pcd.actors;

import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public final class ImpActorSystem extends AbsActorSystem
{
    private void stopByActorRef(
            ActorRef<?> actor,
            Map<ActorRef<? extends Message>, Actor<? extends Message>> actors,
            Map<ActorRef<?>,FutureTask> daemons
    ) {
        //Stop the receiving messages
        ( ( AbsActor )actors.get( actor ) ).stop();

        //Wait termination of task
        FutureTask daemon = daemons.get( actor );
        daemon.cancel( true );

        //Remove daemon and actor of the system
        daemons.remove( actor );
        actors.remove( actor );
    }

    /**
     * Stops all actors of the system.
     */
    @Override
    public void stop()
    {
        Map<ActorRef<? extends Message>, Actor<? extends Message>> actors = getMapActors();
        Map<ActorRef<?>,FutureTask> daemons = getMapDaemons();

        for( Map.Entry<ActorRef<? extends Message>, Actor<? extends Message>> entry : actors.entrySet() )
        {
            stopByActorRef( entry.getKey(), actors, daemons );
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
            stopByActorRef( actor, getMapActors(), getMapDaemons() );
        }
    }

    @Override
    protected ActorRef createActorReference( ActorMode mode )
    {
        if( mode == ActorMode.LOCAL )
        {
            return new LocalActorRef( this );
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
