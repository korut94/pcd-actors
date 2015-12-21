package it.unipd.math.pcd.actors.StackTest;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.Message;

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

    }
}
