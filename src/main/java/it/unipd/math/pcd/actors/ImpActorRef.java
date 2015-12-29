package it.unipd.math.pcd.actors;

/**
 * @author Andrea Mantovani
 * @version 1.0
 * @since 1.0
 */
public abstract class ImpActorRef<T extends Message> implements ActorRef<T>
{
    protected AbsActorSystem system_;

    public ImpActorRef( AbsActorSystem system )
    {
        system_ = system;
    }

    /**
     * Check if actorRef references is same
     * @param other ActorRef
     * @return return 0 if same else -1
     */
    @Override
    public int compareTo( ActorRef other )
    {
        return ( this == other ) ? 0 : -1;
    }
}
