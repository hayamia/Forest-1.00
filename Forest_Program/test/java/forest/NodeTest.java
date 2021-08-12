package forest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest
{
    /**
     * Nodeクラスの単体テストのメソッドであることを「@Test」のアノテーションで表す。
     */
    @Test
    @DisplayName("クラス「Node」の単体テスト")
    public void testInstantiationOfNode()
    {
        Node aNode = new Node("hiNode");
        assertEquals("hiNode", aNode.getNodeName());

        Node childNode = new Node("pp");
        aNode.setChildrenNode(childNode);
        assertEquals(childNode, aNode.getChildrenNode().get(0));

        aNode.setLeafNode(false);
        assertEquals(false, aNode.isLeaf());

        aNode.setNodeDepth(2);
        assertEquals(2, aNode.getNodeDepth());

        aNode.setNodeNumber(33);
        assertEquals(33, aNode.getNodeNumber());

        aNode.setNodePoint(new Point(77, 99));
        assertEquals(new Point(77, 99), aNode.getNodePoint());

        Node mamNode = new Node("mam");
        aNode.setParentNode(mamNode);
        assertEquals(mamNode, aNode.getParentNode());

        aNode.setRootNode(false);
        assertEquals(false, aNode.isRoot());

        aNode.setVisit(true);
        assertEquals(true, aNode.isVisit());
    }
}