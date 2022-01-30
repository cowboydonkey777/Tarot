package tarotFiles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class tarot {
	
	BufferedImage img = ImageIO.read(tarot.class.getResource("/pics/01 Major_Arcana 00 Fool.jpg"));
	int cardtracker = 0;
	
	
	public static void main(String args[]) throws IOException
	{
		tarot card = new tarot();
	}
	
	public tarot() throws IOException
	{
		String[] carddesc = new String[157];
		carddesc[0] = "Draw a card, and read your fortune";
		
		carddesc[1] = "The Fool, the Protagonist. Free, innocent, with unlimited Potential, and much to learn.";
		carddesc[2] = "The Magician, of Mental Fortitude. Inner desires are manifested through personal struggle.";
		carddesc[3] = "The High Priestess, of the spirit. Harmony within, attainment of understanding";
		for(int i = 4; i < 79; i++) {
			carddesc[i] = "PLACEHOLDER UP";
		}
		
		carddesc[79] = "The Fool, of beginnings. Apathy, anxious, growing stifled, and with barriers closing around.";
		carddesc[80] = "The Magician, of Mental Fortitude. Talents are squandered, potential wasted to oblivion";
		carddesc[81] = "The High Priestess, of the spirit. Dissonance within, frustration with a clouded mind.";
		for(int i = 82; i < 156; i++) {
			carddesc[i] = "PLACEHOLDER DOWN";
		}
		
		int o_height = img.getHeight();
		int o_width = img.getWidth();
		
		int bound_width = 287;
		int bound_height = 500;

		
		System.out.println("height " + img.getHeight());
		System.out.println("width " + img.getWidth());
		
		JFrame frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setSize(bound_width, bound_height);
		frame.setMinimumSize(new Dimension(bound_width, bound_height+50));
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(img.getScaledInstance(bound_width, bound_height-40, Image.SCALE_FAST)));
		
		JButton button = new JButton("Draw");
		button.setMinimumSize(new Dimension(80, 20));
		button.setMaximumSize(new Dimension(80, 20));
		button.setBounds((frame.getHeight()/2)-40, label.getWidth()+0, 80, 20);
		
		JTextArea description = new JTextArea(carddesc[cardtracker]);
		description.setColumns(1);
		description.setRows(1);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setBounds(5, 0, 200, 30);

		frame.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent componentEvent) {
				
				int label_height = label.getHeight();
				int label_width = label.getWidth();
				int frame_height = frame.getHeight();
				int frame_width = frame.getWidth();
				int button_height = button.getHeight();
				int button_width = button.getWidth();

				if(frame_width >= label_width+27 &&
						frame_height >= label_height+49 ) {

					if((frame_height - label_height)*0.573  < 
							(frame_width - label_width) ) {
						label_height = (int) (frame_height-49-60 );
						label_width = (int) (label_height*0.573 );
						
						label_height -= 20;
						label_width -= (int)(11.46);
					}
						else if((frame_height - label_height)*0.573  >=
						(frame_width - label_width) ) {
							label_width = (int) ((frame_width-28-34 ));
							label_height = (int) (label_width*1.73);
							
							label_height -= 20;
							label_width -= (int)(11.46);
					}
				}
				else {
					if(frame_width < label_width+27) {
						label_width = (int) ((frame_width)-28-34 );
						label_height = (int) (label_width*1.73);
						
						label_height -= 20;
						label_width -= (int)(11.46);
					}
					
					else if(frame_height <= label_height+49) {
						label_height = (int) (frame_height-49-60);
						label_width = (int) (label_height * 0.573);
						
						label_height -= 20;
						label_width -= (int)(11.46);
					}
				}
				Image simg = resize(img, label_width, label_height);
				label.setIcon(new ImageIcon( simg ));
				label.repaint();
				button.repaint();
			}
		});
		
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				File dir = new File("src/pics/");
				FilenameFilter filter = new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						Random rand = new Random();
						String temp = Integer.toString((rand.nextInt(78)+1));
						if(temp.length() < 2)
							temp = "0" + temp;
						return name.startsWith(temp);
					}
				};
				String[] children = dir.list(filter);
				while(children.length == 0)
					children = dir.list(filter);
				
				String imagesrc = "/pics/" + children[0];
				String temp = children[0].substring(0, 2);
				cardtracker = Integer.parseInt(temp);
				try {
					BufferedImage bufferimg = ImageIO.read(tarot.class.getResource(imagesrc));
					int bw = label.getWidth();
					int bh = label.getHeight();
					img = resize(bufferimg, bw, bh);
					
					Random r = new Random();
					if(r.nextInt(2) == 1) {
						img = rotateImage(img, label.getWidth(), label.getHeight());
						cardtracker += 78;
					}

					//System.out.println("cardtracker: " + cardtracker);
					
						description.setText(carddesc[cardtracker]);
					
					label.setIcon(new ImageIcon( img ));
					
					frame.repaint();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		description.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.add(label);
		frame.add(button);
		frame.add(description);
		frame.setVisible(true);
		frame.setDefaultCloseOperation
         	(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	
	public static BufferedImage rotateImage(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.translate(image.getWidth()/2, image.getHeight()/2);
	    g2d.rotate(Math.PI);
	    g2d.translate(-image.getWidth()/2, -image.getHeight()/2);
		   
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
}