import java.io.*;
import java.net.*;
import java.util.*;


public class ThreadedEchoServer
{  
   public static void main(String[] args )
   {  
      try
      {  
         int i = 1;
         ServerSocket s = new ServerSocket(8189);

         while (true)
         {  
            Socket incoming = s.accept();
            System.out.println("Spawning connection number" + i);
            Runnable r = new ThreadedEchoHandler(incoming);
            Thread t = new Thread(r);
            t.start();
            i++;
         }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
   }
}

/**
   This class handles the client input for one server socket connection. 
*/
class ThreadedEchoHandler implements Runnable
{ 
   /**
      Constructs a handler.
      @param i the incoming socket
      @param c the counter for the handlers (used in prompts)
   */
   public ThreadedEchoHandler(Socket s)
   { 
      incoming = s; 
   }

   public void run()
   {  
      try
      {  
         try
         {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
            
            Scanner in = new Scanner(inStream);         
            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
            
            out.println( "Hello! Enter BYE to exit." );
            
            // echo client input
            boolean done = false;
            while (!done)
            {  
               String line = in.nextLine();            
               out.println("Echo: " + line);            
               if (line.trim().equals("BYE"))
                  done = true;
            }
         }
         finally
         {
            incoming.close();
         }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
   }

   private Socket incoming;
}
