import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.*;
import java.awt.Image;
import java.util.function.Consumer;

class Menu extends JPanel implements MouseListener {
  private int targetFPS = 60;
  private double itteration = 0;
  private double scaleXY = 0;
  private double delta = 0.05;
  private int bobing = 0;
  private static final int width = 800, height = 450;
  private BufferedImage[] imageOG = new BufferedImage[5];
  private BufferedImage[] rotImage = new BufferedImage[5];
  private File alive = new File("images/Image.jpg");
  private int imageNum;
  private Consumer<Integer> listener;
	private String label;
	private boolean hover = false;

  /**
   * @param paths    takes in paths of pictures and addes them to imageOG, 5 Max
   * @param listener takes in a method that takes in an int and returns void
   **/
  public Menu(String[] paths, Consumer<Integer> listener) {
    addMouseListener(this);
    if (paths != null) {
      imageOG = new BufferedImage[paths.length];
      rotImage = new BufferedImage[paths.length];
      for (int i = 0; i < paths.length; i++) {
        try {
          imageOG[i] = (ImageIO.read(new File(paths[i])));
          rotImage[i] = imageOG[i];
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else {
      for (int i = 0; i < imageOG.length; i++) {
        try {
          imageOG[i] = (ImageIO.read(alive));
          rotImage[i] = imageOG[i];
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    this.listener = listener;

    Timer timer = new Timer(1000 / targetFPS, e -> {
			
      Point mousePoint = MouseInfo.getPointerInfo().getLocation();
      SwingUtilities.convertPointFromScreen(mousePoint, this);

      imageNum = findComponent(mousePoint);
			if(imageNum!=-1){
				if(!hover){
					itteration = 3;
					hover = true;
				}
			}else if(hover){
				hover = false;
			}

      itteration += delta;
      bobing = (int) (3.5 * (Math.sin(itteration) + Math.sin(itteration / 9) - Math.sin(itteration / 5)));
      scaleXY = 1 - (((Math.cos(itteration) - Math.cos(itteration / 4) + Math.cos((itteration / 3) + 2)) + 2.712) / 14);
      for (int i = 0; i < imageOG.length; i++) {
        rotImage[i] = rotateImageByDegrees((BufferedImage) imageOG[i], 4
            * (Math.sin(itteration) - Math.sin(itteration / 2.0) + Math.sin(itteration / 3.0) + Math.cos(itteration-1.3)),
            scaleXY);
      }
      repaint();
    });
    timer.setRepeats(true);
    timer.start();
  }

	public Menu(String[] paths, Consumer<Integer> listener, String label){
		this(paths, listener);
		this.label = label;
	}

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

		if(label!=null){
			Font oldFont = g.getFont();
			Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
			g.setFont(font);
			FontMetrics fontMetric = g2d.getFontMetrics(font);
			int x = (width - fontMetric.stringWidth(label))/2;
			int y = ((height - fontMetric.getHeight())/2)+fontMetric.getAscent()-200;
			g2d.drawString(label, x, y);
			g.setFont(oldFont);
		}
		
    for (int i = 0; i < imageOG.length; i++) {
      int curX = (width / (imageOG.length + 1)) * (i + 1);
      if (imageNum == i) {
        g2d.drawImage(rotImage[i], (int) (curX - (rotImage[i].getWidth() * scaleXY) / 2),
            (int) ((height / 2) - (rotImage[i].getHeight() * scaleXY) / 2 - bobing), null);
      } else {
        g2d.drawImage(imageOG[i], (int) (curX - imageOG[i].getWidth() / 2),
            (int) ((height / 2) - (imageOG[i].getHeight()) / 2), null);
      }

    }
  }

  public BufferedImage rotateImageByDegrees(BufferedImage img, double angle, double scale) {

    double rads = Math.toRadians(angle);
    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
    int w = img.getWidth();
    int h = img.getHeight();
    int newWidth = (int) Math.floor(w * cos + h * sin);
    int newHeight = (int) Math.floor(h * cos + w * sin);

    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();
    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

    int x = w / 2;
    int y = h / 2;

    at.rotate(rads, x, y);
    at.scale(scale, scale);
    g2d.setTransform(at);
    g2d.drawImage(img, 0, 0, this);
    g2d.dispose();

    return rotated;
  }

	public void setText(String string){
		label = string;
	}

  private int findComponent(Point point) {
    int pointX = (int) point.getX(), pointY = (int) point.getY();
    // System.out.println(pointX + ", " + pointY);
    for (int i = 0; i < imageOG.length; i++) {
      int curX = (width / (imageOG.length + 1)) * (i + 1);
      int startX = (int) (curX - (imageOG[i].getWidth()) / 2),
          startY = (int) ((height / 2) - (imageOG[i].getHeight()) / 2);
      if ((pointX > startX && pointX < startX + imageOG[i].getWidth())
          && (pointY > startY && pointY < startY + imageOG[i].getHeight())) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // System.out.println("Hello world");
    int pic = findComponent(e.getPoint());
    // System.out.println(pic);
    if (pic != -1) {
      listener.accept(pic);
    }
  }
}