package it.unipd.math.pcd.actors;

import java.util.LinkedList;

/**
 * Created by amantova on 16/12/15.
 */
public final class ImpMailBox<T extends Message,U extends ActorRef<T>> implements MailBox<T,U>
{
    private LinkedList<HeadMail<T,U>> queue_ = new LinkedList<>();
    /**
     * Check if queue_ is empty
     * @return True if size of queue_ is 0, other False
     */
    @Override
    public boolean isEmpty()
    {
        return ( queue_.size() == 0 );
    }

    /**
     * Store message in the tail of queue
     * @param message The message of storage in the queue
     */
    @Override
    public void append( T message, U send )
    {
        queue_.addLast( new HeadMail<>( message, send ) );
    }

    /**
     * Get the head message in the queue
     * @return message extract
     */
    @Override
    public HeadMail<T,U> pop()
    {
        return queue_.pollFirst();
    }
}
