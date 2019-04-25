//RealTimeChart .java  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.ValueAxis;  
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.time.Millisecond;  
import org.jfree.data.time.TimeSeries;  
import org.jfree.data.time.TimeSeriesCollection;  
  
public class RealTimeChart extends ChartPanel implements Runnable  
{  
    private static TimeSeries timeSeries;  
    private long value=0;  
      
    public RealTimeChart(String chartContent,String title,String yaxisName)  
    {  
        super(createChart(chartContent,title,yaxisName));  
    }  
      
    private static JFreeChart createChart(String chartContent,String title,String yaxisName){  
        timeSeries = new TimeSeries(chartContent,Millisecond.class);  
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);  
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"time unit(second)",yaxisName,timeseriescollection,true,true,false);  
        XYPlot xyplot = jfreechart.getXYPlot();  
        
        ValueAxis valueaxis = xyplot.getDomainAxis();  
        
        valueaxis.setAutoRange(true);  
        
        valueaxis.setFixedAutoRange(30000D);  
  
        valueaxis = xyplot.getRangeAxis();  
        
  
        return jfreechart;  
      }  
  
    public void run1(double x)  
    {  
        //while(true)  
        timeSeries.addOrUpdate(new Millisecond(), x);  

      
    }  
    public void run()  
    {  
        //while(true)  
        {  
        try  
        {  
            timeSeries.add(new Millisecond(), randomNum());  
            Thread.sleep(300);  
        }  
        catch (InterruptedException e)  {   }  
        }         
    }  
      
    private long randomNum()  
    {     
        System.out.println((Math.random()*20+80));        
        return (long)(Math.random()*20+80);  
    }  
}  
  