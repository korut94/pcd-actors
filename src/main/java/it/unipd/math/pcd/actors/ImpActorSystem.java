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
    public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor, ActorMode mode ) {
        ActorRef ref = super.actorOf( actor, mode );

        Map<ActorRef<? extends Message>, Actor<? extends Message>> actors = getMapActors();

        executor.execute( ( AbsActor ) actors.get( ref ) );

        return ref;
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
            AbsActor absActor = ( AbsActor ) actors.get( actor );

            //System.out.println( absActor );

            if( absActor == null ) {
                throw new NoSuchActorException();
            } else {
                actors.remove( actor );
                executor.stop( absActor );
            }
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
