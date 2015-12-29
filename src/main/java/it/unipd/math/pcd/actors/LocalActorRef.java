package it.unipd.math.pcd.actors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by amantova on 16/12/15.
 */
public final class LocalActorRef<T extends Message> extends Thread implements ActorRef<T>
{
    private AbsActorSystem system_;
    private MailBox<T,ActorRef<T>> mailBox_ = new ImpMailBox<>();
    private Lock lock_ = new ReentrantLock();
    private Condition working_;
    private boolean processed_ = true;

    public LocalActorRef( AbsActorSystem system )
    {
        system_ = system;
        working_ = lock_.newCondition();
    }

    /**
     * Append message to the mailbox
     * @param message Message to storage
     * @param to Sender of message
     */
    private void post( T message, ActorRef to )
    {
        //Block synchronized to acquire monitor
        lock_.lock();

        if( processed_ )
        {
            mailBox_.append( message, to );
            working_.signal(); //wake up
        }

        lock_.unlock();
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
        /**
         * Email send to the mailbox of ActorRef 'to'
         */
        ( ( LocalActorRef<T> ) to ).post( message, this ); //override???
    }

    /**
     * Exit to the process messages loop
     */
    @Override
    public void interrupt()
    {
        lock_.lock();
        processed_ = false;
        lock_.unlock();

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
                lock_.lock();

                /**
                 * When one call signal of working is sure that there is at least one message in the mailbox
                 */
                if( mailBox_.isEmpty() )
                {
                    working_.await(); //Go to sleep thread and unlock lock
                }

                HeadMail<T,ActorRef<T>> head = mailBox_.pop();
                lock_.unlock();

                ActorRef<T> sender = head.getSender();
                T message = head.getMessage();

                AbsActor<T> actor = ( AbsActor<T> ) system_.dereferenceActor( this );

                actor.setSender( sender );
                actor.receive( message ); //attend conclusion of task
            }

            catch( InterruptedException e )
            {
                lock_.unlock();
            }
        }
    }
}
