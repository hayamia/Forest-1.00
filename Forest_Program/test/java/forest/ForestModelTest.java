package forest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForestModelTest
{
    @Test
    @DisplayName("クラス「ForestModel」の単体テスト")
    public void testInstantiationOfForestModel()
    {
        ForestModel aModel = new ForestModel();
        assertNull(aModel.getView());
        assertNull(aModel.getController());
    }
}