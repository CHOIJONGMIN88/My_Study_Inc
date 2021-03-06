package mymain.tcp.multichat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiChatClient extends JFrame {

	JTextArea jta_display; // 출력창
	JTextField jtf_message; // 메시지창
	JList<String> jlist_user; // 접속자목록
	JButton jbt_connect;
	boolean bConnect = false; // 연결상태관리할 변수
	BufferedReader br;
	// 소켓

	JPanel grimPan;
	Image memPan; // 메모리상의 그림판
	int thick; // 선 굵기
	int r = 0, g = 0, b = 0; // 선 색상

	Socket client;
	String user_name = "겁나추운_오라트리(레알)"; // 대화명

	public MultiChatClient() {
		super("멀티채팅 클라이언트");

		// 조회창 초기화
		init_display();

		// 접속자목록 초기화
		init_userlist();

		// 접속자수 초기화 메시지 창 초기화
		init_input();

		init_grimpan();

		this.pack();
		this.setLocation(200, 100);
		// this.setBounds(200, 100, 400, 300);
		this.setVisible(true);

		System.out.println(grimPan);
		memPan = grimPan.createImage(400, 400);
		System.out.println(memPan);

		Graphics g = memPan.getGraphics();
		g.clearRect(0, 0, 400, 400);
		g.drawString("잘 된건가?", 10, 50);

		grimPan.repaint();
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void init_grimpan() {
		// TODO Auto-generated method stub
		JPanel p = new JPanel(new BorderLayout());

		grimPan = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				g.drawImage(memPan, 0, 0, this);
			}

		};

		grimPan.setPreferredSize(new Dimension(400, 400));
		p.add(grimPan, "Center");

		JPanel menuP = new JPanel(new GridLayout(1, 3));

		p.add(menuP, "North");

		// 선굴기
		Integer[] thick_array = { 10, 20, 30, 40, 50 };
		JComboBox<Integer> jcb_thick = new JComboBox<Integer>(thick_array);
		thick = jcb_thick.getItemAt(0);
		menuP.add(jcb_thick);
		// 2.선색상
		JButton jbt_color = new JButton("선색상");
		menuP.add(jbt_color);

		// 3.지우기
		JButton jbt_clear = new JButton("지우기");
		menuP.add(jbt_clear);

		this.add(p, "West");

		// 메뉴 이벤트 처리
		// 선 굵기(콤보박스)
		jcb_thick.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if (e.getStateChange() == ItemEvent.SELECTED) {
					thick = (Integer) e.getItem();

				}

			}
		});

		jbt_color.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// JColorChooser chooser = new JColorChooser(new Color(r,g,b));
				//
				Color color = JColorChooser.showDialog(MultiChatClient.this, "선색상", new Color(r, g, b));
				// null이 첫번쨰 파라미터에 들어가면 해상도 중앙에 오게 된다.

				if (color != null) {
					r = color.getRed();
					g = color.getGreen();
					b = color.getBlue();
				} else
					color = new Color(r, g, b);

			}
		});
		// 지우기 메뉴 이벤트

		jbt_clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				memPan.getGraphics().clearRect(0, 0, 400, 400);
				grimPan.repaint();
			}
		});
		init_mouse_event();

	}

	private void init_mouse_event() {
		// TODO Auto-generated method stub

		MouseAdapter adapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				my_send_line(e.getPoint());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDragged(e);
				my_send_line(e.getPoint());

			}

		};

		// grimPan에 마우스 이벤트를 추가한다.
		grimPan.addMouseListener(adapter); // mousePressed
		grimPan.addMouseMotionListener(adapter); // mouseDragged

	}

	protected void my_send_line(Point point) {
		// TODO Auto-generated method stub

		if (bConnect == false)
			return;

		// 서버로 그리기 데이터 전송
		String send_message = String.format("GRIM#%d#%d#%d#%d#%d#%d\n", point.x,
				point.y, 
				thick,
				r,
				g,
				b);
		
		try {
			//그림데이터 전송
			client.getOutputStream().write(send_message.getBytes());
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		

	}

	private void init_input() {
		// TODO Auto-generated method stub

		JPanel p = new JPanel(new BorderLayout());

		// 메시지창
		jtf_message = new JTextField();

		p.add(jtf_message, "Center");

		// 연결버튼
		jbt_connect = new JButton("연결");
		p.add(jbt_connect, "East");
		jbt_connect.setPreferredSize(new Dimension(150, 10));

		this.add(p, "South");

		jbt_connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 이건 토글 방식이라고 한다.
				bConnect = !bConnect;

				if (bConnect) {
					// 연결작업
					try {
						client = new Socket("inca001", 7500);
						// client = new Socket("inca001", 8500);
						// 최초접속정보 전송
						String send_message = String.format("IN#%s\n", user_name);
						// 여기서 반드시 \n을 해야 한다. 버퍼드리더이기 때문에
						client.getOutputStream().write(send_message.getBytes());

						my_read_message();

					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						// e1.printStackTrace();
						bConnect = false;
						System.out.println("어느 주소인지 모르겠다.");
						System.out.println("연결실패");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						// e1.printStackTrace();
						bConnect = false;
						System.out.println("서버가 죽어있는거 같은데?");
						System.out.println("연결실패");
					}

				} else {
					// 끊기작업
					try {
						client.close();
						my_clear_userlist();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						// e1.printStackTrace();
						System.out.println("연결해제할 서버가 죽어있는 거 같은데?");
					}

				}

				jbt_connect.setText(bConnect ? "끊기" : "연결");

			}
		});

		// 키이벤트 처리
		init_key_event();

	}

	protected void my_clear_userlist() {
		// TODO Auto-generated method stub
		String[] empty = new String[0];
		jlist_user.setListData(empty);
	}

	protected void my_read_message() {
		// TODO Auto-generated method stub
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				while (true) {

					try {
						String readStr = br.readLine();
						if (readStr == null)
							break;

						// 수신데이터 프로토콜 분리작업
						String[] data = readStr.split("#");
						String display_message;
						switch (data[0]) {
						case "IN":
							display_message = String.format("[%s]님 입장", data[1]);
							my_display_message(display_message);
							break;
						case "OUT":
							display_message = String.format("[%s]님 퇴장", data[1]);
							my_display_message(display_message);
							break;
						case "LIST":
							my_display_userlist(data);
							break;
						// 사용자접속자 목록이 변경상황
						// data = "LIST#길동1#길동2#"
						// data = { "LIST","길동1","길동2"};
						case "CHAT":
							display_message = String.format("[%s] : %s", data[1], data[2]);
							my_display_message(display_message);
							break;
						case "GRIM":
							Point p = new Point(Integer.parseInt(data[1]),Integer.parseInt(data[2]));
							int t = Integer.parseInt(data[3]);
							int red = Integer.parseInt(data[4]);
							int green = Integer.parseInt(data[5]);
							int blue = Integer.parseInt(data[6]);
							my_draw_line(p,t,red,green,blue);
							
							

						}


					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						break;
					}

				}
				bConnect = false;
				jbt_connect.setText("연결");
			}
		}.start();

	}

	protected void my_draw_line(Point p,int t,int red,int green, int blue) {
		// TODO Auto-generated method stub
		Graphics g1 = memPan.getGraphics();
		Color color = new Color(red, green, blue);

		g1.setColor(color);
		g1.fillOval(p.x - t / 2, p.y - t / 2, t, t);

		// 메모리 -->화면복사 : paintComponent에서 복사코드가 있다.
		grimPan.repaint();
	}

	public void my_display_message(String message) {

		jta_display.append(message + "\r\n");

		int position = jta_display.getDocument().getLength();
		jta_display.setCaretPosition(position);

	}

	protected void my_display_userlist(String[] user_array) {
		// TODO Auto-generated method stub

		String[] name_array = new String[user_array.length - 1];

		System.arraycopy(user_array, 1, name_array, 0, name_array.length);

		jlist_user.setListData(name_array);

	}

	private void init_key_event() {
		// TODO Auto-generated method stub

		KeyAdapter adapater = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_ENTER) {
					my_send_message();

				}
			}
		};
		jtf_message.addKeyListener(adapater);

	}

	protected void my_send_message() {
		// TODO Auto-generated method stub
		if (bConnect == false) {
			jtf_message.setText("");
			JOptionPane.showMessageDialog(this, "서버 연결 후 이용하세요");
			return;

		}

		String message = jtf_message.getText().trim();
		if (message.isEmpty())
			return;

		String send_message = String.format("CHAT#%s#%s\n", user_name, message); // 전송

		try {
			client.getOutputStream().write(send_message.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("--메시지 전송 실패--");
		}
		jtf_message.setText("");
	}

	private void init_userlist() {
		// TODO Auto-generated method stub
		jlist_user = new JList<String>();
		JScrollPane jsp = new JScrollPane(jlist_user);

		this.add(jsp, "East");

		jsp.setPreferredSize(new Dimension(150, 400));

		// String[] user_array = { "길동1", "길동2", "삼식이", "길동3" };
		// jlist_user.setListData(user_array);

	}

	private void init_display() {
		// TODO Auto-generated method stub
		jta_display = new JTextArea();
		JScrollPane jsp = new JScrollPane(jta_display);

		this.add(jsp, "Center");

		jsp.setPreferredSize(new Dimension(400, 400));

		// 읽기전용
		jta_display.setEditable(false);

	}

	public static void main(String[] args) {
		new MultiChatClient();
		

	}

}