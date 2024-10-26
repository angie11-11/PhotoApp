/**
 * AlbumIterator is an interface for navigating through a collection of photos.
 */
public interface AlbumIterator {

    /**
     * Checks if there is a next photo in the collection.
     *
     * @return true if there is a next photo, false otherwise
     */
    boolean hasNext();

    /**
     * Checks if there is a previous photo in the collection.
     *
     * @return true if there is a previous photo, false otherwise
     */
    boolean hasPrevious();

    /**
     * Retrieves the photo at the current position in the collection.
     *
     * @return the current Photo object
     */
    Photo current();

    /**
     * Advances the iterator to the next position and returns the next photo.
     *
     * @return the next Photo object in the collection
     */
    Photo next();

    /**
     * Moves the iterator to the previous position and returns the previous photo.
     *
     * @return the previous Photo object in the collection
     */
    Photo previous();
}
