package it.unipd.math.pcd.actors;


/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public interface MailBox<T extends Message,U extends ActorRef<T>>
{
    boolean isEmpty();

    HeadMail<T,U> pop();

    void append( T message, U send );

    void clear();
}
