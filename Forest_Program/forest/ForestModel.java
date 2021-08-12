package forest;

import utility.StringUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * モデル。描画物のデータまわりを専門に行います。
 */
public class ForestModel extends Object
{
    /**
     * 入力されたテキストファイルからtree:以下の文字列を束縛するフィールドです。
     */
    private final List<String> treesText;
    /**
     * 入力されたテキストファイルからnode:以下の文字列を束縛するフィールドです。
     */
    private final List<String> nodesText;
    /**
     * 入力されたテキストファイルからbranch:以下の文字列を束縛するフィールドです。
     */
    private final List<String> branchesText;
    /**
     * 全てのノードを配列として束縛するフィールドです。
     */
    private final List<Node> nodeTree;
    /**
     * ビューを束縛するフィールドです。
     */
    private ForestView view;
    /**
     * コントローラを束縛するフィールドです。
     */
    private ForestController controller;
    /**
     * 引数に渡されたファイルを束縛するフィールドです。
     */
    private final File readFile;

    /**
     * 指定されたファイルを元にForestModelオブジェクトを構築します。
     * @param aFile ファイル
     */
    public ForestModel(File aFile)
    {
        this.treesText = new ArrayList<>();
        this.nodesText = new ArrayList<>();
        this.branchesText = new ArrayList<>();
        this.nodeTree = new ArrayList<>();
        this.readFile = aFile;
        this.view = null;
        this.controller = null;
    }

    /**
     * 新しいForestModelオブジェクトを構築します。
     */
    public ForestModel()
    {
        this.treesText = new ArrayList<>();
        this.nodesText = new ArrayList<>();
        this.branchesText = new ArrayList<>();
        this.nodeTree = new ArrayList<>();
        this.readFile = null;
        this.view = null;
        this.controller = null;
    }

    /**
     * 指定されたビューをインスタンス変数aViewに設定します。
     * @param aView このモデルのビュー
     */
    public void setView(ForestView aView)
    {
        this.view = aView;
    }

    /**
     * 束縛されたビューを応答します。
     * @return ビュー
     */
    public ForestView getView()
    {
        return this.view;
    }

    /**
     * 指定されたコントローラをインスタンス変数aControllerに設定します。
     * @param aController このモデルのコントローラ
     */
    public void setController(ForestController aController)
    {
        this.controller = aController;
    }

    /**
     * 束縛されたコントローラを応答します。
     * @return コントローラ
     */
    public ForestController getController()
    {
        return this.controller;
    }

    /**
     * アニメーションを行います。
     */
    public void animate()
    {
        Thread aThread = new Thread(this.view);
        aThread.start();
    }

    /**
     * 指定されたフォーマットで記述されたファイルをそれぞれのインスタンス変数treeText, nodesText, branchesTextに分割します。
     * @throws IOException IOException
     */
    public void readText()throws IOException
    {
        List<String> textList = StringUtility.readTextFromFile(this.readFile);

        int types = -1;
        for(String tmp : textList)
        {
            if(tmp.equals("trees:")){types = 0; continue;}
            if(tmp.equals("nodes:")){types = 1; continue;}
            if(tmp.equals("blanches:")){types = 2; continue;}

            switch(types){
                case -1:
                    System.err.println("不正な値です。");
                    break;
                case 0:
                    this.treesText.add(tmp);
                    break;
                case 1:
                    this.nodesText.add(tmp);
                    break;
                case 2:
                    this.branchesText.add(tmp);
                    break;
            }
        }
    }

    /**
     * treeTextの文字列から各々のノードのインスタンスを作成します。
     */
    public void parseTree()
    {
        Map<Integer, Node> parentNodes = new HashMap<>();
        for (String tmp : this.treesText)
        {
            this.nodeTree.add(getAttribute(tmp, parentNodes));
        }
        getChildren();
        getLeaf();
    }

    /**
     * 指定された文字列からノードの属性を読み取り、インスタンスを作成します。
     * @param aString ノード名
     * @param parentNodes ノード名と番号の対応表　
     * @return ノードのインスタンス
     */
    public Node getAttribute(String aString, Map<Integer, Node> parentNodes)
    {
        String[] splitString = aString.split("\\|-- ");
        Integer depth = splitString.length - 1;
        String nodeName = splitString[depth];

        Node aNode = new Node(nodeName, depth);
        aNode.setParentNode(parentNodes.get(depth - 1));
        aNode.setNodeNumber(this.nodeTree.size() + 1);

        parentNodes.put(depth, aNode);

        return aNode;
    }

    /**
     * インスタンス変数nodeTreeに束縛されているノード属性を読み取り、子ノードの属性を追加します。
     */
    public void getChildren()
    {
        for (Node aNode : this.nodeTree)
        {
            for (Node childNode : this.nodeTree)
            {
                if (childNode.getParentNode() == aNode)
                {
                    aNode.setChildrenNode(childNode);
                }
            }
        }
    }

    /**
     * インスタンス変数nodeTreeに束縛されているノード属性を読み取り、葉ノードか否かを追加します。
     */
    public void getLeaf()
    {
        for(Node aNode : this.nodeTree)
        {
            if (aNode.getChildrenNode().size() == 0)aNode.setLeafNode(true);
        }
    }

    /**
     * インスタンス変数nodeTreeを応答します。
     * @return 全ノード
     */
    public List<Node> getNodeTree()
    {
        return this.nodeTree;
    }

    @Override
    public String toString()
    {
        return this.nodeTree.toString();
    }
}
