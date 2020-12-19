package ooga.view.gamedisplay;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creates the icon views of the players in the game based off selected icons in the splash screen
 *
 * @author Hosam Tageldin
 */
public class IconView {

  private final List<ImageView> iconImageView;
  private final List<String> iconList;
  private final static int ICON_HEIGHT = 30;

  public IconView(List<String> icons) {
    this.iconList = icons;
    iconImageView = new ArrayList<>();
    createIconImageViews();
  }

  /**
   * @return the list of Icons in the same order as the list of players
   */
  public List<ImageView> getIcons() {
    return iconImageView;
  }

  private void createIconImageViews() {
    int i = 0;
    for (String icon : iconList) {
      ImageView img = new ImageView(
          new Image(this.getClass().getClassLoader().getResourceAsStream(icon)));
      img.setId("playerView" + i);
      i++;
      img.setFitHeight(ICON_HEIGHT);
      img.setPreserveRatio(true);
      iconImageView.add(img);
    }

  }
}
