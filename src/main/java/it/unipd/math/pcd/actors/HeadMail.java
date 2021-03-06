package it.unipd.math.pcd.actors;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public class HeadMail<T extends Message, U extends ActorRef<T>>
{
    T message_;
    U sender_;

    public HeadMail( T message, U sender )
    {
        message_ = message;
        sender_ = sender;
    }

    public T getMessage()
    {
        return message_;
    }

    public U getSender()
    {
        return sender_;
    }
}
