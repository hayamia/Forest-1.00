package forest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ForestControllerTest
{
    @Test
    @DisplayName("クラス「ForestController」の単体テスト")
    public void testInstantiationOfForestController()
    {
        ForestController aController = new ForestController();
        assertNull(aController.getView());
        assertNull(aController.getModel());
    }
}