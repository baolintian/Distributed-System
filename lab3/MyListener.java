import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.math.*;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.ValueAxis;  
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.time.Millisecond;  
import org.jfree.data.time.TimeSeries;  
import org.jfree.data.time.TimeSeriesCollection;  

public class MyListener implements MessageListener {
	int block = 200;//处理块的大小
	double[] data = new double[2000+10];
	double tot = 0;
	double ave = 0;//均值
	double avar = 0;//方差
	int idx = 0;
	double low, high;
	int anormal = 0;//奇异点的个数
	JFrame frame=new JFrame("Test Chart");  
	RealTimeChart rtcp=new RealTimeChart("Random Data","time","y value");  
	MyListener(){
		super();
	    frame.getContentPane().add(rtcp,new BorderLayout().CENTER);  
	    frame.pack();  
	    frame.setVisible(true);  
	    frame.addWindowListener(new WindowAdapter()   
	    {  
	        public void windowClosing(WindowEvent windowevent)  
	        {  
	            System.exit(0);  
	        }  
	  
	    });
		
	}
	public void onMessage(Message message) {
		
		try {
			data[idx++] = Double.parseDouble(((TextMessage)message).getText());
			//System.out.println(data[idx-1]);
			tot += data[idx-1];
			rtcp.run1(data[idx-1]);
			if(idx%block == 0){
				ave = tot/block;
				for(int i=0; i<block; i++){
					avar += (data[i]-ave)*(data[i]-ave);
				}
				avar = Math.sqrt(avar/block);
				System.out.printf("The average number is %.4f, the variance is %.4f\n", ave, avar);
				low = ave-3*Math.sqrt(avar);
				high = ave+3*Math.sqrt(avar);
				for(int i=0; i<block; i++){
					if(data[i]<low||data[i]>high){
						anormal++;
					}
				}
				System.out.printf("The abnormal points number is %d\n", anormal);
				anormal = 0; idx = 0; avar = 0; ave = 0; tot = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
