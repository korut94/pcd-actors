package it.unipd.math.pcd.actors.LoopTest;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by amantova on 22/12/15.
 */
public class LoopActor extends AbsActor<Message>
{
    private static int _i = 0;
    private int id_ = _i;

    public LoopActor()
    {
        _i++;
        id_++;
    }

    @Override
    public void receive( Message message )
    {
        if( message instanceof ResendMessage )
        {
            try
            {
                Thread.sleep( 100 );
                System.out.println( "Receive " + id_ );
            }
            catch( InterruptedException e )
            {
            }

            self.send( message, sender );
        }
    }
}
