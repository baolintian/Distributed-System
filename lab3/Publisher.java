import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import java.util.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

    private static String brokerURL = "tcp://localhost:61616";
    private static ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
	private Topic topic;
    
    public Publisher(String topicName) throws JMSException {
		
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		topic = session.createTopic(topicName);
        producer = session.createProducer(topic);
		
		connection.start();
    }    
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
	public static void main(String[] args) throws JMSException {
    	Publisher publisher = new Publisher("MYTOPIC");
        publisher.sendMessage();
        publisher.close();

	}
	
    public void sendMessage() throws JMSException {
        double ans = 12.34;
        Random r = new Random();
		Message message = session.createTextMessage("");
		System.out.println("The session has started");
		for(int i=0;i<2000;i++){
			
			try {
				Thread.sleep(10);
         	} catch (Exception e) {
				e.printStackTrace();
         	}
			ans = r.nextGaussian();
			message = session.createTextMessage(Double.toString(ans));
			producer.send(message);
			//System.out.println("Sent a message!");
		}
    }	

}
