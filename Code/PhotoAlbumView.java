import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PhotoAlbumView {
    private JFrame frame;
    private final JTextField photoNameField; // Field to input photo name
    private final JTextField filePathField; // Field to input file path
    private final JButton addButton, deleteButton;
    private final JButton sortByNameButton, sortByDateButton, sortBySizeButton;
    private final JButton nextButton, previousButton;
    private final JList<Photo> photoList; // List to display photos
    private final JLabel currentPhotoLabel; // Label to show the current photo name
    private final JLabel photoDisplayLabel; // Label to display the current photo (thumbnail or full image)
    private final DefaultListModel<Photo> photoListModel; // Model for the photo list

    public PhotoAlbumView() {
        // Initialize the frame
        frame = new JFrame("Photo Album Manager");
        frame.setLayout(new BorderLayout());

        // Initialize text fields for photo name and file path
        photoNameField = new JTextField(15);
        filePathField = new JTextField(15);

        // Initialize buttons
        addButton = new JButton("Add Photo");
        deleteButton = new JButton("Delete Photo");
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        sortByNameButton = new JButton("Sort By Name");
        sortByDateButton = new JButton("Sort By Date");
        sortBySizeButton = new JButton("Sort By Size");

        // Initialize photo list and model
        photoListModel = new DefaultListModel<>();
        photoList = new JList<>(photoListModel);
        photoList.setCellRenderer(new PhotoCellRenderer()); // Set custom cell renderer
        currentPhotoLabel = new JLabel("Current Photo: None");
        photoDisplayLabel = new JLabel(); // To display the current photo

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Photo Name:"));
        inputPanel.add(photoNameField);
        inputPanel.add(new JLabel("File Path:"));
        inputPanel.add(filePathField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        // Panel for displaying the current photo
        JPanel currentPhotoPanel = new JPanel(new BorderLayout());
        currentPhotoPanel.add(currentPhotoLabel, BorderLayout.NORTH);
        currentPhotoPanel.add(photoDisplayLabel, BorderLayout.CENTER);

        // Main panel configuration
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(photoList), BorderLayout.WEST); // Photo list on the left
        mainPanel.add(currentPhotoPanel, BorderLayout.CENTER); // Current photo on the right

        // Panel for sort and navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByDateButton);
        buttonPanel.add(sortBySizeButton);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        // Adding panels to the frame
        frame.add(inputPanel, BorderLayout.NORTH); // Input panel at the top
        frame.add(mainPanel, BorderLayout.CENTER); // Main panel in the center
        frame.add(buttonPanel, BorderLayout.SOUTH); // Button panel at the bottom

        // Frame settings
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Adding a component listener for resizing behavior
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updatePhotoDisplay();
            }
        });
    }

    // Method to update the photo display area on resizing
    private void updatePhotoDisplay() {
        if (photoDisplayLabel.getIcon() != null) {
            ImageIcon icon = (ImageIcon) photoDisplayLabel.getIcon();
            Image img = icon.getImage();
            // Resize the image to fill the display area, keeping the aspect ratio
            Image newImg = img.getScaledInstance(photoDisplayLabel.getWidth(), photoDisplayLabel.getHeight(), Image.SCALE_SMOOTH);
            photoDisplayLabel.setIcon(new ImageIcon(newImg));
        }
    }

    // Custom cell renderer to display the photo information
    private class PhotoCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Photo photo = (Photo) value;
            String text = photo.getName() + " - " + photo.getDateAdded() + " (" + photo.getFileSize() + " bytes)";
            JLabel label = (JLabel) super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            // Load and set the thumbnail image
            ImageIcon icon = new ImageIcon(photo.getFilePath());
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize to thumbnail
            label.setIcon(new ImageIcon(img));
            return label;
        }
    }

    // Method to get the photo name from the input field
    public String getPhotoName() {
        return photoNameField.getText();
    }

    // Method to get the file path from the input field
    public String getFilePath() {
        return filePathField.getText();
    }

    // Method to clear input fields
    public void clearInputFields() {
        photoNameField.setText("");
        filePathField.setText("");
    }

    // Method to add a photo to the list model
    public void addPhotoToList(Photo photo) {
        photoListModel.addElement(photo);
    }

    // Method to remove a photo from the list model
    public void removePhotoFromList(Photo photo) {
        photoListModel.removeElement(photo);
    }

    // Method to set the current photo label and display
    public void setCurrentPhoto(Photo photo) {
        if (photo != null) {
            currentPhotoLabel.setText("Current Photo: " + photo.getName());
            // Load the image
            ImageIcon icon = new ImageIcon(photo.getFilePath());
            Image img = icon.getImage(); // transform it
            Image newimg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH); // scale it smoothly
            photoDisplayLabel.setIcon(new ImageIcon(newimg)); // transform it back
            updatePhotoDisplay(); // Adjust the display on setting the photo
        } else {
            currentPhotoLabel.setText("Current Photo: None");
            photoDisplayLabel.setIcon(null);
        }
    }

    // Method to add listener for the add button
    public void addAddPhotoListener(ActionListener listenForAddButton) {
        addButton.addActionListener(listenForAddButton);
    }

    // Method to add listener for the delete button
    public void addDeletePhotoListener(ActionListener listenForDeleteButton) {
        deleteButton.addActionListener(listenForDeleteButton);
    }

    // Method to add listener for the next button
    public void addNextPhotoListener(ActionListener listenForNextButton) {
        nextButton.addActionListener(listenForNextButton);
    }

    // Method to add listener for the previous button
    public void addPreviousPhotoListener(ActionListener listenForPreviousButton) {
        previousButton.addActionListener(listenForPreviousButton);
    }

    // Method to add listener for sort by name button
    public void addSortByNameListener(ActionListener listenForSortByNameButton) {
        sortByNameButton.addActionListener(listenForSortByNameButton);
    }

    // Method to add listener for sort by date button
    public void addSortByDateListener(ActionListener listenForSortByDateButton) {
        sortByDateButton.addActionListener(listenForSortByDateButton);
    }

    // Method to add listener for sort by size button
    public void addSortBySizeListener(ActionListener listenForSortBySizeButton) {
        sortBySizeButton.addActionListener(listenForSortBySizeButton);
    }

    // Method to get the currently selected photo from the list
    public Photo getSelectedPhoto() {
        return photoList.getSelectedValue();
    }

    // Method to update the photo list model
    public void updatePhotoListModel() {
        photoList.repaint();
    }

    // Method to show a message dialog
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
