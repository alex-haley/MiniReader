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
        if (signal == 0)
            LoadPDFImage(pdfPath);
        if (signal == 1)
            LoadPDFText(pdfPath);
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

    void LoadPDFImage(String pdfPath)
    {
        try (PDDocument document = Loader.loadPDF(new File(pdfPath)))
        {
            PDFRenderer renderer = new PDFRenderer(document);

            JPanel pagePanel = new JPanel();
            pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));

            for (int page = 0; page < document.getNumberOfPages(); page++)
            {
                BufferedImage image = renderer.renderImageWithDPI(page, 70, ImageType.RGB);
                JLabel label = new JLabel(new ImageIcon(image));
                pagePanel.add(label);
            }

            JScrollPane scrollPane = new JScrollPane(pagePanel);
            LoadFrame(scrollPane, 600, 800);

            document.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void LoadPDFText(String pdfPath)
    {
        try (PDDocument document = Loader.loadPDF(new File(pdfPath)))
        {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            JPanel pagePanel = new JPanel();
            pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
            JLabel label = new JLabel(text);
            pagePanel.add(label);

            JScrollPane scrollPane = new JScrollPane(pagePanel);
            LoadFrame(scrollPane, 600, 800);
            document.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}