package ru.foxit.grayfox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Реализуем интерфейс ActionListener посредством переопределения метода Action
public class ServerGUI extends JFrame implements ActionListener,
// Наследуемся от Thread.UncaughtExceptionHandler
        Thread.UncaughtExceptionHandler {

    private static final int POS_X = 1000;
    private static final int POS_Y = 600;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private static final ChatServer chatServer = new ChatServer(); // При старте окошка будет новый чат сервер.
    private final JButton btnStart = new JButton("Start"); // Кнопка старт
    private final JButton btnStop = new JButton("Stop"); // Кнопка стоп

    // Оно и будет запускать клиент
    public static void main(String[] args) {
        // Мы запустили в отдельном потоке
        // Придумали специальный метод, который отправляет выполняемые действие в отдельный поток.
        // И все что будет в этом окне, оно будет обрабатываться в отдельном спец потоке.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
    }

    ServerGUI() {
        // и прописать самой первой строчкой сложную штуку
        Thread.setDefaultUncaughtExceptionHandler(this); // Установить дефолдный обработчик не пойманных исключений.
        // Наше окно будет отлавливать все исключения, которые на нем возникли.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Закрытие по крестику
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT); // Сразу размеры и положение
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true); // Что то должно быть поверх всех окон
        setLayout(new GridLayout(1, 2));
        btnStart.addActionListener(this); //Обработчику кнопки передаем себя
        btnStop.addActionListener(this); //Обработчику кнопки передаем себя
        add(btnStart); // добавить кнопку старт.
        add(btnStop); // добавить кнопку стоп.
        setVisible(true); // отображение окна.
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Обработчик кнопки (а не мыши).
        // Создаем метод гет соурс, что событие прилетело от такого то компонента.
        Object src = e.getSource();
        // Условие, если getSource равен кнопке btnStart
        if (src == btnStart) {
            //То запускаем чат сервер на порту 8189
            chatServer.start(8189);
            // в противном случае == кнопке стоп
        } else if (src == btnStop) {
            // То мы останавливаем чат
            // int a = 5 / 0; // Вызвал ошибку в кнопке стоп, чтобы проверить, как метод uncaughtException
            // Вылавливает ошибки.
            chatServer.stop();
        } else {
            // В противном случае RuntimeException
            // Мы справниваем соурсы, а тут вариант, который мы не учли!
            // И мы показываем src какой нам прилетел.
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    // Переопределяем
    @Override
    // Мы пишем собственный обработчик исключений
    // и как предложение, сделать обработчик исключений нас.
    // Мы реализуем uncaughtException - принимает исключения
    // Весь код не в коем случае не оборачиваем в трай кетч, это безумие и безобразие
    public void uncaughtException(Thread t, Throwable e) {
        // Мы пишем эту ошибку, вывести в окошечко, вывести например модный алерт.
        e.printStackTrace();
        // Нам понадобиться стринг msg
        String msg;
        // Массив из StackTraceElement элементов, который будет называться ste.
        // А ошибку мы будем доставить из e.getStackTrace и ложить в массив.
        StackTraceElement[] ste = e.getStackTrace();
        // Если длина ste.length массива равна 0, то
        if (ste.length == 0) {
            // то в сообщение мы выводим Empty stack trace.
            msg = "Empty stack trace";
        } else {
            // в противном случае, мы пишем что msg =
            msg = e.getClass().getCanonicalName() + ": " +
                    e.getMessage() + "\n" + "\t at " + ste[0];
        }
        // На фоне этого окна нужно соченить объект Msg, стринг это будет Exception и Тип сообщения JOptionPane.ERROR_MESSAGE
        //
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        // После этого мы должны грохнуть нашу программу со статусом единичка.
        System.exit(1);
    }
}