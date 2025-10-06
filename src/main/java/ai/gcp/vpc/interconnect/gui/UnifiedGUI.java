package ai.gcp.vpc.interconnect.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import ai.gcp.vpc.interconnect.repository.InterconnectRepository;
import ai.gcp.vpc.interconnect.repository.VPCRepository;

public class UnifiedGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final VPCRepository vpcRepo = new VPCRepository();
	private final InterconnectRepository icRepo = new InterconnectRepository();
	private final JTextArea outputArea = new JTextArea(20, 60); // For displaying results

	public UnifiedGUI() {
		setTitle("AI + Google VPC & Interconnect CRUD App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// === Output Panel ===
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane outputScroll = new JScrollPane(outputArea);
		add(outputScroll, BorderLayout.CENTER);

		// === Button Panel ===
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		// VPC Buttons
		JPanel vpcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		vpcPanel.setBorder(BorderFactory.createTitledBorder("VPC Operations"));
		JButton btnShowVPCs = new JButton("Show VPCs");
		JButton btnInsertVPC = new JButton("Insert VPC");
		JButton btnUpdateVPC = new JButton("Update VPC");
		JButton btnDeleteVPC = new JButton("Delete VPC");
		JButton btnGetVPC = new JButton("Get VPC by ID");
		vpcPanel.add(btnShowVPCs);
		vpcPanel.add(btnInsertVPC);
		vpcPanel.add(btnUpdateVPC);
		vpcPanel.add(btnDeleteVPC);
		vpcPanel.add(btnGetVPC);

		// Interconnect Buttons
		JPanel icPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		icPanel.setBorder(BorderFactory.createTitledBorder("Interconnect Operations"));
		JButton btnShowICs = new JButton("Show Interconnects");
		JButton btnInsertIC = new JButton("Insert Interconnect");
		JButton btnUpdateIC = new JButton("Update Interconnect");
		JButton btnDeleteIC = new JButton("Delete Interconnect");
		JButton btnGetIC = new JButton("Get Interconnect by ID");
		icPanel.add(btnShowICs);
		icPanel.add(btnInsertIC);
		icPanel.add(btnUpdateIC);
		icPanel.add(btnDeleteIC);
		icPanel.add(btnGetIC);

		buttonPanel.add(vpcPanel);
		buttonPanel.add(icPanel);

		JScrollPane buttonScroll = new JScrollPane(buttonPanel);
		buttonScroll.setPreferredSize(new Dimension(800, 160));
		add(buttonScroll, BorderLayout.NORTH);

		// === Action Listeners for VPC ===
		btnShowVPCs.addActionListener(e -> outputArea.setText(vpcRepo.getAllVPCs()));

		btnInsertVPC.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter VPC Name:");
			String subnets = JOptionPane.showInputDialog("Enter Subnet(s):");
			String region = JOptionPane.showInputDialog("Enter Region:");
			String ipRange = JOptionPane.showInputDialog("Enter IP Range:");
			vpcRepo.insertVPC(name, subnets, region, ipRange);
			outputArea.setText("Inserted VPC: " + name);
		});

		btnUpdateVPC.addActionListener(e -> {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter VPC ID to Update:"));
			String name = JOptionPane.showInputDialog("Enter New VPC Name:");
			String subnets = JOptionPane.showInputDialog("Enter New Subnet(s):");
			String region = JOptionPane.showInputDialog("Enter New Region:");
			String ipRange = JOptionPane.showInputDialog("Enter New IP Range:");
			vpcRepo.updateVPC(id, name, subnets, region, ipRange);
			outputArea.setText("Updated VPC ID: " + id);
		});

		btnDeleteVPC.addActionListener(e -> {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter VPC ID to Delete:"));
			vpcRepo.deleteVPC(id);
			outputArea.setText("Deleted VPC ID: " + id);
		});

		btnGetVPC.addActionListener(e -> {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter VPC ID to Get:"));
			String result = vpcRepo.getVPCById(id);
			outputArea.setText(result);
		});

		// === Action Listeners for Interconnect ===
		btnShowICs.addActionListener(e -> outputArea.setText(icRepo.getAllInterconnects()));

		btnInsertIC.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter Interconnect Name:");
			String location = JOptionPane.showInputDialog("Enter Location:");
			String bandwidth = JOptionPane.showInputDialog("Enter Bandwidth:");
			String state = JOptionPane.showInputDialog("Enter State:");
			icRepo.insertInterconnect(name, location, bandwidth, state);
			outputArea.setText("Inserted Interconnect: " + name);
		});

		btnUpdateIC.addActionListener(e -> {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Interconnect ID to Update:"));
			String name = JOptionPane.showInputDialog("Enter New Name:");
			String location = JOptionPane.showInputDialog("Enter New Location:");
			String bandwidth = JOptionPane.showInputDialog("Enter New Bandwidth:");
			String state = JOptionPane.showInputDialog("Enter New State:");
			icRepo.updateInterconnect(id, name, location, bandwidth, state);
			outputArea.setText("Updated Interconnect ID: " + id);
		});

		btnDeleteIC.addActionListener(e -> {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Interconnect ID to Delete:"));
			icRepo.deleteInterconnect(id);
			outputArea.setText("Deleted Interconnect ID: " + id);
		});

		btnGetIC.addActionListener(e -> {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Interconnect ID to Get:"));
			String result = icRepo.getInterconnectById(id);
			outputArea.setText(result);
		});

		pack();
		setLocationRelativeTo(null); // Center window
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(UnifiedGUI::new);
	}
}
