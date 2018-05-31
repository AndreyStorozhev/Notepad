import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NodePad {
    private final String NAME = "Новый файл";
    private JFileChooser f = new JFileChooser(); //окошко выбора куда нужно сохранить файл
    private JTabbedPane tabs = new JTabbedPane(); //при нажатии на кнопку создать(открыть, сохоанить) файл создается новая вкладка

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //закинем процесс в диспетчер процессов
            @Override
            public void run() {
                new NodePad();
            }
        });
    }

    public NodePad() {
        JMenuBar menuBar = new JMenuBar(); //возможностть создавать меню
        JMenu file = new JMenu("Файл");
        JMenuItem newFile = new JMenuItem("Создать файл");
        JMenuItem openFile = new JMenuItem("Открыть файл");
        JMenuItem saveFile = new JMenuItem("Сохранить файл");

        //добавляенм кнопки в меню
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        //добавляем всё в меню бар
        menuBar.add(file);

        JFrame window = new JFrame("Блокнот");
        window.setSize(800, 600); //размер окна
        window.setJMenuBar(menuBar); //добавляем меню в окно блокнота
        window.add(tabs); //добавляем вкладку в окно блокнота
        window.setResizable(false);//нельзя изменять размер блокнота
        window.setLocationRelativeTo(null);//блокнот появляется в ценрте экрана
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//при нажатии на крестик программа закрывается
        window.setVisible(true);//запускаем приложение

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea text = new JTextArea();//добавляем вкладку для написания текста
                Scroll scroll = new Scroll(text, false, ""); //добавляем скролл
                tabs.addTab(NAME, scroll); //добавляем в мнеджер вкладок имя и возможность скролла
            }
        });
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scroll text = (Scroll) tabs.getSelectedComponent(); //полуаем копонент который сейчас выведен на экране
                String output = text.getText();



                if (tabs.countComponents() != 0) {

                    if (text.isOpen()){ //если вкалдка с новым фалом открыта то
                        try {
                            FileOutputStream writer = new FileOutputStream(text.getPath());
                            writer.write(output.getBytes());
                        } catch (IOException eq) { eq.printStackTrace(); }
                    }else {
                        f.showSaveDialog(null); //вызов окна сохранения
                        File file1 = f.getSelectedFile();//получаем файл который будем сохранять
                        try {
                            FileOutputStream writer = new FileOutputStream(file1);
                            writer.write(output.getBytes());
                        } catch (IOException eq) {
                            eq.printStackTrace();
                        }
                    }
                }
            }
        });
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    f.showOpenDialog(null); //выводит окошку типа выбора файла
                    File file2 = f.getSelectedFile();
                    String input = new String(Files.readAllBytes(Paths.get(file2.getAbsolutePath())));
                    JTextArea textArea = new JTextArea(input); //запишет в блокнот всё что есть в файле
                    Scroll scroll = new Scroll(textArea, true, file2.getAbsolutePath());
                    tabs.addTab(file2.getName(), scroll);
                }catch (IOException e1){e1.printStackTrace();}
            }
        });
    }
}
