package it.unipd.math.pcd.actors;

/**
 * Versione thread-safe of ImpMailBox.
 *
 *  @author Andrea Mantovani
 */
public class BlockingImpMailBox <T extends Message,U extends ActorRef<T>> extends ImpMailBox<T,U> {

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }


    @Override
    public synchronized void append( T message, U send ) {
        super.append( message, send );
        notify();
    }

    @Override
    public synchronized HeadMail<T,U> pop() throws InterruptedException{
        if ( super.isEmpty() ) {
            wait();
        }

        return super.pop();
    }
}
