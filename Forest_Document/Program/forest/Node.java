package forest;

import java.awt.Point;
import java.util.ArrayList;

/**
 * ノードの属性を記憶します。
 */
public class Node extends Object
{
    /**
     * 子ノードを記憶するフィールドです。
     */
    private ArrayList<Node> childrenNode;
    /**
     * 葉ノードか否かを記憶するフィールドです。
     */
    private Boolean leafNode = false;
    /**
     * ノードの深さを記憶するフィールドです。
     */
    private Integer nodeDepth;
    /**
     * ノードの名前を記憶するフィールドです。
     */
    private final String nodeName;
    /**
     * 喉の番号を記憶するフィールドです。
     */
    private Integer nodeNumber;
    /**
    * ノードの座標を記憶するフィールドです。
    */
    private Point nodePoint;
    /**
     * 親ノードを記憶するフィールドです。
     */
    private Node parentNode;
    /**
     * 根ノードか否かを記憶するフィールドです。
     */
    private Boolean rootNode;
    /**
     * ノードが探索済みかを記憶するフィールドです。
     */
    private Boolean visit;


    /**
     * 指定されたノード名を元に新しいNodeオブジェクトを構築します。
     * @param name ノード名
     */
    Node(String name)
    {
        this.childrenNode = new ArrayList<Node>();
        this.leafNode = null;
        this.nodeDepth = 0;
        this.nodeName = name;
        this.nodeNumber = 0;
        this.parentNode = null;
        this.rootNode = false;
    }

    /**
     * 指定されたノード名、深さを元に新しいNodeオブジェクトを構築します。
     * @param name ノード名
     * @param depth ノードの深さ
     */
    Node(String name, Integer depth)
    {
        this.childrenNode = new ArrayList<Node>();
        this.leafNode = false;
        this.nodeDepth = depth;
        this.nodeName = name;
        this.nodeNumber = 0;
        this.nodePoint = null;
        this.parentNode = null;
        this.rootNode = false;
        this.visit = false;
    }
/**
    Node(Integer number, String name)
    {
        this.childrenNode = new ArrayList<Node>();
        this.leafNode = null;
        this.nodeDepth = 0;
        this.nodeName = name;
        this.nodeNumber = number;
        this.parentNode = null;
        this.rootNode = false;
    }
**/

    /**
     * 子ノードを応答します。
     * @return 子ノード
     */
    public ArrayList<Node> getChildrenNode()
    {
        return this.childrenNode;
    }

    /**
     * ノード深度を応答します。
     * @return ノード深度
     */
    public Integer getNodeDepth()
    {
        return this.nodeDepth;
    }

    /**
     * ノード名を応答します。
     * @return ノード名
     */
    public String getNodeName()
    {
        return this.nodeName;
    }

    /**
     * ノード番号を応答します。
     * @return ノード番号
     */
    public Integer getNodeNumber()
    {
        return this.nodeNumber;
    }

    /**
     * ノード座標を応答します。
     * @return ノード座標
     */
    public Point getNodePoint()
    {
        return this.nodePoint;
    }

    /**
     * 親ノードを応答します。
     * @return 親ノード
     */
    public Node getParentNode()
    {
        return this.parentNode;
    }

    /**
     * 葉ノードかを応答します。
     * @return 葉ノード
     */
    public Boolean isLeaf()
    {
        return this.leafNode;
    }

    /**
     * 根ノードかを応答します。
     * @return 根ノード
     */
    public Boolean isRoot()
    {
        return this.rootNode;
    }

    /**
     * 探索済みかを応答します。
     * @return 探索済みか否か
     */
    public Boolean isVisit()
    {
        return this.visit;
    }

    /**
     * 子ノードを設定します。
     * @param children 子ノード
     */
    public void setChildrenNode(Node children)
    {
        this.childrenNode.add(children);
    }

    /**
     * 葉ノードかを設定します。
     * @param leaf 葉ノード
     */
    public void setLeafNode(Boolean leaf)
    {
        this.leafNode = leaf;
    }

    /**
     * ノード深度を設定します。
     * @param depth ノード深度
     */
    public void setNodeDepth(Integer depth)
    {
        this.nodeDepth = depth;
    }

    /**
     * ノード番号を設定します。
     * @param num ノード番号
     */
    public void setNodeNumber(Integer num)
    {
        this.nodeNumber = num;
    }

    /**
     * ノード座標を設定します。
     * @param point ノード座標
     */
    public void setNodePoint(Point point)
    {
        this.nodePoint = point;
    }

    /**
     * 親ノードを設定します。
     * @param parent 親ノード
     */
    public void setParentNode(Node parent)
    {
        if(parent != null)
        {
            this.parentNode = parent;
        }else
        {
            setRootNode(true);
        }
    }

    /**
     * 根ノードかを設定します。
     * @param root 根ノード
     */
    public void setRootNode(Boolean root)
    {
        this.rootNode = root;
    }

    /**
     * 探索済みかを設定します。
     * @param visit 探索済みか否か
     */
    public void setVisit(Boolean visit) {
        this.visit = visit;
    }

    @Override
    public String toString()
    {

        if(this.parentNode == null)
        {
            return  "name = "+ this.nodeName + ", depth = "+ this.nodeDepth +
                    ", parent = null\n";
        }
        else
        {
            return  "name = "+ this.nodeName + ", depth = "+ this.nodeDepth +
                    ", parent = "+ this.parentNode.getNodeName() +"\n";
        }

    }

}
