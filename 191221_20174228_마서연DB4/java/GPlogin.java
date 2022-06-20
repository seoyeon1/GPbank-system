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
            l2 = new JLabel("계좌번호");
            t1=new JTextField();
            l3=new JLabel("고객번호");
            t2=new JTextField();
            b1 = new JButton("login");
            l1.setBounds(70,40,200,40);//요소들 좌표값으로 (x,y,w,h)배치
            l2.setBounds(70,100,100,20);
            t1.setBounds(70,120,200,30);
            l3.setBounds(70,170,100,20);
            t2.setBounds(70,190,200,30);
            b1.setBounds(170,240,100,30);

            add(l1);//요소들을 부착
            add(l2);
            add(t1);
            add(l3);
            add(t2);
            add(b1);
            setLayout(null);
            setVisible(true);
            setSize(400,400);//사이즈
    		setTitle("GBbank System");//타이틀

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {//디비랑 연결
                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GPB", "12345");
                        String sql="select * from account where account_no='"+t1.getText()+"' and customer_id='"+t2.getText()+"'";//sql쿼리문
//                      입력한 게 존재하는 계좌번호&고객번호인지 아닌지 판단
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        if(rs.next()){//계좌 테이블에 존재하는 값이었으면 로그인 성공 팝업
                            JOptionPane.showMessageDialog(null, "로그인 성공!");
                            setVisible(false);//그리고 창을 지우고 대출상품정보를 보여주는 창을 띄움
                            new GPbankSYS();

                        }else{//계좌 테이블에 존재하는 값이 x었으면 로그인 실패 팝업&에러 표시
                            JOptionPane.showMessageDialog(null, "로그인 실패...");
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