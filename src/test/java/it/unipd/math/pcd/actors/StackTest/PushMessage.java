package it.unipd.math.pcd.actors.StackTest;

import it.unipd.math.pcd.actors.Message;

/**
 * Created by amantova on 21/12/15.
 */
public class PushMessage implements Message
{
    private int payload_ = 0;

    public PushMessage( int payload )
    {
        payload_ = payload;
    }

    public int extract()
    {
        return payload_;
    }
}
