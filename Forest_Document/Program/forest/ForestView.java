package forest;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 * ビュー。描画物の描画まわりを専門に行います。
 */
public class ForestView extends JPanel implements Runnable
{
    /**
     * モデルを束縛するフィールドです。
     */
    private final ForestModel model;
    /**
     * コントローラを束縛するフィールドです。
     */
    private final ForestController controller;
    /**
     * スクロール量を座標として束縛するフィールドです。
     */
    private Point offset ;
    /**
     * フォントサイズを束縛するフィールドです。
     */
    private final Integer fontSize = 12;
    /**
     * フォントを束縛するフィールドです。
     */
    private final Font font = new Font(Font.SERIF, Font.PLAIN, this.fontSize);

    /**
     * フォントサイズに合わせた矩形の幅を束縛するフィールドです。
     */
    public final int STR_AREA_WIDTH = this.fontSize / 2;
    /**
     * フォントサイズに合わせた矩形の高さを束縛するフィールドです。
     */
    public final int STR_AREA_HEIGHT = this.fontSize + 2;

    /**
     * 指定されたForestModelを元に新しいForestViewオブジェクトを構築します。
     * @param aForestModel モデル
     */
    public ForestView(ForestModel aForestModel)
    {
        this.model = aForestModel;
        this.model.setView(this);
        this.controller = new ForestController();
        this.controller.setModel(this.model);
        this.controller.setView(this);
        this.model.setController(this.controller);
        this.offset = new Point(0, 0);

    }
    /**
    * 指定されたグラフィクスに背景色（明灰色）でビュー全体を塗り、その後にモデルの内容物を描画します。
    * @param aGraphics グラフィックス・コンテキスト
    */
    @Override
    public void paintComponent(Graphics aGraphics) {
        int width = this.getWidth();
        int height = this.getHeight();

        aGraphics.setColor(Color.white);
        aGraphics.fillRect(0, 0, width, height);
        List<Node> nodeTree = this.model.getNodeTree();

        aGraphics.setColor(Color.black);

        int lineNum = 0;
        for (Node aNode : nodeTree)
        {
            if(aNode.isVisit())
            {
                drawNode(aGraphics, aNode, lineNum);
            }
            else
            {
                drawInitNode(aGraphics, aNode);
            }
            if (aNode.isLeaf()) lineNum++;
        }
        return;
    }

    /**
     * 指定されたノードを直列に描画します。
     * @param aGraphics グラフィクス・コンテキスト
     * @param aNode ノード
     */
    public void drawInitNode(Graphics aGraphics, Node aNode)
    {
        int strLength = aNode.getNodeName().length();
        int initOffset = (aNode.getNodeNumber() - 1) * (this.fontSize + 8);

        aGraphics.setColor(Color.white);
        aGraphics.fillRect(this.offset.x, this.offset.y + initOffset,
                strLength * (STR_AREA_WIDTH), STR_AREA_HEIGHT);
        aGraphics.setColor(Color.black);
        aGraphics.drawRect(this.offset.x, this.offset.y + initOffset,
                strLength * (STR_AREA_WIDTH), STR_AREA_HEIGHT);
        aGraphics.setFont(this.font);
        aGraphics.drawString(aNode.getNodeName(), this.offset.x, this.offset.y + initOffset + this.fontSize);
    }

    /**
     * 指定されたノードと座標からノードを木構造になるよう描画します。
     * @param aGraphics グラフィクス・コンテキスト
     * @param aNode ノード
     * @param lineNum ノード座標
     */
    public void drawNode(Graphics aGraphics, Node aNode, Integer lineNum)
    {
        int strLength = aNode.getNodeName().length();

        int x = this.offset.x + aNode.getNodeDepth() * 150;
        int y = this.offset.y + lineNum * (STR_AREA_HEIGHT);

        Point aPoint = new Point(x + (strLength * (STR_AREA_WIDTH))/ 2, y + (STR_AREA_HEIGHT)/ 2);
        aNode.setNodePoint(aPoint);
        if(!aNode.isRoot())
        {
            Point parentPoint = aNode.getParentNode().getNodePoint();
            if (parentPoint != null)aGraphics.drawLine(parentPoint.x, parentPoint.y, aPoint.x, aPoint.y);
        }

        aGraphics.setColor(Color.white);
        aGraphics.fillRect(x, y , strLength * (STR_AREA_WIDTH), STR_AREA_HEIGHT);
        aGraphics.setColor(Color.black);
        aGraphics.drawRect(x, y , strLength * (STR_AREA_WIDTH), STR_AREA_HEIGHT);
        aGraphics.setFont(this.font);
        aGraphics.drawString(aNode.getNodeName(), x, y + this.fontSize);
    }

    /**
     * スクロール量を指定された座標分だけ相対スクロールします。
     * @param aPoint X軸とY軸のスクロール量を表す座標
     */
    public void scrollBy(Point aPoint)
    {
        int x = this.offset.x + aPoint.x;
        int y = this.offset.y + aPoint.y;
        this.scrollTo(new Point(x, y));
        return;
    }

    /**
     * スクロール量を指定された座標に設定（絶対スクロール）します。
     * @param aPoint X軸とY軸の絶対スクロール量を表す座標
     */
    public void scrollTo(Point aPoint)
    {
        this.offset = aPoint;
        return;
    }

    /**
     * アニメーションを行います。
     */
    @Override
    public void run()
    {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Node> nodeTree = this.model.getNodeTree();
        for (Node aNode : nodeTree)
        {
            aNode.setVisit(true);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
}
