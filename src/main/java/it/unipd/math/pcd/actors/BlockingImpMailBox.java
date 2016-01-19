package it.unipd.math.pcd.actors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Versione thread-safe of ImpMailBox.
 *
 *  @author Andrea Mantovani
 */
public class BlockingImpMailBox <T extends Message,U extends ActorRef<T>> extends ImpMailBox<T,U> {

    private Lock lock_ = new ReentrantLock();
    private Condition working_;

    public BlockingImpMailBox() {
        working_ = lock_.newCondition();
    }

    @Override
    public void append( T message, U send ) {
        lock_.lock();

        super.append( message, send );
        working_.signal();

        lock_.unlock();
    }

    @Override
    public HeadMail<T,U> pop() throws InterruptedException{
        lock_.lock();

        if ( super.isEmpty() ) {
            working_.await();
        }

        HeadMail<T,U> head = super.pop();
        lock_.unlock();

        return head;
    }
}
