import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyRectApplet extends Applet implements ActionListener, MouseListener, MouseMotionListener {

    private List<Rectangle> rectangles; // Массив для хранения прямоугольников
    private Button rectButton, coloredRectButton, drawableRectButton; // Кнопки
    private Rectangle selectedRect = null; // Выбранный для перемещения прямоугольник
    private int mouseOffsetX, mouseOffsetY; // Смещение мыши от угла прямоугольника

    public void init() {
        rectangles = new ArrayList<>();

        // Создание кнопок
        rectButton = new Button("Rectangle");
        coloredRectButton = new Button("ColoredRect");
        drawableRectButton = new Button("DrawableRect");

        // Добавление кнопок на апплет
        add(rectButton);
        add(coloredRectButton);
        add(drawableRectButton);

        // Установка слушателей событий для кнопок
        rectButton.addActionListener(this);
        coloredRectButton.addActionListener(this);
        drawableRectButton.addActionListener(this);

        // Установка слушателей событий для мыши
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    // Обработчик нажатий кнопок
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rectButton) {
            rectangles.add(new Rectangle(50, 50, 100, 100));
        } else if (e.getSource() == coloredRectButton) {
            rectangles.add(new ColoredRect(50, 50, 100, 100, Color.BLACK, Color.GREEN));
        } else if (e.getSource() == drawableRectButton) {
            rectangles.add(new DrawableRect(50, 50, 100, 100, Color.BLUE));
        }
        repaint(); // Перерисовать апплет, чтобы отобразить изменения
    }

    // Метод отрисовки апплета
    public void paint(Graphics g) {
        for (Rectangle rect : rectangles) {
            if (rect instanceof ColoredRect) {
                ((ColoredRect) rect).draw(g);
            } else if (rect instanceof DrawableRect) {
                ((DrawableRect) rect).draw(g);
            } else {
                g.drawRect(rect.x1, rect.y1, rect.x2 - rect.x1, rect.y2 - rect.y1);
            }
        }
    }

    // Обработчики событий мыши
    @Override
    public void mouseClicked(MouseEvent e) {} // не используется
    @Override
    public void mouseEntered(MouseEvent e) {} // не используется
    @Override
    public void mouseExited(MouseEvent e) {} // не используется


    // Обработка нажатия кнопки мыши
    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Проверяем, какой прямоугольник был нажат
        for (Rectangle rect : rectangles) {
            if (mouseX >= rect.x1 && mouseX <= rect.x2 && mouseY >= rect.y1 && mouseY <= rect.y2) {
                selectedRect = rect;
                mouseOffsetX = mouseX - rect.x1;
                mouseOffsetY = mouseY - rect.y1;
                break;
            }
        }
    }


    // Обработка отпускания кнопки мыши
    @Override
    public void mouseReleased(MouseEvent e) {
        selectedRect = null;
    }

    // Обработка перемещения мыши
    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedRect != null)
        {
            selectedRect.move(e.getX() - selectedRect.x1 - mouseOffsetX, e.getY() - selectedRect.y1-mouseOffsetY);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {} // не используется
}