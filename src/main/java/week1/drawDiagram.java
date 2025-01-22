package week1;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartColor;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class drawDiagram {
    public static void main(String[] args) throws IOException {
        double[] vals = { 12, 18, 14, 15, 22, 24, 29, 28, 8, 6, 5, 9, 10 }; // this is the test data.
        drawing(vals);
    }

    public static void drawing(double[] vals) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < vals.length; i++) {
            dataset.setValue(vals[i], "Population", "class " + i); // Each bar gets an individual labal.
        }

        BarRenderer myBar = new BarRenderer();
        ChartColor color = new ChartColor(79, 129, 189); // you can change the color of bars here
        myBar.setMaximumBarWidth(0.01); // you can change the width of the bars here.
        CategoryAxis xAxis = new CategoryAxis("X"); // X axis label
        NumberAxis yAxis = new NumberAxis("Number of methods"); // Y axis labeles
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, myBar);
        JFreeChart bars = new JFreeChart(plot);
        ImageIO.write(bars.createBufferedImage(1000, 500), "png", new File("myBar.png")); // the bar is saved.
    }
}
