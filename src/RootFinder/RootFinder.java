/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootFinder;

import RootFinder.Functions.Euler;
import RootFinder.Functions.Logarithm;
import RootFinder.Functions.Quadratic;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 *
 * @author Abdullah
 */
public class RootFinder {
    public static void main(String[] args) {
        RootFinder rootFinder = new RootFinder();
    }
    
    public RootFinder() {
        XYChart chart = new XYChartBuilder().width(600).height(500).build();
        
        chart.addSeries("Quadratic", new Quadratic(true).getX(), new Quadratic(true).getY()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Logarithm", new Logarithm(true).getX(), new Logarithm(true).getY()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Euler", new Euler(true).getX(), new Euler(true).getY()).setMarker(SeriesMarkers.NONE);
        new SwingWrapper<>(chart).displayChart();
    }
}
