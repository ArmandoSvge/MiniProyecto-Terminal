import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String comando;



        do {
            System.out.print("Ingrese un comando: ");
            comando = scanner.nextLine().trim();
            String[]  Rutas = comando.split(" ");

            switch (Rutas[0]) {

                case "help":
                    Metodos.help(Rutas);
                    break;

                case "cd":
                    Metodos.cd(Rutas);
                    break;

                case "mkdir":

                    try {
                        Metodos.mkdir(Rutas);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "info":
                       Metodos.info(Rutas);

                    break;

                case "cat":
                      Metodos.cat(Rutas);

                    break;

                case "top":
                     Metodos.top(Rutas);

                    break;

                case "mkfile":

                      Metodos.mkfile(Rutas);
                    break;

                case "write":
                     Metodos.write(Rutas);

                    break;

                case "dir":

                     Metodos.dir(Rutas);

                    break;

                case "readpoint":

                  Metodos.readpoint(Rutas);

                    break;

                case "delete":

                    Metodos.delete(Rutas);


                    break;

                case "Start":

                 Metodos.Start(Rutas);
                    break;

                case "close":
                    Metodos.close(Rutas);

                    break;

                case "Clear":
                    Metodos.Clear(Rutas);

                    break;

                default:
                    System.out.println("Comando no reconocido.");
                    break;
            }
        } while (!comando.equalsIgnoreCase("close"));


    }
    }
