package it.unipd.math.pcd.actors.StackTest;

import it.unipd.math.pcd.actors.Message;

/**
 * Created by amantova on 21/12/15.
 */
public class ResponseMessage implements Message
{
    private int payload_ = 0;

    public ResponseMessage( int payload )
    {
        payload_ = payload;
    }

    public int extract()
    {
        return payload_;
    }
}
