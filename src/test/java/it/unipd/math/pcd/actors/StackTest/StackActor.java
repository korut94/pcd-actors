package it.unipd.math.pcd.actors.StackTest;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.Message;
import it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException;

import java.util.Stack;

/**
 * Created by amantova on 21/12/15.
 */
public class StackActor extends AbsActor<Message>
{
    private Stack<Integer> stack_ = new Stack<>();

    @Override
    public void receive( Message message )
    {
        if( message instanceof PopMessage )
        {
            if( stack_.size() > 0 )
            {
                self.send( new ResponseMessage( stack_.pop() ), sender );
            }
        }

        else if( message instanceof PushMessage )
        {
            PushMessage msg = ( PushMessage ) message;

            stack_.push( msg.extract() );
            System.out.println( stack_.toString() );
        }

        else throw new UnsupportedMessageException( message );
    }
}
