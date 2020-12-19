package ooga.view.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import ooga.controller.util.ResourceUtil;
import ooga.view.MonopolyView;
import ooga.view.alerts.ErrorAlert;

/**
 * Factory to create the Menu of the game with options to change the view of the board/game
 *
 * @author Isabella Knox, Hosam Tageldin
 */
public class MenuFactory {

  private MenuBar gameMenuBar;
  private final ResourceBundle menuBundle;
  private static final String PATH = "properties/FrontEndMenu";
  private final MonopolyView myView;

  public MenuFactory(MonopolyView view) {
    menuBundle = ResourceBundle.getBundle(PATH);
    this.myView = view;
    createMenuBar();
  }

  /**
   * @return the MenuBar to be displayed on the game screen
   */
  public MenuBar getGameMenuBar() {
    return gameMenuBar;
  }

  private void createMenuBar() {
    Menu gameMenu = createMenu();
    gameMenuBar = new MenuBar(gameMenu);
  }

  private Menu createMenu() {
    Menu gameMenu = new Menu(ResourceUtil.getResourceValue("MenuLabel"));
    String[] menuOptions = menuBundle.getString("Headers").split(",");
    for (String option : menuOptions) {
      Menu menu = new Menu(ResourceUtil.getResourceValue(option));
      addSubMenu(menu, convert(menuBundle.getString(option)));
      addAction(menu);
      gameMenu.getItems().add(menu);
    }
    return gameMenu;
  }

  private void addAction(Menu gameMenuItem) {
    for (MenuItem item : gameMenuItem.getItems()) {
      item.setOnAction(event -> {
        try {
          Method m = this.myView.getClass()
              .getDeclaredMethod(menuBundle.getString(item.getText()), String.class);
          m.invoke(this.myView, item.getText());
        } catch (Exception e) {
          ErrorAlert alert = new ErrorAlert();
          alert.createErrorAlert(e.getMessage());
        }
      });
    }
  }

  private List<RadioMenuItem> convert(String string) {
    String[] eachString = string.split(",");
    List<RadioMenuItem> ret = new ArrayList<>();
    for (String str : eachString) {
      ret.add(new RadioMenuItem(str));
    }
    return ret;
  }

  private void addSubMenu(Menu gameMenuItem, List<RadioMenuItem> options) {
    ToggleGroup toggleGroup = new ToggleGroup();
    for (int i = 0; i < options.size(); i++) {
      toggleGroup.getToggles().add(options.get(i));
      gameMenuItem.getItems().add(options.get(i));
    }
    toggleGroup.selectToggle(toggleGroup.getToggles().get(0));
  }

}
