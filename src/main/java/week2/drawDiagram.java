package week2;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.jfree.chart.ChartColor;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class drawDiagram {
    static HashMap<Integer, Integer> hashMapTask1 = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/jhotdraw");
        recursive(file);
        double[] valuesArrayTask1 = convertHashMapToArray(hashMapTask1);
        drawing(valuesArrayTask1);
    }

    public static double[] convertHashMapToArray(HashMap<Integer, Integer> hashMap) {
        double[] valuesArray = new double[hashMap.size()];
        int i = 0;
        for (Integer value : hashMap.values()) {
            valuesArray[i++] = value;
        }

        return valuesArray;
    }

    public static void recursive(File folder) throws FileNotFoundException {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                recursive(file);
            } else {
                if (file.getName().endsWith(".java")) {
                    VoidVisitor<?> cls = new ClassIdentification();
                    CompilationUnit cu = StaticJavaParser.parse(file);
                    cls.visit(cu, null);
                    task2(cu);
                }
            }
        }
    }

    static class ClassIdentification extends VoidVisitorAdapter<Void> {
        public void visit(ClassOrInterfaceDeclaration node, Void arg) {
            super.visit(node, arg);

            int methods_size = node.getMethods().size();
            if (methods_size <= 15) {
                if (hashMapTask1.get(methods_size) == null) {
                    hashMapTask1.put(methods_size, 1);
                } else {
                    hashMapTask1.compute(methods_size, (k, current_value) -> current_value + 1);
                }
            }
        }
    }

    static void task2(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cd -> {
            int methods_size = cd.getMethods().size();
            int fields_size = cd.getFields().size();

            if (methods_size > 10 && fields_size > 10) {
                System.out.println(cd.getNameAsString());
            }
        });
    }

    public static void drawing(double[] vals) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < vals.length; i++) {
            dataset.setValue(vals[i], "Population of classes with 15 or less that 15 methods", Integer.toString(i)); // Each bar gets an individual labal.
        }

        BarRenderer myBar = new BarRenderer();
        ChartColor color = new ChartColor(79, 129, 189); // you can change the color of bars here
        myBar.setMaximumBarWidth(0.01); // you can change the width of the bars here.
        CategoryAxis xAxis = new CategoryAxis("Number of Methods in a Class"); // X axis label
        NumberAxis yAxis = new NumberAxis("Number Classes"); // Y axis labeles
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, myBar);
        JFreeChart bars = new JFreeChart(plot);
        ImageIO.write(bars.createBufferedImage(1000, 500), "png", new File("lab2Task1.png")); // the bar is saved.
    }
}
