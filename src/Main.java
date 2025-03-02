import java.io.IOException;

class Main
{
    public static void main(String[] args)
    {
        String FILEPATH = args[0];

        if (args.length > 1)
        {
            if (args[1].equals("-text"))
            {
                try {
                    new PDFViewer(FILEPATH, 1);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        } else
        {
            try {
                new PDFViewer(FILEPATH, 0);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}