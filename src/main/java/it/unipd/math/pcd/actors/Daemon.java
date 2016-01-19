package it.unipd.math.pcd.actors;

import java.util.concurrent.Callable;

/**
 * Abstract Daemon to implement recall task until the ActorSystem stopped its
 *
 * @author Andrea Mantovani
 */
public abstract class Daemon implements Callable<Void> {
    /**
     *  Defines until the daemon run.
     *
     * @return Value of condition checked inside
     */
    public abstract boolean condition();

    /**
     * Logic executed in the loop.
     */
    public abstract void loop();

    /**
     * The forward of loop's end.
     */
    public abstract void forward();

    @Override
    public Void call() {
        while ( condition() ) {
            loop();
        }

        forward();
        return null;
    }
}
