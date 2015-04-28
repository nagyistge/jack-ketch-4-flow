package somossuinos.jackketch.workflow;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import somossuinos.jackketch.workflow.node.NodeType;

public class ActionNodeTest {

    private final static String ID = "#ID";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_create_Assign_Empty_Id_Fails() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("\"id\" must not be empty");

        final ActionNode an = new ActionNode(" ");
    }

    @Test
    public void test_create_Assign_Null_Id_Fails() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("\"id\" must not be empty");

        final ActionNode an = new ActionNode(null);
    }

    @Test
    public void test_getType_Is_The_Right_One() {
        final ActionNode an = new ActionNode(ID);
        Assert.assertEquals(NodeType.ACTION, an.getType());
    }

    @Test
    public void test_getId_Is_The_Same_As_Assigned_Id() {
        final ActionNode an = new ActionNode(ID);
        Assert.assertEquals(ID, an.getId());
    }

}