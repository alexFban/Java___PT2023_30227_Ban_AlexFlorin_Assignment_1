import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OperationTest {
    static Polynomial P1 = new Polynomial();
    static Polynomial P2 = new Polynomial();
    static Polynomial PR = new Polynomial();
    @BeforeAll
    public static void before(){
        P1.setMap(new HashMap<>(Map.of(1, 1f, 2, 1f)));
        P2.setMap(new HashMap<>(Map.of(3, 1f, 2, 3f, 0, 1f)));
    }
    @Test
    public void addTest1(){
        PR.setMap(new HashMap<>(Map.of(3, 1f, 1, 1f, 2, 4f,0, 1f)));
        assertEquals(PR.getValues(), Operation.addition(P1, P2).getValues());
    }
    @Test
    public void subTest(){
        PR.setMap(new HashMap<>(Map.of(3, -1f, 2, -2f, 1, 1f, 0, -1f)));
        assertEquals(PR.getValues(), Operation.subtraction(P1, P2).getValues());
    }
    @Test
    public void mulTest(){
        PR.setMap(new HashMap<>(Map.of(5, 1f, 4, 4f, 3, 3f, 2, 1f, 1, 1f)));
        assertEquals(PR.getValues(), Operation.multiplication(P1, P2).getValues());
    }
    @Test
    public void divTest(){
        Polynomial remainder = new Polynomial();
        remainder.setMap(new HashMap<>(Map.of(1, -2f, 0, 1f)));
        Polynomial result = new Polynomial();
        PR.setMap(new HashMap<>(Map.of(1, 1f, 0, 2f)));
        assertEquals(remainder.getValues(), Operation.division(P1, P2, result).getValues());
        assertEquals(PR.getValues(), result.getValues());
    }
    @Test
    public void derTest(){
        PR.setMap(new HashMap<>(Map.of(0, 1f, 1, 2f)));
        assertEquals(PR.getValues(), Operation.derivative(P1).getValues());
        PR.setMap(new HashMap<>(Map.of(2, 3f, 1, 6f)));
        assertEquals(PR.getValues(), Operation.derivative(P2).getValues());
    }
    @Test
    public void intTest(){
        PR.setMap(new HashMap<>(Map.of(3, 1f/3f, 2, 1f/2f)));
        assertEquals(PR.getValues(), Operation.integration(P1).getValues());
        PR.setMap(new HashMap<>(Map.of(4, 1f/4f, 3, 1f, 1, 1f)));
        assertEquals(PR.getValues(), Operation.integration(P2).getValues());
    }
}
