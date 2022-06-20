import java.awt.BorderLayout;
//import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GPbankSYS extends JFrame {
	private Connection conn = null; 
	private JLabel state;
	private String colName[] = { 
			"대출상품명", "이자(금리)", "신용등급"
			};//화면 상단부에 보여질 속성 이름들
	private DefaultTableModel model = new DefaultTableModel(colName, 0);

	private JTable table = new JTable(model);
	private String row[] = new String[3];//열 인덱스(대출타입T 속성 : 3개)
	

	public GPbankSYS() { 
		setTitle("GBbank Loan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLayout(new BorderLayout());
	
	JPanel Btn_panel = new JPanel();
	Btn_panel.setLayout(new FlowLayout());
	JButton con = new JButton("Connect"); 
	JButton show = new JButton("read more");
//	JButton dep_info = new JButton("");

	
	Btn_panel.add(con);
	Btn_panel.add(show);
	 
	con.addActionListener(new ActionListener() { 
		@Override 
		public void actionPerformed(ActionEvent e) { 
			connect();
			} 
		});

	
	 
	show.addActionListener(new ActionListener() { 
		@Override 
		public void actionPerformed(ActionEvent e) { 
			show_db(); 
			} 
		});

	
	state = new JLabel(); 
	state.setText("GP bank 대출 상품");
	
	
	add(state, BorderLayout.NORTH);
	
	add(new JScrollPane(table), BorderLayout.CENTER);
	add(Btn_panel, BorderLayout.SOUTH);
	
	setSize(400, 300);
	setVisible(true);
	}
	public void connect() { 
		try { 
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			conn = DriverManager.getConnection( 
					"jdbc:oracle:thin:@localhost:1521:xe", "GPB", "12345");
			System.out.println("대출 정보 로딩중"); state.setText("GP bank에서 제공하는 대출 상품입니다.");
			
		} catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
			state.setText("DB 연결 실패 " + e.toString());
			} catch (SQLException e) { 
				e.printStackTrace(); 
				state.setText("DB 연결 실패 " + e.toString());
			}
	}
	public void show_db() { 
		try {  
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from loantype");
			
			System.out.println("손님, GP대출상품정보는 GUI로 보세요~");
			
			while (rset.next()) { 
				
				for (int i = 1; i < 4; i++) { 
					row[i - 1] = rset.getString(i); 
					}
				model.addRow(row); 
				} 
			state.setText("GP대출상품"); 
			} catch (SQLException e) { 
				e.printStackTrace(); 
				state.setText("GP대출상품 읽기 실패 " + e.toString()); 
				}
			}

	
	public static void main(String[] args) {
		new GPbankSYS();
	}
}