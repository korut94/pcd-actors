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
package it.unipd.math.pcd.actors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> extends Thread implements Actor<T> {

    private MailBox<T,ActorRef<T>> mailBox_ = new ImpMailBox<>();
    private Lock lock_ = new ReentrantLock();
    private Condition working_;
    private boolean processed_ = true;

    public AbsActor()
    {
        working_ = lock_.newCondition();
        start();
    }

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    /**
     * Sets the sender-referece.
     *
     * @param sender The reference to actor sender
     * @return The actor.
     */
    protected final Actor<T> setSender( ActorRef<T> sender )
    {
        this.sender = sender;
        return this;
    }

    /**
     * Append message to the mailbox
     * @param message Message to storage
     * @param to Sender of message
     */
    public void post( T message, ActorRef to )
    {
        //Block synchronized to acquire monitor
        lock_.lock();

        if( processed_ )
        {
            mailBox_.append( message, to );
            working_.signal(); //wake up
        }

        lock_.unlock();
    }

    /**
     * Exit to the process messages loop
     */
    @Override
    public void interrupt()
    {
        lock_.lock();
        processed_ = false;
        lock_.unlock();

        super.interrupt();
    }

    /**
     * This function call author receive( T message ) for each message in the MailBox
     */
    @Override
    public void run()
    {
        while( processed_ )
        {
            try
            {
                lock_.lock();

                /**
                 * When one call signal of working is sure that there is at least one message in the mailbox
                 */
                if( mailBox_.isEmpty() )
                {
                    working_.await(); //Go to sleep thread and unlock lock
                }

                HeadMail<T,ActorRef<T>> head = mailBox_.pop();
                lock_.unlock();

                ActorRef<T> sender = head.getSender();
                T message = head.getMessage();

                setSender( sender );
                receive( message ); //attend conclusion of task
            }

            catch( InterruptedException e )
            {
                lock_.unlock();
            }
        }
    }
}
