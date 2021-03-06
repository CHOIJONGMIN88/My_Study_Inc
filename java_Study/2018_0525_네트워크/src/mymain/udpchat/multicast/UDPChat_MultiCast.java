package mymain.udpchat.multicast;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UDPChat_MultiCast extends JFrame {

	JTextArea jta_display;// 출력창
	JTextField jtf_address;// 주소창
	JTextField jtf_message;// 입력창
	JScrollPane jsp;

	// 대화명
	String user_name = "오라트리-삼3-";

	Font font = new Font("굴림체", Font.PLAIN, 20);

	DatagramSocket socket;

	// 멀티캐스트 소켓 사용
	MulticastSocket socket_m;

	public UDPChat_MultiCast() {
		super("UDP 채팅");

		// 출력창 초기화
		init_display();

		// 입력창 초기화
		init_input();
		init_socket();

		jta_display.setFont(font);
		jtf_address.setFont(font);
		jtf_message.setFont(font);

		this.setResizable(false);

		this.setLocation(200, 100);
		// this.setBounds(200, 100, 400, 300);
		this.pack();
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void init_socket() {
		// TODO Auto-generated method stub

		try {
			//socket = new DatagramSocket(7000);
			socket_m = new MulticastSocket(8000);
			socket_m.setTimeToLive(10);

			my_read_message();

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void my_read_message() {
		// TODO Auto-generated method stub
		// 수신 대기용 Thread 생성
		new Thread() {

			public void run() {
				while (true) {

					try {
						byte[] buff = new byte[512];
						// 수신된 데이터는 byte[] buff에 들어간다.
						DatagramPacket dp = new DatagramPacket(buff, buff.length);

						// 데이터 수신
						// socket.receive(dp);
						socket_m.receive(dp);

						byte[] read_bytes = dp.getData();

						String read_message = new String(read_bytes).trim();
						display_message(read_message);

						InetAddress you_ia = dp.getAddress();
						System.out.printf("[%s]:%s\n", you_ia.getHostAddress(), read_message);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};

		}.start();

	}

	private void init_input() {
		// TODO Auto-generated method stub
		JPanel p = new JPanel(new GridLayout(3, 1));
		this.add(p, "South");

		jtf_address = new JTextField("224.0.0.1");

		// 가입/탈퇴
		JPanel p1 = new JPanel(new GridLayout(1, 2));
		JButton jbt_register = new JButton("가입");
		JButton jbt_unregister = new JButton("탈퇴");
		p1.add(jbt_register);
		p1.add(jbt_unregister);

		jtf_message = new JTextField();

		p.add(jtf_address);
		p.add(p1);
		p.add(jtf_message);

		input_key_event();

		// 가입/탈퇴 이벤트 처리
		jbt_register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				my_register();

			}
		});

		jbt_unregister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				my_unregister();

			}
		});

	}

	protected void my_unregister() {
		// TODO Auto-generated method stub
		try {
			String ip_address = jtf_address.getText();
			InetAddress mcastaddr = InetAddress.getByName(ip_address);
			// 멀티 캐스트 그룹에 탈퇴
			socket_m.leaveGroup(mcastaddr);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			JOptionPane.showMessageDialog(this, "가입이 되어있지 않습니다.");

		}
	}

	protected void my_register() {
		// TODO Auto-generated method stub
		try {
			String ip_address = jtf_address.getText();
			InetAddress mcastaddr = InetAddress.getByName(ip_address);
			// 멀티 캐스트 그룹에 가입
			socket_m.joinGroup(mcastaddr);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			JOptionPane.showMessageDialog(this, "이미 가입되었습니다");

		}

	}

	private void input_key_event() {
		// TODO Auto-generated method stub
		KeyAdapter adapter = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_ENTER) {
					// 메시지 전송
					send_mesage();
				}
			}

		};

		jtf_message.addKeyListener(adapter);
	}

	protected void send_mesage() {
		// TODO Auto-generated method stub
		// 1.입력값 얻어오기
		String message = jtf_message.getText().trim();

		// 입력된 메세지가 없으면 끝내라.
		if (message.isEmpty()) {
			jtf_message.setText("");
			jtf_message.requestFocus();// 포커스 설정
			return;
		}

		// 2.얻어온 입력값을 패키징한다.: =>홍길동#안녕하세요
		String send_message = String.format("%s#%s", user_name, message);

		// 3.네트워크 전송작업

		try {
			// 전송지 주소얻기
			String ip_address = jtf_address.getText();
			InetAddress ia = InetAddress.getByName(ip_address);
			// String->byte[]
			byte[] send_bytes = send_message.getBytes();
			DatagramPacket dp = new DatagramPacket(send_bytes, send_bytes.length, ia, 8000);
			// 전송

			// socket.send(dp);
			socket_m.send(dp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 수신된 데이터 출력
		// display_message(send_message);

		// 4.입력창 비우기
		jtf_message.setText("");

	}

	private void display_message(String send_message) {
		// TODO Auto-generated method stub
		System.out.println(send_message);
		String[] message_array = send_message.split("#");
		String display_message = String.format("%s님 말씀\r\n   %s\r\n", message_array[0], message_array[1]);
		jta_display.append(display_message);

		// 입력포커스를 마지막에 가져다 놓는다.
		int position = jta_display.getDocument().getLength();
		jta_display.setCaretPosition(position);
	}

	private void init_display() {
		// TODO Auto-generated method stub
		jta_display = new JTextArea();
		jsp = new JScrollPane(jta_display);
		jsp.setPreferredSize(new Dimension(400, 500));

		this.add(jsp, "Center");

		// 출력창 읽기전용
		jta_display.setEditable(false);

	}

	public static void main(String[] args) {
		new UDPChat_MultiCast();

	}

}