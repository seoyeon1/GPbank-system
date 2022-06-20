import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@SuppressWarnings("serial")
class GPlogin extends JFrame{
        JLabel l1, l2, l3;
        JTextField t1, t2;
        JButton b1;
        GPlogin(){
            Font f = new Font("Arial", Font.BOLD,24);
            l1 = new JLabel("GPbank");
            l1.setFont(f);
            l2 = new JLabel("���¹�ȣ");
            t1=new JTextField();
            l3=new JLabel("����ȣ");
            t2=new JTextField();
            b1 = new JButton("login");
            l1.setBounds(70,40,200,40);//��ҵ� ��ǥ������ (x,y,w,h)��ġ
            l2.setBounds(70,100,100,20);
            t1.setBounds(70,120,200,30);
            l3.setBounds(70,170,100,20);
            t2.setBounds(70,190,200,30);
            b1.setBounds(170,240,100,30);

            add(l1);//��ҵ��� ����
            add(l2);
            add(t1);
            add(l3);
            add(t2);
            add(b1);
            setLayout(null);
            setVisible(true);
            setSize(400,400);//������
    		setTitle("GBbank System");//Ÿ��Ʋ

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {//���� ����
                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GPB", "12345");
                        String sql="select * from account where account_no='"+t1.getText()+"' and customer_id='"+t2.getText()+"'";//sql������
//                      �Է��� �� �����ϴ� ���¹�ȣ&����ȣ���� �ƴ��� �Ǵ�
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        if(rs.next()){//���� ���̺� �����ϴ� ���̾����� �α��� ���� �˾�
                            JOptionPane.showMessageDialog(null, "�α��� ����!");
                            setVisible(false);//�׸��� â�� ����� �����ǰ������ �����ִ� â�� ���
                            new GPbankSYS();

                        }else{//���� ���̺� �����ϴ� ���� x������ �α��� ���� �˾�&���� ǥ��
                            JOptionPane.showMessageDialog(null, "�α��� ����...");
                        }
                    } catch (Exception e) {System.out.println(e);}
                    
                }
            });
        }
    public static void main(String[] args){
    	@SuppressWarnings("unused")
		GPlogin gplogin= new GPlogin();
	}
}

//test account: 35600119990, 1002