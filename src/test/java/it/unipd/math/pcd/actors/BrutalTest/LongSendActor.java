package it.unipd.math.pcd.actors.BrutalTest;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by amantova on 20/01/16.
 */
public class LongSendActor extends AbsActor<Message>
{
    @Override
    public void receive( Message message ) {
        if ( message instanceof LongWaitMessage ) {
            try {
                System.out.println( "Start" );
                Thread.sleep( 10000 );
                System.out.println( "End" );
            } catch( InterruptedException e ) {
                System.out.println( "Muori" );
            }
        }
    }
}
