import java.io.File;
import java.util.Date;
import java.util.Iterator;

public final class PhotoAlbumController {
    private final PhotoAlbumModel model;
    private final PhotoAlbumView view;
    private Iterator<Photo> iterator; // Store the iterator to manage state

    public PhotoAlbumController(PhotoAlbumModel model, PhotoAlbumView view) {
        this.model = model;
        this.view = view;
        setupListeners(); // Setup listeners for the view
        refreshIterator(); // Initialize the iterator
    }

    public void setupListeners() {
        view.addAddPhotoListener(e -> addPhoto());
        view.addDeletePhotoListener(e -> deletePhoto());
        view.addNextPhotoListener(e -> nextPhoto());
        view.addPreviousPhotoListener(e -> previousPhoto());
        view.addSortByNameListener(e -> sortByName());
        view.addSortByDateListener(e -> sortByDate());
        view.addSortBySizeListener(e -> sortBySize());
    }

    private void addPhoto() {
        String name = view.getPhotoName();
        String path = view.getFilePath();
        
        System.out.println("Attempting to add photo: " + name + ", " + path); // Debugging output

        // Validate the input
        if (name.isEmpty() || path.isEmpty()) {
            view.showMessage("Photo name and file path cannot be empty.", "Error");
            return;
        }

        // Check if the file exists and is a valid image file
        File file = new File(path);
        if (!file.exists() || !file.isFile() || 
            (!path.endsWith(".jpg") && !path.endsWith(".png") && !path.endsWith(".jpeg"))) {
            view.showMessage("File does not exist or is not a valid image file. Please provide a valid file path.", "Error");
            return;
        }

        // Create a new Photo object
        Photo newPhoto = new Photo(name, path, new Date(), file.length());
        model.addPhoto(newPhoto);
        view.addPhotoToList(newPhoto);
        view.clearInputFields();
        refreshPhotoList(); // Update the photo list to reflect the newly added photo
        view.showMessage("Photo added successfully.", "Success");
    }

    private void deletePhoto() {
        Photo selectedPhoto = view.getSelectedPhoto();
        System.out.println("Attempting to delete photo: " + (selectedPhoto != null ? selectedPhoto.getName() : "None")); // Debugging output

        if (selectedPhoto == null) {
            view.showMessage("Please select a photo to delete.", "Error");
            return;
        }

        model.removePhoto(selectedPhoto);
        view.removePhotoFromList(selectedPhoto);
        refreshPhotoList(); // Update the list after deletion
        view.showMessage("Photo deleted successfully.", "Success");
    }

    private void nextPhoto() {
        if (iterator.hasNext()) {
            Photo nextPhoto = iterator.next();
            view.setCurrentPhoto(nextPhoto);
        } else {
            view.showMessage("You are already at the last photo.", "Info");
        }
    }

    private void previousPhoto() {
        if (iterator instanceof PhotoAlbumModel.AlbumIteratorImpl albumIterator) {
            if (albumIterator.hasPrevious()) {
                Photo previousPhoto = albumIterator.previous();
                view.setCurrentPhoto(previousPhoto);
            } else {
                view.showMessage("You are already at the first photo.", "Info");
            }
        }
    }

    private void sortByName() {
        model.sortPhotosByName();
        refreshPhotoList();
    }

    private void sortByDate() {
        model.sortPhotosByDate();
        refreshPhotoList();
    }

    private void sortBySize() {
        model.sortPhotosBySize();
        refreshPhotoList();
    }

    private void refreshPhotoList() {
        view.updatePhotoListModel(); // Ensure the list is refreshed
        refreshIterator(); // Reinitialize the iterator after sorting
        if (model.getPhotoCount() > 0) {
            view.setCurrentPhoto(model.getPhotoAt(0)); // Set the first photo as the current one
        } else {
            view.setCurrentPhoto(null); // No photo to display
        }
    }

    private void refreshIterator() {
        iterator = model.iterator(); // Reset the iterator after modifying the list
    }
}