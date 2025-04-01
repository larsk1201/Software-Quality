import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * <p>De klasse voor een Bitmap item</p>
 * <p>Bitmap items hebben de verantwoordelijkheid om zichzelf te tekenen.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class BitmapItem extends SlideItem {

  private BufferedImage bufferedImage;
  private String imageName;

  protected static final String FILE = "Bestand ";
  protected static final String NOTFOUND = " niet gevonden";

  // level staat voor het item-level; name voor de naam van het bestand met de afbeelding
  public BitmapItem(int level, String name) {
    super(level);
    imageName = name;
    try {
      bufferedImage = ImageIO.read(new File(imageName));
    } catch (IOException e) {
      System.err.println(FILE + imageName + NOTFOUND);
    }
  }

  // Een leeg bitmap-item
  public BitmapItem() {
    this(0, null);
  }

  public BufferedImage getBufferedImage() {
    return this.bufferedImage;
  }

  public void setBufferedImage(BufferedImage bufferedImage) {
    this.bufferedImage = bufferedImage;
  }

  public String getImageName() {
    return this.imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public BitmapItem(int lev, BufferedImage bufferedImage, String imageName) {
    super(lev);
    this.bufferedImage = bufferedImage;
    this.imageName = imageName;
  }

  public BitmapItem(BufferedImage bufferedImage, String imageName) {
    this.bufferedImage = bufferedImage;
    this.imageName = imageName;
  }

  // geef de bestandsnaam van de afbeelding
  public String getName() {
    return imageName;
  }

  // geef de bounding box van de afbeelding
  public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
    return new Rectangle((int) (myStyle.indent * scale), 0,
        (int) (bufferedImage.getWidth(observer) * scale),
        ((int) (myStyle.leading * scale)) +
            (int) (bufferedImage.getHeight(observer) * scale));
  }

  // teken de afbeelding
  public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
    int width = x + (int) (myStyle.indent * scale);
    int height = y + (int) (myStyle.leading * scale);
    g.drawImage(bufferedImage, width, height, (int) (bufferedImage.getWidth(observer) * scale),
        (int) (bufferedImage.getHeight(observer) * scale), observer);
  }

  public String toString() {
    return "BitmapItem[" + getLevel() + "," + imageName + "]";
  }
}
