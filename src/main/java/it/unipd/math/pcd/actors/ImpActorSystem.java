package it.unipd.math.pcd.actors;

import java.util.Map;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public final class ImpActorSystem extends AbsActorSystem
{
    private void stopActor( AbsActor actor )
    {
        actor.interrupt();

        try
        {
            actor.join(); //wait exit of its loop
        }
        catch( InterruptedException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Stops all actors of the system.
     */
    @Override
    public void stop()
    {
        Map<ActorRef<? extends Message>, Actor<? extends Message>> actors = getMap();

        for( Map.Entry<ActorRef<? extends Message>, Actor<? extends Message>> entry : actors.entrySet() )
        {
            stopActor( ( AbsActor ) entry.getValue() );
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
            stopActor( ( AbsActor ) getMap().get( actor ) );
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
            return null;
        }
    }
}
