package forest;


import javax.swing.event.MouseInputAdapter;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

/**
 * コントローラ。描画物の制御まわりを専門に行います。
 */
public class ForestController extends MouseInputAdapter implements MouseWheelListener
{
    /**
     * モデルを束縛するフィールドです。
     */
    private ForestModel model;
    /**
     * ビューを束縛するフィールドです。
     */
    private ForestView view;
    /**
     * 現在にマウスのボタンが押された場所を束縛するフィールドです。
     */
    private Point current;
    /**
     * 以前にマウスのボタンが押された場所を束縛するフィールドです。
     */
    private Point previous;

    /**
     * 新しいForestControllerオブジェクトを構築します。
     */
    public ForestController()
    {
        super();
        this.model = null;
        this.view = null;
        this.current = null;
        this.previous = null;
    }

    /**
     * 指定された座標にノードがあればその座標を出力します。
     * @param aPoint　マウス座標
     */
    public void getNodeAttribute(Point aPoint)
    {
        List<Node> nodeTree = this.model.getNodeTree();

        for (Node aNode : nodeTree)
        {
            int strLength = aNode.getNodeName().length();
            int x = aNode.getNodePoint().x - strLength * (this.view.STR_AREA_WIDTH / 2);
            int y = aNode.getNodePoint().y - (this.view.STR_AREA_HEIGHT / 2);
            int width = aNode.getNodeName().length() * this.view.STR_AREA_WIDTH;
            int height = this.view.STR_AREA_HEIGHT;

            if (x <= aPoint.x && aPoint.x <= x + width &&
                y <= aPoint.y && aPoint.y <= y + height)
            {
                System.out.println(aNode);
            }
        }
    }

    /**
     * 指定されたマウスイベントからマウスカーサの位置を獲得して、
     * モデル座標系でのクリック位置を割り出して標準出力に出力します。
     * @param aMouseEvent マウスイベント
     */
    public void mouseClicked(MouseEvent aMouseEvent)
    {
        Point aPoint = aMouseEvent.getPoint();
        //aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
        System.out.println(aPoint);
        getNodeAttribute(aPoint);

        return;
    }

    /**
     * マウスカーサの形状を移動の形に変化させ、指定されたマウスイベントからマウスカーサの位置を獲得して、
     * インスタンス変数currentに設定すると共に、以前のマウスカーサの位置からの差分を計算します。
     * そして、その差分だけビューに対してスクロールを依頼し、その後にビューの再描画を依頼します。
     * 最後にインスタンス変数previousをインスタンス変数currentに更新します。
     * @param aMouseEvent マウスイベント
     */
    public void mouseDragged(MouseEvent aMouseEvent)
    {
        Cursor aCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        Component aComponent = (Component) aMouseEvent.getSource();
        aComponent.setCursor(aCursor);

        this.current = aMouseEvent.getPoint();
        Integer x = this.current.x - this.previous.x;
        Integer y = this.current.y - this.previous.y;
        Point aPoint = new Point(x, y);
        this.scrollBy(aPoint);
        this.previous = this.current;

        return;
    }

    /**
     * マウスカーサの形状を十字に変化させ、指定されたマウスイベントからマウスカーサの位置を獲得して、
     * インスタンス変数currentに設定する共にインスタンス変数previousをインスタンス変数currentに更新します。
     * @param aMouseEvent マウスイベント
     */
    public void mousePressed(MouseEvent aMouseEvent)
    {
        Cursor aCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
        Component aComponent = (Component)aMouseEvent.getSource();
        aComponent.setCursor(aCursor);
        this.current = aMouseEvent.getPoint();
        this.previous = this.current;
        return;
    }

    /**
     * マウスカーサの形状をデフォルトに戻し、指定されたマウスイベントからマウスカーサの位置を獲得して、
     * インスタンス変数currentに設定する共にインスタンス変数previousをインスタンス変数currentに更新します。
     * @param aMouseEvent マウスイベント
     */
    public void mouseReleased(MouseEvent aMouseEvent)
    {
        Cursor aCursor = Cursor.getDefaultCursor();
        Component aComponent = (Component)aMouseEvent.getSource();
        aComponent.setCursor(aCursor);
        this.current = aMouseEvent.getPoint();
        this.previous = this.current;
        return;
    }

    /**
     *　マウスホィールで、縦（垂直）方向のスクロールを行う。
     *　その際に、何らかの修飾があれば、横（水平）方向のスクロールも行う。
     * @param aMouseWheelEvent マウスホイールイベント
     */
    public void mouseWheelMoved(MouseWheelEvent aMouseWheelEvent)
    {
        Integer someModifiers = aMouseWheelEvent.getModifiersEx();
        Boolean isHorizontal = (someModifiers) != 0;
        Point aPoint;
        if (isHorizontal){
            Integer x = aMouseWheelEvent.getUnitsToScroll();
            aPoint = new Point(-2*x, 0);
        }else {
            Integer y = aMouseWheelEvent.getUnitsToScroll();
            aPoint = new Point(0, -2*y);
        }

        scrollBy(aPoint);

        return;
    }

    /**
     * スクロール量を指定された座標分だけ相対スクロールし、ビューを再描画します。
     * @param aPoint スクロール量を表す座標。
     */
    public void scrollBy(Point aPoint)
    {
        this.view.scrollBy(aPoint);
        this.view.repaint();

        return;
    }

    /**
     * 指定されたモデルをインスタンス変数modelに設定します。
     * @param aModel このコントローラのモデル
     */
    public void setModel(ForestModel aModel)
    {
        this.model = aModel;
        return;
    }

    /**
     * 指定されたビューをインスタンス変数viewに設定し、
     * ビューのマウスのリスナおよびモーションリスナそしてホイールリスナをこのコントローラにします。
     * @param aView このコントローラのビュー
     */
    public void setView(ForestView aView)
    {
        this.view = aView;
        this.view.addMouseListener(this);
        this.view.addMouseMotionListener(this);
        this.view.addMouseWheelListener(this);
        return;
    }
}
