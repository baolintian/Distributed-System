package wsclient;
import wsproxy.*;
import java.awt.Button;  
import java.awt.FlowLayout;  
import java.awt.Frame;  
import java.awt.TextField;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.KeyAdapter;  
import java.awt.event.KeyEvent;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
  
public class CodeInfoClient {
    private Frame f;  
    private Button but;  
    private TextField tf;  
    private TextField tfOutput;
    public CodeInfoClient() {  
        init();  
    }
    private void init(){  
        f=new Frame("me frame");  
        f.setBounds(300, 200, 600, 500);  
        f.setLayout(new FlowLayout());
        tf=new TextField(30);
        tfOutput = new TextField(40);
        but=new Button("Click to query");
        f.add(tf);  
        f.add(but);  
        f.add(tfOutput);
        event();
        f.setVisible(true);  
    }
    public void process(String inString) {
    	MobileCodeWS service = new MobileCodeWS();
        MobileCodeWSSoap pService = service.getMobileCodeWSSoap();
        String ans = pService.getMobileCodeInfo(inString,"");
		tfOutput.setText(ans);
    }
    private void event(){  
        f.addWindowListener(new WindowAdapter() {  
            @Override  
            public void windowClosing(WindowEvent e) {  
                // TODO Auto-generated method stub  
                System.exit(0);  
            }
        });
        tf.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){  
                    System.out.println("我要发送消息!");
                    process(tf.getText());
                }
            }
        });
        but.addActionListener(new ActionListener() {
            @Override  
            public void actionPerformed(ActionEvent e) {  
                // TODO Auto-generated method stub  
                //System.out.println("actionPerformed 活动一次");  
            }
        });
        but.addMouseListener(new MouseAdapter() {  
            private int count=0;  
            private int clickCount=1;  
            //public void mouseEntered(MouseEvent e){  
            //    System.out.println("鼠标进入到改组件"+count++);  
            //}
            public void mouseClicked(MouseEvent e){  
                if(e.getClickCount()==2){  
                    System.out.println("双击动作");  
                }else  System.out.println("点击动作"+clickCount++); 
                process(tf.getText());
            }
        });
    }
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        new CodeInfoClient();
    }
}