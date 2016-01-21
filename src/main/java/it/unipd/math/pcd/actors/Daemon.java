package it.unipd.math.pcd.actors;

/**
 * Abstract Daemon to implement recall task until the ActorSystem stopped its
 *
 * @author Andrea Mantovani
 */
public abstract class Daemon implements Runnable {
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

    /**
     * The before of loop
     */
    public abstract void before();

    @Override
    public void run() {
        before();

        while ( condition() ) {
            loop();
        }

        forward();
    }
}
