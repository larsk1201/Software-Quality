package jp;

import jp.io.AccessorFactory;
import jp.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** JabberPoint Main Program */
public class JabberPoint {
	/** The Frame for the ShowView */
	protected static JFrame frame;
	/** The model */
	protected Model model;
	/** The view */
	protected static ShowView slideView;
	/** Other View */
	protected static JList<Slide> textView;
	/** The styles */
	protected static Style[] styles;
	private static Style codeStyle;

	/** The Real Main Program */
	public static void main(String argv[]) {
		try {
			JabberPoint jp = new JabberPoint();

			if (argv.length == 0) { // run a demo program
				AccessorFactory.getInstance(AccessorFactory.DEMO_NAME).loadFile(jp.model, "");
			} else {
				AccessorFactory.getInstance(argv[0]).loadFile(jp.model, argv[0]);
			}
			jp.model.setSlideNumber(0);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "IO Error: " + ex, "JabberPoint Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	/** Construct a JabberPoint Program */
	JabberPoint() {
		model = new Model();
		slideView = new ShowView(model);
		textView = new JList<>(new DefaultListModel<>());
		model.addObserver(slideView);

		frame = new JFrame("JabberPoint 0.1");
		model.setParentView(frame);
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textView, slideView);
		frame.setContentPane(pane);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		pane.setResizeWeight(0.4);
		frame.setVisible(true);

		final KeyController keyController = new KeyController(model);
		slideView.addKeyListener(keyController);
		textView.addKeyListener(keyController);
		frame.setJMenuBar(new MenuController(frame, model));

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});

		styles = new Style[]{
				new Style(50, Color.red, 40, 48),
				new Style(20, Color.blue, 32, 36),
				new Style(50, Color.black, 24, 28),
				new Style(70, Color.black, 20, 24),
				new Style(90, Color.black, 16, 20),
		};
		codeStyle = new Style(50, Color.black, 20, 4);
	}

	public static Style getStyle(int lev) {
		if (lev >= styles.length)
			lev = styles.length - 1;
		return styles[lev];
	}

	public static Graphics getGraphics() {
		return frame.getGraphics();
	}

	public static Style getCodeStyle() {
		return codeStyle;
	}

	public static JFrame getFrame() {
		return frame;
	}
}
