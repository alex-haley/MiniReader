// standard library
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// exceptions
import java.io.IOException;

// apache pdfbox
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

public class PDFViewer extends JFrame
{
    public PDFViewer(String pdfPath, int signal) throws IOException
    {
	LoadPDF(pdfPath, signal);
    }

    void LoadFrame(JScrollPane scrollPane, int SizeX, int SizeY)
    {
        setTitle("PDF Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(scrollPane);
        setSize(SizeX, SizeY);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // this function need some refactoring
    // because we dont need to use JPanel and
    // return it at the same time...
    // this is just redicoulus.
    JPanel FillPanel(PDDocument document, JPanel pagePanel, int signal)
    {
	try {
	    if (signal == 0)
	    {
		PDFRenderer renderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); page++)
		{
		    BufferedImage image = renderer.renderImageWithDPI(page, 70, ImageType.RGB);
		    JLabel label = new JLabel(new ImageIcon(image));
		    pagePanel.add(label);
		}
		return pagePanel;
	    } else
	    {			    
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(document);
		JLabel label = new JLabel(text);
		pagePanel.add(label);
		return pagePanel;
	    }
	} catch (IOException e)
	{
	    e.printStackTrace();
	}

	JPanel zeroPanel = new JPanel();
	return zeroPanel;
    }

    void LoadPDF(String pdfPath, int signal)
    {
        try (PDDocument document = Loader.loadPDF(new File(pdfPath)))
        {
            JPanel pagePanel = new JPanel();
            pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));

	    pagePanel = FillPanel(document, pagePanel, signal);
	    
            JScrollPane scrollPane = new JScrollPane(pagePanel);
            LoadFrame(scrollPane, 600, 800);
            document.close();
	    
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
