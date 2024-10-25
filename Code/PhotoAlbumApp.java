public class PhotoAlbumApp {
    public static void main(String[] args) {
        // Create the model, view, and controller
        PhotoAlbumModel model = new PhotoAlbumModel();
        PhotoAlbumView view = new PhotoAlbumView();
        PhotoAlbumController controller = new PhotoAlbumController(model, view);

        // Set up listeners in the controller
        controller.setupListeners();
    }
}
