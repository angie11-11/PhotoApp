import java.io.File;
import java.util.Date;

/**
 * PhotoAlbumController manages interactions between the PhotoAlbumModel and PhotoAlbumView.
 * It handles user actions such as adding, deleting, and navigating photos, as well as sorting.
 */
public final class PhotoAlbumController {
    private final PhotoAlbumModel model;
    private final PhotoAlbumView view;
    private PhotoAlbumModel.AlbumIteratorImpl iterator; // Iterator to navigate photos

    /**
     * Constructs a controller with the given model and view, setting up listeners
     * and initializing the iterator.
     *
     * @param model the PhotoAlbumModel instance
     * @param view  the PhotoAlbumView instance
     */
    public PhotoAlbumController(PhotoAlbumModel model, PhotoAlbumView view) {
        this.model = model;
        this.view = view;
        setupListeners(); // Setup listeners for the view
        refreshIterator(); // Initialize the iterator
    }

    /**
     * Sets up listeners for the view to handle user actions.
     */
    public void setupListeners() {
        view.addAddPhotoListener(e -> addPhoto());
        view.addDeletePhotoListener(e -> deletePhoto());
        view.addNextPhotoListener(e -> nextPhoto());
        view.addPreviousPhotoListener(e -> previousPhoto());
        view.addSortByNameListener(e -> sortByName());
        view.addSortByDateListener(e -> sortByDate());
        view.addSortBySizeListener(e -> sortBySize());
    }

    // Private helper methods to handle specific actions:

    /**
     * Adds a new photo to the album, validating input and updating the model and view.
     */
    private void addPhoto() {
        String name = view.getPhotoName();
        String path = view.getFilePath();

        if (name.isEmpty() || path.isEmpty()) {
            view.showMessage("Photo name and file path cannot be empty.", "Error");
            return;
        }

        File file = new File(path);
        if (!file.exists() || !file.isFile() || 
            (!path.endsWith(".jpg") && !path.endsWith(".png") && !path.endsWith(".jpeg"))) {
            view.showMessage("File does not exist or is not a valid image file. Please provide a valid file path.", "Error");
            return;
        }

        Photo newPhoto = new Photo(name, path, new Date(), file.length());
        model.addPhoto(newPhoto);
        view.addPhotoToList(newPhoto);
        view.clearInputFields();
        refreshPhotoList();
        view.showMessage("Photo added successfully.", "Success");
    }

    /**
     * Deletes the selected photo from the album, updating the model and view.
     */
    private void deletePhoto() {
        Photo selectedPhoto = view.getSelectedPhoto();
        if (selectedPhoto == null) {
            view.showMessage("Please select a photo to delete.", "Error");
            return;
        }

        model.removePhoto(selectedPhoto);
        view.removePhotoFromList(selectedPhoto);
        refreshPhotoList();
        view.showMessage("Photo deleted successfully.", "Success");
    }

    /**
     * Advances to the next photo in the album and updates the view.
     */
    private void nextPhoto() {
        if (iterator.hasNext()) {
            Photo nextPhoto = iterator.next();
            view.setCurrentPhoto(nextPhoto);
        } else {
            view.showMessage("You are already at the last photo.", "Info");
        }
    }

    /**
     * Moves to the previous photo in the album and updates the view.
     */
    private void previousPhoto() {
        if (iterator.hasPrevious()) {
            Photo previousPhoto = iterator.previous();
            view.setCurrentPhoto(previousPhoto);
        } else {
            view.showMessage("You are already at the first photo.", "Info");
        }
    }

    /**
     * Sorts photos by name and refreshes the list in the view.
     */
    private void sortByName() {
        model.sortPhotosByName();
        refreshPhotoList();
    }

    /**
     * Sorts photos by date and refreshes the list in the view.
     */
    private void sortByDate() {
        model.sortPhotosByDate();
        refreshPhotoList();
    }

    /**
     * Sorts photos by file size and refreshes the list in the view.
     */
    private void sortBySize() {
        model.sortPhotosBySize();
        refreshPhotoList();
    }

    /**
     * Updates the photo list model in the view and reinitializes the iterator.
     */
    private void refreshPhotoList() {
        view.updatePhotoListModel(model.getAllPhotos());
        refreshIterator();

        if (model.getPhotoCount() > 0) {
            Photo firstPhoto = model.getPhotoAt(0);
            view.setCurrentPhoto(firstPhoto);
        } else {
            view.setCurrentPhoto(null);
        }
    }

    /**
     * Reinitializes the photo iterator after modifications.
     */
    private void refreshIterator() {
        iterator = model.new AlbumIteratorImpl();
    }
}
