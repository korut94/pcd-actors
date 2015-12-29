package it.unipd.math.pcd.actors;

/**
 * Created by amantova on 16/12/15.
 */
public final class LocalActorRef<T extends Message> extends ImpActorRef<T>
{
    public LocalActorRef( AbsActorSystem system )
    {
        super( system );
    }

    /**
     * Append the message to sender at actor and waik up thread
     * @param message The message to send
     * @param to The actor to which sending the message
     */
    @Override
    public void send( T message, ActorRef to )
    {
        AbsActor actor = ( AbsActor ) system_.dereferenceActor( to );
        actor.post( message, this );
    }
}
