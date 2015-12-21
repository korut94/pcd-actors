/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors.StackTest;

import it.unipd.math.pcd.actors.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases about {@link ActorRef} type.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class ActorRefTest {

    @Test
    public void simpleTest() {
        // FIXME This is a simple (and definitely useless) example test
        //       that has to be substituted with something more useful
        Assert.assertTrue(true);
    }

    @Test
    public void sendAndReceive()
    {
        System.out.println( "In sendAndReceive test:" );
        ImpActorSystem system = new ImpActorSystem();
        ActorRef<Message> ref = ( ActorRef<Message> ) system.actorOf( StackActor.class, ActorSystem.ActorMode.LOCAL );

        /*
        ActorRef<Message> printer = ( ActorRef<Message> ) system.actorOf( PrintActor.class, ActorSystem.ActorMode.LOCAL );
        printer.send( new PushMessage( 5 ), ref );
        printer.send( new PushMessage( 4 ), ref );
        printer.send( new PushMessage( 3 ), ref );
        printer.send( new PushMessage( 6 ), ref );
        printer.send( new PopMessage(), ref );
        printer.send( new PopMessage(), ref );
        printer.send( new PushMessage( 10 ), ref );
        printer.send( new PushMessage( 5 ), ref );
        printer.send( new PopMessage(), ref );
        printer.send( new PopMessage(), ref );
        printer.send( new PopMessage(), ref );
        printer.send( new PushMessage( 3 ), ref );
        printer.send( new PushMessage( 6 ), ref );
        */

        int bound = 10;

        ActorRef<Message>[] printers = new ActorRef[bound];
        for( int i = 0; i < bound; i++ )
        {
            printers[i] = ( ActorRef<Message> ) system.actorOf( PrintActor.class, ActorSystem.ActorMode.LOCAL );
            printers[i].send( new PushMessage( i ), ref );
        }

        for( int j = 0; j < bound; j += 2)
        {
            printers[j].send( new PopMessage(), ref );
        }

        //wait completely all actor's task
        try {
            Thread.sleep( 100 );
        }
        catch( InterruptedException e )
        {

        }
    }
}





