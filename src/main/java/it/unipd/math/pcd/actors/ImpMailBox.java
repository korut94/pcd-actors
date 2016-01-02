package it.unipd.math.pcd.actors;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public final class ImpMailBox<T extends Message,U extends ActorRef<T>> extends Thread implements MailBox<T,U>
{
    private LinkedBlockingQueue<HeadMail<T,U>> queue_ = new LinkedBlockingQueue<>();
    private AbsActor<T> actor_;
    private boolean processed_ = true;

    public ImpMailBox( AbsActor<T> actor )
    {
        actor_ = actor;
    }

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
        if( processed_ )
        {
            queue_.offer( new HeadMail<>( message, send ) );
        }
    }

    /**
     * Get the head message in the queue
     * @return message extract
     */
    @Override
    public HeadMail<T,U> pop()
    {
        return queue_.poll();
    }

    /**
     * Removes all of the elements from this MailBox
     */
    @Override
    public void clear()
    {
        queue_.clear();
    }

    /**
     * Stop daemon that it processed messages
     */
    @Override
    public void interrupt()
    {
        processed_ = false;
        super.interrupt();
    }

    /**
     * This function call author receive( T message ) for each message in the MailBox
     */
    @Override
    public void run()
    {
        while( processed_ )
        {
            try
            {
                HeadMail<T, U> head = queue_.take();

                actor_.sender = head.getSender();
                actor_.receive( head.getMessage() ); //attend conclusion of task
            }
            catch( InterruptedException e )
            {
            }
        }

        //Remove all message for don't create garbage
        clear();
    }
}
