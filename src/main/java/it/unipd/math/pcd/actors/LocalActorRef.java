package it.unipd.math.pcd.actors;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by amantova on 16/12/15.
 */
public final class LocalActorRef<T extends Message> implements ActorRef<T>
{
    private AbsActorSystem system_;

    public LocalActorRef( AbsActorSystem system )
    {
        system_ = system;
    }

    /**
     * Check if actorRef references is same
     * @param other ActorRef
     * @return return 0 if same else -1
     */
    @Override
    public int compareTo( ActorRef other )
    {
        return ( this == other ) ? 0 : -1;
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
