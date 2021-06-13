import javax.swing.*;
import java.awt.*;

/**
 * @author lcl100
 * @create 2021-06-11 22:24
 */
public class MainApp extends JFrame {
    public MainApp(){
        // 设置窗体名称
        setTitle("走迷宫游戏");
        // 获取自定义的游戏地图面板的实例对象
        MapPanel panel=new MapPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
        // 执行并构建窗体设定
        pack();
    }

    public static void main(String[] args) {
        MainApp app=new MainApp();
        // 允许窗体关闭操作
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 显示窗体
        app.setVisible(true);
    }
}
