package it.unipd.math.pcd.actors;



/**
 *  @author Andrea Mantovani
 */
public class MailBoxDaemon<T extends Message> extends Daemon {
    private MailBox<T,ActorRef<T>> mailBox_;
    private AbsActor<T> actor_;
    private Thread worker_;
    private boolean processed_ = true;

    public MailBoxDaemon( AbsActor<T> actor ) {
        actor_ = actor;
        mailBox_ = actor.getMailBox();
    }

    public void stop() {
        processed_ = false;

        if( worker_ != null ) {
            worker_.interrupt();
        }
    }

    @Override
    public boolean condition() {
        /**
         * When actorySystem has stopped a actor it is going to process
         * messages until the mailbox is not empty
         */
        return ( processed_ || !mailBox_.isEmpty() );
    }

    /**
     * This function call author receive( T message ) for each message in the MailBox
     */
    @Override
    public void loop() {
        try {
            HeadMail<T,ActorRef<T>> head = mailBox_.pop();
            actor_.sender = head.getSender();
            //attend conclusion of task
            actor_.receive( head.getMessage() );

        } catch ( InterruptedException e ) {}
    }

    /**
     * Nothing to do
     */
    @Override
    public void forward() {}

    /**
     * Capture the current thread
     */
    @Override
    public void before() {
        worker_ = Thread.currentThread();
    }
}
