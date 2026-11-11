import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 1989;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Sunucu " + port + " portunda başlatıldı. Tarayıcıdan http://localhost:1989 adresine gidebilirsiniz.");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                    String line = reader.readLine();
                    while (line != null && !line.isEmpty()) {
                        line = reader.readLine();
                    }

                    String htmlContent = "<!DOCTYPE html>\n" +
                            "<html lang=\"tr\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Ayşe Gül Açıkbaş - Biyografi</title>\n" +
                            "    <style>\n" +
                            "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f9; color: #333; text-align: center; padding-top: 50px; }\n" +
                            "        h1 { color: #d9534f; font-size: 2.5em; }\n" +
                            "        h2 { color: #0275d8; font-size: 1.8em; }\n" +
                            "        .bio-box { background: white; max-width: 600px; margin: 0 auto; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }\n" +
                            "        p { font-size: 1.1em; line-height: 1.6; color: #555; }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div class=\"bio-box\">\n" +
                            "        <h1>Ayşe Gül Açıkbaş</h1>\n" +
                            "        <h2>Öğrenci No: 1250505021</h2>\n" +
                            "        <p>Merhaba! Ben Kırklareli Üniversitesi Yazılım Mühendisliği 2. sınıf öğrencisiyim. " +
                            "Yapay zeka, nesne yönelimli programlama ve modern web teknolojileriyle yakından ilgileniyor; " +
                            "kendimi bu alanda geliştirmek için sürekli yeni projeler üretiyorum.</p>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>";

                    writer.write("HTTP/1.1 200 OK\r\n");
                    writer.write("Content-Type: text/html; charset=UTF-8\r\n");
                    writer.write("Content-Length: " + htmlContent.getBytes("UTF-8").length + "\r\n");
                    writer.write("Connection: close\r\n\r\n");

                    writer.write(htmlContent);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Sunucu hatası: " + e.getMessage());
        }
    }
}