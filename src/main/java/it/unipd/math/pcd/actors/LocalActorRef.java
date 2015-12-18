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
    private Condition notEmpty_;
    private boolean stop_ = false;

    public LocalActorRef(AbsActorSystem system )
    {
        system_ = system;
        notEmpty_ = lock_.newCondition();
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
    public void send( T message, ActorRef to )
    {
        lock_.lock();
        mailBox_.append( message, to );
        notEmpty_.signal(); //wake up
        lock_.unlock();
    }

    /**
     * Stop send message
     */
    public void stopSend()
    {
        stop_ = true;
    }

    /**
     * This function call author receive( T message ) for each message in the MailBox
     */
    @Override
    public void run()
    {
        try
        {
            while( !stop_ )
            {
                lock_.lock();
                /**
                 * I use if because when append to message in the MailBox not one remove it
                 */
                if( mailBox_.isEmpty() )
                {
                    notEmpty_.await(); //Go to the sleep thread and unlock lock
                }

                HeadMail<T,ActorRef<T>> head = mailBox_.pop();
                lock_.unlock();

                ActorRef<T> sender = head.getSender();
                T message = head.getMessage();

                AbsActor<T> actor = ( AbsActor<T> ) system_.dereferenceActor( sender );
                actor.setSender( sender );

                actor.receive( message ); //attend conclusion of task
            }
        }
        catch( InterruptedException e )
        {
            e.printStackTrace();
        }
        finally
        {
            lock_.unlock();
        }
    }
}
