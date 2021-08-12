package forest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ForestViewTest
{
    @Test
    @DisplayName("クラス「ForestView」の単体テスト")
    public void testInstantiationOfForestView()
    {
        ForestView aView = new ForestView();
        assertNull(aView.getModel());
        assertNull(aView.getController());
        Point aPoint = new Point(0, 0);
        assertEquals(aPoint, aView.getOffset());
    }
}