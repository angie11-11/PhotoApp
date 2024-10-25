import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PhotoAlbumModel implements Iterable<Photo> {
    private final List<Photo> photos; // List to store photos

    public PhotoAlbumModel() {
        photos = new ArrayList<>();
    }

    // Method to add a photo to the album
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    // Method to remove a photo from the album
    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    // Method to get the index of a photo
    public int getPhotoIndex(Photo photo) {
        return photos.indexOf(photo);
    }

    // Method to get the total count of photos
    public int getPhotoCount() {
        return photos.size();
    }

    // Method to get a photo at a specific index
    public Photo getPhotoAt(int index) {
        return photos.get(index);
    }

    // Method to sort photos by name
    public void sortPhotosByName() {
        Collections.sort(photos, Comparator.comparing(Photo::getName));
    }

    // Method to sort photos by date added
    public void sortPhotosByDate() {
        Collections.sort(photos, Comparator.comparing(Photo::getDateAdded));
    }

    // Method to sort photos by size
    public void sortPhotosBySize() {
        Collections.sort(photos, Comparator.comparingLong(Photo::getFileSize));
    }

    @Override
    public Iterator<Photo> iterator() {
        return new AlbumIteratorImpl(); // Ensure the iterator returns the correct type
    }

    // Inner class for AlbumIterator implementation
    public class AlbumIteratorImpl implements Iterator<Photo> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < photos.size();
        }

        @Override
        public Photo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return photos.get(currentIndex++);
        }

        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        public Photo previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return photos.get(--currentIndex);
        }

        public Photo current() {
            if (currentIndex < 0 || currentIndex >= photos.size()) {
                throw new NoSuchElementException();
            }
            return photos.get(currentIndex);
        }
    }
}
