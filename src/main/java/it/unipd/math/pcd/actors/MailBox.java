package it.unipd.math.pcd.actors;


/**
 * Created by amantova on 16/12/15.
 */
public interface MailBox<T extends Message,U extends ActorRef<T>>
{
    boolean isEmpty();

    HeadMail<T,U> pop();
    void append( T message, U send );
}
