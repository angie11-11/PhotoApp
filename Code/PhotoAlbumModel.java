import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * PhotoAlbumModel represents a collection of photos with functionality
 * to add, remove, and retrieve photos, as well as to sort and iterate over them.
 */
public class PhotoAlbumModel implements Iterable<Photo> {
    private final List<Photo> photos; // List to store photos
    private final List<ChangeListener> listeners; // List to store registered listeners

    /**
     * Constructs an empty PhotoAlbumModel.
     */
    public PhotoAlbumModel() {
        photos = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * Adds a photo to the album and notifies observers.
     *
     * @param photo the Photo object to add
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
        notifyChangeListeners();  
    }

    /**
     * Removes a photo from the album and notifies observers.
     *
     * @param photo the Photo object to remove
     */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
        notifyChangeListeners();  
    }

    /**
     * Registers a new change listener.
     *
     * @param listener the ChangeListener to add
     */
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners of a change.
     */
    private void notifyChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }

    /**
     * Retrieves all photos in the album.
     *
     * @return a list containing all Photo objects in the album
     */
    public List<Photo> getAllPhotos() {
        return new ArrayList<>(photos); // Return a copy of the photos list
    }

    /**
     * Retrieves the total count of photos in the album.
     *
     * @return the number of photos
     */
    public int getPhotoCount() {
        return photos.size();
    }

    /**
     * Retrieves a photo at the specified index.
     *
     * @param index the index of the photo
     * @return the Photo object at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Photo getPhotoAt(int index) {
        if (index < 0 || index >= photos.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return photos.get(index);
    }

    /**
     * Sorts the photos in the album by name and notifies observers.
     */
    public void sortPhotosByName() {
        Collections.sort(photos, Comparator.comparing(Photo::getName));
        notifyChangeListeners();
    }

    /**
     * Sorts the photos in the album by the date they were added and notifies observers.
     */
    public void sortPhotosByDate() {
        Collections.sort(photos, Comparator.comparing(Photo::getDateAdded));
        notifyChangeListeners();
    }

    /**
     * Sorts the photos in the album by file size and notifies observers.
     */
    public void sortPhotosBySize() {
        Collections.sort(photos, Comparator.comparingLong(Photo::getFileSize));
        notifyChangeListeners();
    }

    @Override
    public Iterator<Photo> iterator() {
        return new AlbumIteratorImpl();
    }

    /**
     * AlbumIteratorImpl is an iterator for the photo album, allowing
     * navigation through photos with forward and backward movement.
     */
    public class AlbumIteratorImpl implements Iterator<Photo> {
        private int currentIndex = 0;

        /**
         * Checks if there is a next photo in the album.
         *
         * @return true if there is a next photo, false otherwise
         */
        @Override
        public boolean hasNext() {
            return currentIndex < photos.size();
        }

        /**
         * Moves to the next photo in the album.
         *
         * @return the next Photo object
         * @throws NoSuchElementException if there is no next photo
         */
        @Override
        public Photo next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more photos available.");
            }
            return photos.get(currentIndex++);
        }

        /**
         * Checks if there is a previous photo in the album.
         *
         * @return true if there is a previous photo, false otherwise
         */
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        /**
         * Moves to the previous photo in the album.
         *
         * @return the previous Photo object
         * @throws NoSuchElementException if there is no previous photo
         */
        public Photo previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No previous photo available.");
            }
            return photos.get(--currentIndex);
        }

        /**
         * Retrieves the current photo in the album without advancing the position.
         *
         * @return the current Photo object
         * @throws NoSuchElementException if there is no current photo
         */
        public Photo current() {
            if (currentIndex <= 0 || currentIndex > photos.size()) {
                throw new NoSuchElementException("Current photo is not available.");
            }
            return photos.get(currentIndex - 1);
        }
    }
}
