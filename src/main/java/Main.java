import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main extends Application{
    TextField polyOne = new TextField();
    TextField polyTwo = new TextField();
    TextField answer = new TextField();
    TextField remainder = new TextField("Remainder: ");
    Button addBtn = new Button("+");
    Button subBtn = new Button("-");
    Button mulBtn = new Button("*");
    Button divBtn = new Button("/");
    Button derBtn = new Button("'");
    Button intBtn = new Button("S");
    Button clearPOne = new Button("C1");
    Button clearPTwo = new Button("C2");
    Button equal = new Button("=");
    GridPane root = new GridPane();
    StringBuffer operation = new StringBuffer();
    public static void main()
    {
        launch();
    }
    public boolean isCorrect(Matcher copyMatcher) {
        if(copyMatcher.group().equals(""))
        {
            copyMatcher.find();
            if(copyMatcher.group()!=null)
            {
                if(!copyMatcher.group().equals(""))
                    return false;
            }
            else
                return true;
        }
        if(copyMatcher.group().length() < 2 && !(new String("123456789").contains(copyMatcher.group())))
            return false;
        if(!copyMatcher.group(4).isEmpty() && copyMatcher.group(3) == null)
            return false;
        if(copyMatcher.group(3)!=null && copyMatcher.group(3).length() != 2)
            return false;
        return true;
    }
    public Polynomial parsePol(String copy) {
        Polynomial parsedPol = new Polynomial();
        String patternString = "(([+-]?[0-9]*[.]?[0-9]*)(x\\^*)?([+-]*?[0-9]*))";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(copy);
        int pwr;
        float coeff;
        while(matcher.find()) {
            if(!isCorrect(matcher)) {
                answer.setText("Incorrect Polynomial format");
                return new Polynomial();
            }
            if(matcher.group() == null)
                    break;
            if(matcher.group(4).isEmpty())
                pwr = 0;
            else
                pwr = Integer.parseInt(matcher.group(4));
            if(matcher.group(2).isEmpty())
                coeff = 1;
            else if(matcher.group(2).toString().equals(new String("-")))
                coeff = -1;
            else if(matcher.group(2).toString().equals(new String("+")))
                coeff = 1;
            else
                coeff = Float.parseFloat(matcher.group(2));
            parsedPol.addValue(pwr, coeff);
        }
        parsedPol.setMap(new HashMap<>(new TreeMap<>(parsedPol.getValues())));
        return parsedPol;
    }
    public StringBuffer writePol(Polynomial copyPol) {
        StringBuffer answer = new StringBuffer("");
        TreeMap<Integer, Float> copy = new TreeMap<>(copyPol.getValues());
        if(copy.isEmpty())
            answer.insert(0, "0");
        else
            for(Map.Entry<Integer, Float> entry : copy.entrySet()) {
                if(entry.getKey() != 0)
                    answer.insert(0, "x^" + Integer.toString(entry.getKey()) );
                if((entry.getValue() !=1 && entry.getValue() != -1) || entry.getKey() == 0) {
                    if(entry.getValue() == Math.round(entry.getValue()))
                        answer.insert(0, String.format("%.0f", entry.getValue()));
                    else
                        answer.insert(0, Float.toString(entry.getValue()));
                }
                else if(entry.getValue() == -1)
                    answer.insert(0, "-");
                if(entry.getValue() > 0)
                    answer.insert(0, "+");
            }
        if(answer.charAt(0) == '+')
            answer.delete(0, 1);
        return  answer;
    }
    public void calculate(StringBuffer operation) {
        remainder.setText("Remainder: ");
        String bufferPol1 = polyOne.getText();
        String bufferPol2 = polyTwo.getText();
        Polynomial copyPol1 = parsePol(bufferPol1);
        Polynomial copyPol2 = parsePol(bufferPol2);
        if(copyPol1.getValues().isEmpty() || copyPol2.getValues().isEmpty())
            return;
        Polynomial result = new Polynomial();
        if(operation.toString().equals(new String("add")))
            result = Operation.addition(copyPol1, copyPol2);
        if(operation.toString().equals(new String("sub")))
            result = Operation.subtraction(copyPol1, copyPol2);
        if(operation.toString().equals(new String("mul")))
            result = Operation.multiplication(copyPol1, copyPol2);
        if(operation.toString().equals(new String("div")))
            remainder.setText("Remainder: " + writePol(Operation.division(copyPol1, copyPol2, result)).toString());
        if(operation.toString().equals(new String("der")))
            result = Operation.derivative(copyPol1);
        if(operation.toString().equals(new String("int")))
            result = Operation.integration(copyPol1);
        answer.setText(writePol(result).toString());
    }
    public void setWidth() {
        polyOne.setPrefWidth(210);
        polyTwo.setPrefWidth(210);
        polyTwo.setPrefWidth(210);
        addBtn.setPrefWidth(100);
        subBtn.setPrefWidth(100);
        mulBtn.setPrefWidth(100);
        divBtn.setPrefWidth(100);
        derBtn.setPrefWidth(100);
        intBtn.setPrefWidth(100);
        clearPOne.setPrefWidth(45);
        clearPTwo.setPrefWidth(45);
        equal.setPrefWidth(100);
    }
    public void setColor() {
        addBtn.setStyle("-fx-background-color: White");
        subBtn.setStyle("-fx-background-color: White");
        mulBtn.setStyle("-fx-background-color: White");
        divBtn.setStyle("-fx-background-color: White");
        derBtn.setStyle("-fx-background-color: White");
        intBtn.setStyle("-fx-background-color: White");
    }
    public void initializeRoot() {
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(20);
    }
    public void setButtons() {
        root.add(polyOne, 0, 0, 4, 1);
        root.add(polyTwo, 0, 1, 4, 1);
        root.add(answer, 0, 6, 4, 1);
        root.add(remainder, 0, 7, 4, 1);
        root.add(addBtn, 0, 2, 2, 1);
        root.add(subBtn, 2, 2, 2, 1);
        root.add(mulBtn, 0, 3, 2, 1);
        root.add(divBtn, 2, 3, 2, 1);
        root.add(derBtn, 0, 4, 2, 1);
        root.add(intBtn, 2, 4, 2, 1);
        root.add(clearPOne, 0, 5);
        root.add(clearPTwo, 1, 5);
        root.add(equal, 2, 5, 2, 1);
    }
    public void activateButton(Button btn, String opText) {
        setColor();
        btn.setStyle("-fx-background-color: MediumSeaGreen");
        operation.replace(0, 3, opText);
    }
    public void setActionsOfButtons() {
        addBtn.setOnAction(e -> activateButton(addBtn, "add"));
        subBtn.setOnAction(e -> activateButton(subBtn, "sub"));
        mulBtn.setOnAction(e -> activateButton(mulBtn, "mul"));
        divBtn.setOnAction(e -> activateButton(divBtn, "div"));
        derBtn.setOnAction(e -> activateButton(derBtn, "der"));
        intBtn.setOnAction(e -> activateButton(intBtn, "int"));
        clearPOne.setOnAction(e -> polyOne.setText(""));
        clearPTwo.setOnAction(e -> polyTwo.setText(""));
        equal.setOnAction(e -> calculate(operation));
    }
    public void start(Stage stage) throws Exception {
        setWidth();
        setColor();
        initializeRoot();
        setButtons();
        setActionsOfButtons();
        answer.setEditable(false);
        remainder.setEditable(false);
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Polynomial Calculator");
        stage.show();
    }
}