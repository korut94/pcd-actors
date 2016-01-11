package it.unipd.math.pcd.actors;

import java.util.Map;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
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
            ( ( AbsActor ) entry.getValue() ).stop();
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
            ( ( AbsActor ) getMap().get( actor ) ).stop();
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
