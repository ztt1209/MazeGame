import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author lcl100
 * @create 2021-06-11 22:27
 */
public class MapPanel extends JPanel implements KeyListener {
    // 窗体的宽和高
    private static final int WIDTH = 450;
    private static final int HEIGHT = 450;
    // 设定背景方格默认行数和列数
    private static final int ROW = 15;
    private static final int COLUMN = 15;
    // 设置窗体单个图像，采用30x30大小的图形，一行设置15个，即450像素，即窗体默认大小
    private static final int SIZE = 30;

    // 设定迷宫地图
    private static final byte FLOOR = 0;// 0表示通道地板
    private static final byte WALL = 1;// 1表示墙
    private static final byte END = 2;// 2表示终点
    private byte[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1}
    };

    // 设定显示的图像对象
    private Image floorImage;
    private Image wallImage;
    private Image heroImage;
    private Image endImage;

    // 角色坐标
    private int x, y;

    // 区分上下左右按键的移动
    private static final byte LEFT = 0;
    private static final byte RIGHT = 1;
    private static final byte UP = 2;
    private static final byte DOWN = 3;

    public MapPanel() {
        // 设定面板大小
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // 加载图片
        loadImage();
        // 初始化角色坐标
        this.x = 1;
        this.y = 1;
        // 设定焦点在本窗体并且监听键盘事件
        setFocusable(true);
        addKeyListener(this);
    }

    /**
     * 画地图和角色
     *
     * @param g 画笔
     */
    public void paintComponent(Graphics g) {
        drawMap(g);
        drawRole(g);
    }

    /**
     * 画角色（英雄）
     *
     * @param g 画笔
     */
    private void drawRole(Graphics g) {
        g.drawImage(heroImage, x * SIZE, y * SIZE, SIZE, SIZE, this);
    }

    private void loadImage() {
        // 获取当前类对应相对位置image文件夹下的地板图像
        ImageIcon icon = new ImageIcon(getClass().getResource("images/floor.png"));
        // 将地板图像实例赋给floorImage变量
        floorImage = icon.getImage();
        // 获取墙体图像
        icon = new ImageIcon(getClass().getResource("images/wall.gif"));
        wallImage = icon.getImage();
        // 获取英雄图像
        icon = new ImageIcon(getClass().getResource("images/hero.png"));
        heroImage = icon.getImage();
        // 获取终点图像
        icon = new ImageIcon(getClass().getResource("images/end.png"));
        endImage = icon.getImage();
    }

    /**
     * 根据map[i][j]中记录的地图信息绘制图案画出地图
     * 标记0为地板，标记1为墙
     *
     * @param g
     */
    private void drawMap(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                switch (map[i][j]) {
                    case 0:
                        // 标记为0时画出地板，在指定位置加载图像
                        g.drawImage(floorImage, j * SIZE, i * SIZE, this);
                        break;
                    case 1:
                        // 标记为1时画出城墙
                        g.drawImage(wallImage, j * SIZE, i * SIZE, this);
                        break;
                    case 2:
                        // 标记为2时画出终点
                        g.drawImage(endImage, j * SIZE, i * SIZE, SIZE, SIZE, this);
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 根据按键进行移动
        int keyCode = e.getKeyCode();// 获取按键编码
        switch (keyCode) {
            // 左方向键或'A'键，都可以左移
            case KeyEvent.VK_LEFT:
                move(LEFT);
                break;
            case KeyEvent.VK_A:
                move(LEFT);
                break;
            // 右方向键或'D'键，都可以右移
            case KeyEvent.VK_RIGHT:
                move(RIGHT);
                break;
            case KeyEvent.VK_D:
                move(RIGHT);
                break;
            // 上方向键或'W'键，都可以上移
            case KeyEvent.VK_UP:
                move(UP);
                break;
            case KeyEvent.VK_W:
                move(UP);
                break;
            // 下方向键或'S'键，都可以下移
            case KeyEvent.VK_DOWN:
                move(DOWN);
                break;
            case KeyEvent.VK_S:
                move(DOWN);
                break;
            default:
                break;
        }
        // 重新绘制窗体图像
        repaint();
        if (isFinish(x, y)) {
            // 移动到出口
            JOptionPane.showMessageDialog(this, "恭喜通关！");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 判断是否允许移动，如果传入的坐标不是墙则可以移动
     *
     * @param x
     * @param y
     * @return 允许移动则返回true，否则返回false
     */
    private boolean isAllowMove(int x, int y) {
        // 以判断(x,y)是WALL还是FLOOR来作为是否能移动的根据
        // 1表示墙，不能移动；0表示地板，可以移动
        if (x < COLUMN && y < ROW) {// 进行参数校验，不能超过数组的长度
            return map[y][x] != 1;
        }
        return false;
    }

    /**
     * 移动角色人物
     *
     * @param event 传入移动方向，分别可以是LEFT、RIGHT、UP、DOWN
     */
    private void move(int event) {
        switch (event) {
            case LEFT:// 左移
                if (isAllowMove(x - 1, y)) {// 判断左移一步后的位置是否允许移动（不是墙就可以移动）
                    x--;
                }
                break;
            case RIGHT:// 右移
                if (isAllowMove(x + 1, y)) {
                    x++;
                }
                break;
            case UP:// 上移
                if (isAllowMove(x, y - 1)) {
                    y--;
                }
                break;
            case DOWN:// 下移
                if (isAllowMove(x, y + 1)) {
                    y++;
                }
            default:
                break;
        }
    }

    /**
     * 传入人物的坐标来判断是否到达终点
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isFinish(int x, int y) {
        // 2表示终点图像
        // 注意：x坐标表示第几列，y坐标表示第几行，所以是map[y][x]而不是map[x][y]
        return map[y][x] == END;
    }
}
