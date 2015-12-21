package it.unipd.math.pcd.actors.StackTest;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by amantova on 21/12/15.
 */
public class PrintActor extends AbsActor<Message>
{
    private static int _idGlobal = 0;
    private int id_ = 0;

    public PrintActor()
    {
        _idGlobal++;
        id_ = _idGlobal;
    }

    @Override
    public void receive( Message message )
    {
        if( message instanceof SendMessage )
        {
            self.send( new PushMessage( id_ ), sender );
        }

        else if( message instanceof ResponseMessage )
        {
            System.out.println( ( ( ResponseMessage ) message ).extract() );
        }
    }
}
