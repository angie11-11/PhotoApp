/**
 * The main class for the photo album application. Initializes the model, view,
 * and controller, and sets up event listeners.
 */
public class PhotoAlbumApp {

    /**
     * Main method that starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        PhotoAlbumModel model = new PhotoAlbumModel();
        PhotoAlbumView view = new PhotoAlbumView();
        PhotoAlbumController controller = new PhotoAlbumController(model, view);

        controller.setupListeners();
    }
}
