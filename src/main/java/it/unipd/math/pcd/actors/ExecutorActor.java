package it.unipd.math.pcd.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Andrea Mantovani
 */
public class ExecutorActor {
    private Map<Actor<?>,MailBoxDaemon<?>> mailBoxes_ = new HashMap<>();
    private Map<Actor<?>,Future> daemons_ = new HashMap<>();
    private ExecutorService executor_ = Executors.newCachedThreadPool();


    public boolean isExecuted( AbsActor actor ) {
        return mailBoxes_.containsKey( actor ) ;
    }

    /**
     *
     * @param actor
     */
    public void execute( AbsActor actor ) {
        MailBoxDaemon<?> mailBoxDaemon = new MailBoxDaemon<>( actor );

        mailBoxes_.put( actor, mailBoxDaemon );
        daemons_.put( actor, executor_.submit( mailBoxDaemon ) );
    }


    public void close() { executor_.shutdown(); }


    /**
     *
     * @param actor
     */
    public void stop( AbsActor actor ) {
        if ( isExecuted( actor ) ) {
            mailBoxes_.remove( actor ).stop();

            try {
                daemons_.remove( actor ).get();

            } catch ( ExecutionException | InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }
}
