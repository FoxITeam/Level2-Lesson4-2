package ru.foxit.grayfox;


//        Отправлять сообщения в лог по нажатию кнопки или по нажатию клавиши Enter.
//        Enter так и не сделал, был завал на работе. На выходных должен доделать.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame implements Thread.UncaughtExceptionHandler,
        ActionListener {

    // Размеры
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    // компоненты (лог)
    private final JTextArea log = new JTextArea();

    // Верхняя панелька, где будут лежать
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    // ip адрес
    private final JTextField tfIPAddress = new JTextField("10.10.10.210");
    // порт
    private final JTextField tfPort = new JTextField("8189");
    // галочка
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    // логин
    private final JTextField tfLogin = new JTextField("grayfox");
    // пароль
    private final JPasswordField tfPassword = new JPasswordField("123456");
    // кнопка логин
    private final JButton btnLogin = new JButton("Login");

    // Нижняя панелька, где будут желать
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    // дисконнект
    private final JButton btnDisconnect = new JButton("Disconnect");
    // Поле где можн писать сообщение
    private final JTextField tfMessage = new JTextField();
    // Отправить сообщение
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    // Оно и будет запускать клиент
    public static void main(String[] args) {
        // Мы запустили в отдельном потоке
        // Придумали специальный метод, который отправляет выполняемые действие в отдельный поток.
        // И все что будет в этом окне, оно будет обрабатываться в отдельном спец потоке.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    ClientGUI() {

        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие окна по клику на крестик
        setLocationRelativeTo(null); // ровно по середине экрана окошко
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");

        // На верхнюю панель мы кладем
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);

        // на нижнюю панель мы кладем
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);

        log.setEditable(false); //  У нашего лога запрещаем редактирование.
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log); // Скрол ползунок создаем в наш лог и
        JScrollPane scrollUsers = new JScrollPane(userList); // ползунок для юзеров
        scrollUsers.setPreferredSize(new Dimension(100, 0));

        String[] users = {"user1_000_000_000", "user2", "user3", "user4", "user5",
                "user6", "user7", "user8", "user9", "user10"}; // Создаем массив из пользователей.
        userList.setListData(users); //Нажему юзерс листу добавили юзерс


        add(panelTop, BorderLayout.NORTH); // север
        add(panelBottom, BorderLayout.SOUTH); // юг (низ)
        add(scrollLog, BorderLayout.CENTER); // окошко с логом будет в центре
        add(scrollUsers, BorderLayout.EAST); //Добавляем его на восток, этот ползунок.

        setVisible(true); // отображение
    }

//    РћС‚РїСЂР°РІР»СЏС‚СЊ СЃРѕРѕР±С‰РµРЅРёСЏ РІ Р»РѕРі РїРѕ РЅР°Р¶Р°С‚РёСЋ РєРЅРѕРїРєРё РёР»Рё РїРѕ РЅР°Р¶Р°С‚РёСЋ РєР»Р°РІРёС€Рё Enter.
//    РЎРѕР·РґР°С‚СЊ Р»РѕРі РІ С„Р°Р№Р»Рµ (Р·Р°РїРёСЃРё РґРѕР»Р¶РЅС‹ РґРµР»Р°С‚СЊСЃСЏ РїСЂРё РѕС‚РїСЂР°РІРєРµ СЃРѕРѕР±С‰РµРЅРёР№).


//    public void action(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            log.append(tfMessage.getText() + "\n");
//        }
//    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend) {
            log.append(tfMessage.getText() + "\n"); // Добавления в лог текст, который написал с поля tfMessage.
            tfMessage.setText(""); // Устанавливаем пустое поле tfMessage, если ты написал сообщение.
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }


    // вылавливание ошибки и отображение
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0) {
            msg = "Empty stack trace";
        } else {
            msg = e.getClass().getCanonicalName() + ": " +
                    e.getMessage() + "\n" + "\t at " + ste[0];
        }
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}