package vm.texture;

import exception.ElException;
import exception.ElException.NotFound;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vm.values.VMValue;

public class Texture extends VMValue {
  public final BufferedImage image;

  public Texture() {
    this.image = null;
  }

  public Texture(BufferedImage image) {
    this.image = image;
  }

  public Texture(String fileName) throws NotFound {
    try {
      this.image = ImageIO.read(new File(workingPath + "/" + fileName));
    } catch(IOException ex) {
      throw new NotFound(this, "Texture " + fileName);
    }
  }
  
  @Override
  public VMValue create() {
    return new Texture(image);
  }
  
  @Override
  public BufferedImage getImage() throws ElException {
    return image;
  }
}
