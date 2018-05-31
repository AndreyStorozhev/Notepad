import javax.swing.*;

public class Scroll extends JScrollPane {
    private final String path;
    private final JTextArea text;
    private final boolean isOpen;

    public Scroll(JTextArea text, boolean isOpen, String path){
        super(text);
        this.text = text;
        this.isOpen = isOpen;
        this.path = path;
    }
    public String getText(){
        return text.getText();
    }
    public boolean isOpen(){
        return isOpen;
    }

    public String getPath() {
        return path;
    }
}
