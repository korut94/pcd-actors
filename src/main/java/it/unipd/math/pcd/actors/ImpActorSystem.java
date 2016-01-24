package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.Map;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public final class ImpActorSystem extends AbsActorSystem
{
    private ExecutorActor executor = new ExecutorActor();

    @Override
    public Actor<? extends Message> dereferenceActor( ActorRef ref ) {
        AbsActor actor = ( AbsActor ) super.dereferenceActor( ref );

        if ( !executor.isExecuted( actor ) ) {
            executor.execute( actor );
        }

        return actor;
    }

    /**
     * Stops all actors of the system.
     */
    @Override
    public void stop()
    {
        Map<ActorRef<? extends Message>, Actor<? extends Message>> actors = getMapActors();

        for( Map.Entry<ActorRef<? extends Message>, Actor<? extends Message>> entry : actors.entrySet() )
        {
            actors.remove( entry.getKey() );
            executor.stop( ( AbsActor ) entry.getValue() );
        }

        executor.close();
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
            Map<ActorRef<? extends Message>, Actor<? extends Message>> actors = getMapActors();
            AbsActor absActor = ( AbsActor ) actors.remove( actor );

            if( absActor == null ) {
                throw new NoSuchActorException();
            }

            executor.stop( absActor );
        }
    }

    @Override
    protected ActorRef createActorReference( ActorMode mode )
    {
        if( mode == ActorMode.LOCAL )
        {
            return new LocalActorRef<>( this );
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
