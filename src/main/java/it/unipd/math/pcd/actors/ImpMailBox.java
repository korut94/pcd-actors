package it.unipd.math.pcd.actors;

import java.util.LinkedList;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
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

    /**
     * Removes all of the elements from this MailBox
     */
    @Override
    public void clear()
    {
        queue_.clear();
    }
}
