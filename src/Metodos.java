import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Metodos {

    public static String directoActual = System.getProperty("user.dir");

    public static void help(String[] Rutas) {

        if (Rutas.length != 1) {
            System.out.println("Uso incorrecto del comando 'top'. Debe proporcionar un numero de lineas y nombre del fichero.");
            return;
        }
        System.out.println("Lista de comandos:");
        System.out.println("comando   | descripción");
        System.out.println("--------- | -----------");
        System.out.println("help      | Lista todos los comandos");
        System.out.println("cd        | Muestra la ruta del directorio actual");
        System.out.println("cd ..     | Se mueve al directorio padre");
        System.out.println("cd + nombre | Lista archivos de ese directorio");
        System.out.println("mkdir     | Crea un directorio en la ruta actual");
        System.out.println("info      | Muestra la información del elemento");
        System.out.println("cat       | Muestra el contenido de un fichero");
        System.out.println("top       | Muestra las líneas especificadas de un fichero");
        System.out.println("mkfile    | Crea un fichero con ese nombre y el contenido de texto");
        System.out.println("write     | Añade 'texto' al final del fichero especificado");
        System.out.println("dir       | Lista los archivos o directorios de la ruta actual");
        System.out.println("readpoint | Lee un archivo desde una determinada posición del puntero");
        System.out.println("delete    | Borra el fichero, si es un directorio, borra todo su contenido y a sí mismo");
        System.out.println("Start     | Ejecuta este proceso");
        System.out.println("close     | Cierra el programa");
        System.out.println("Clear     | Vacía la lista (meteme esto en un System.out.println())");

    }


    public static void cd(String[] Rutas) {
        if (Rutas.length == 1) {
            Path directorioActual = Paths.get(directoActual);
            System.out.println(directoActual);
        } else if (Rutas.length == 2 && Rutas[1].equals("..")) {
            Path directorioActual = Paths.get(directoActual).toAbsolutePath();
            Path directorioPadre = directorioActual.getParent();
            if (directorioPadre != null) {
                String nuevoDirectorio = directorioPadre.toString();
                directoActual = nuevoDirectorio;
                System.out.println(directoActual);
            } else {
                System.out.println("No se pudo cambiar al directorio padre. Estás en la raíz.");
            }
        } else if (Rutas.length == 2 && !Rutas[1].isEmpty()) {
            Path nuevoDirectorio = Paths.get(directoActual, Rutas[1]);
            if (nuevoDirectorio.toFile().exists() && nuevoDirectorio.toFile().isDirectory()) {
                directoActual = nuevoDirectorio.toAbsolutePath().toString();
                System.out.println(directoActual);
            } else {
                System.out.println("No encuentra el directorio");
            }
        } else if (Rutas.length > 2){

            System.out.println("El comando cd solo admite dos parametros");
        }
    }

    public static boolean mkdir(String[] Rutas) throws IOException {

        if (Rutas.length != 2) {
            System.out.println("Uso incorrecto del comando 'mkdir'. Debe proporcionar un nombre de directorio.");
            return false;
        }

        String nuevoDirectorio = Rutas[1];
        File directorio = new File(directoActual, nuevoDirectorio);

        if (directorio.exists()) {
            System.out.println("El directorio '" + nuevoDirectorio + "' ya existe.");
            return false;
        }

        if (directorio.mkdir()) {
            System.out.println("Directorio '" + nuevoDirectorio + "' creado correctamente.");
            return true;
        } else {
            System.out.println("No se pudo crear el directorio '" + nuevoDirectorio + "'.");
            return false;
        }
    }

    public static void info(String[] Rutas) {

        if (Rutas.length != 2) {
            System.out.println("Uso incorrecto del comando 'info'. Debe proporcionar el nombre de archivo.");
        } else {
            String nombreArchivo = Rutas[1];
            Path archivoPath = Paths.get(directoActual, nombreArchivo);

            if (Files.exists(archivoPath)) {
                try {

                    BasicFileAttributes atributos = Files.readAttributes(archivoPath, BasicFileAttributes.class);
                    long tamañoBytes = atributos.size();
                    long milisegundos = atributos.lastModifiedTime().toMillis();

                    Date fechaModificacion = new Date(milisegundos);
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fechaFormateada = formatoFecha.format(fechaModificacion);

                    System.out.println("Información del archivo '" + nombreArchivo + "':");
                    System.out.println("Tamaño: " + tamañoBytes + " bytes");
                    System.out.println("Fecha de modificación: " + fechaFormateada);


                } catch (IOException e) {
                    System.out.println("Error al obtener información del archivo '" + nombreArchivo + "': " + e.getMessage());
                }
            } else {
                System.out.println("El archivo '" + nombreArchivo + "' no existe.");
            }

        }

    }

    public static void cat(String[] Rutas) {

        if (Rutas.length != 2) {
            System.out.println("Uso incorrecto del comando 'cat'. Debe proporcionar un archivo que leer");
            return;
        }
        String nombreArchivo = Rutas[1];
        File archivo = new File(directoActual, nombreArchivo);

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("El archivo no existe", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void top(String[] Rutas) {
        if (Rutas.length != 3) {
            System.out.println("Uso incorrecto del comando 'top'. Debe proporcionar un numero de lineas y nombre del fichero.");
            return;
        }
        int limite = Integer.parseInt(Rutas[1]);
        String archivo = Rutas[2];
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(archivo));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String linea;
        int contador = 0;

        while (true) {
            try {
                if (!((linea = br.readLine()) != null && contador < limite)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(linea);
            contador++;


        }
    }
    public static void mkfile(String[] Rutas) {

        if (Rutas.length != 3) {
            System.out.println("Uso incorrecto del comando 'mkfile'. Debe proporcionar un nombre de fichero y su correspondiente texto.");
              return;
        }

        String nuevoDirectorio = Rutas[1];
        String contenido = Rutas[2];

        File directorio = new File(directoActual, nuevoDirectorio);

        if (directorio.exists()) {
            System.out.println("El directorio '" + nuevoDirectorio + "' ya existe.");

        }

        try {
            if (directorio.createNewFile()) {
                System.out.println("Archivo '" + nuevoDirectorio + "' creado correctamente.");

            } else {
                System.out.println("No se pudo crear el Archivo '" + nuevoDirectorio + "'.");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nuevoDirectorio))) {
            bw.write(contenido);
            System.out.println("Contenido escrito en el archivo correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void write(String[] Rutas) {

        if (Rutas.length != 3) {
            System.out.println("Uso incorrecto. Debe proporcionar el nombre del archivo y el contenido a agregar.");
            return;
        }

        String nombreArchivo = Rutas[0];
        String contenido = Rutas[1];
        File file = new File(directoActual, nombreArchivo);

        if (file.isFile()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true))) {

                bw.write(contenido);

                System.out.println("Contenido agregado al final del archivo correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     else {
            System.out.println("El archivo no existe");
            return;
        }


    }

    public static void dir(String[] Rutas) {

        if (Rutas.length != 1) {
            System.out.println("Uso incorrecto. Debe proporcionar solor el dir");
            return;
        }

        for (String ruta : Rutas) {
            try {
                Process ps = Runtime.getRuntime().exec("cmd /c  dir");
                System.out.println("Id del proceso " + ps.pid());

                BufferedReader reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));

                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }

                ps.destroy();
                System.out.println("Proceso finalizado");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


        public static void readpoint(String[] Rutas) {

        if (Rutas.length != 3) {
            System.out.println("Uso incorrecto. Debe proporcionar el nombre del archivo y la posición.");
            return;
        }
        File file = new File (directoActual,Rutas[1]);

        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file,"rw");
            randomAccessFile.seek(Long.parseLong(Rutas[2]));
            String linea;
            while((linea = randomAccessFile.readLine())!=null) {
                System.out.println(linea);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            randomAccessFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void delete(String[] Rutas) {
        if (Rutas.length != 2) {
            System.out.println("Uso incorrecto. Debe proporcionar el nombre del archivo o directorio a borrar.");
            return;
        }

        String nombreArchivo = Rutas[1];
        File archivoODirectorio = new File(directoActual, nombreArchivo);

        if (!archivoODirectorio.exists()) {
            System.out.println("El archivo o directorio '" + nombreArchivo + "' no existe.");
            return;
        }

        if (archivoODirectorio.isDirectory()) {

            boolean exito = borrarDirectorio(archivoODirectorio);

            if (exito) {
                System.out.println("Directorio '" + nombreArchivo + "' y su contenido han sido borrados exitosamente.");
            } else {
                System.out.println("No se pudo borrar el directorio '" + nombreArchivo + "' y su contenido.");
            }
        } else {

            if (archivoODirectorio.delete()) {
                System.out.println("Archivo o enlace simbólico '" + nombreArchivo + "' ha sido borrado exitosamente.");
            } else {
                System.out.println("No se pudo borrar el archivo o enlace simbólico '" + nombreArchivo + "'.");
            }
        }
    }

    private static boolean borrarDirectorio(File directorio) {
        File[] archivos = directorio.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {

                    if (!borrarDirectorio(archivo)) {
                        return false;
                    }
                } else {

                    if (!archivo.delete()) {
                        return false;
                    }
                }
            }
        }


        return directorio.delete();
    }

    public static void Start(String[] Rutas) {
        if (Rutas.length != 2) {
            System.out.println("Uso incorrecto. Debe proporcionar el nombre del archivo");
            return;
        }
         String PAD = Rutas[1];

        ProcessBuilder pb = new ProcessBuilder(PAD);

        try {
            Process ps = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(String[] Rutas) {

        if (Rutas.length != 2) {
            System.out.println("Uso incorrecto. Debe proporcionar el nombre del archivo");
            return;
        }
        String PAD = Rutas[1];
        try {
            // Ejecuta un comando para cerrar el programa (en Windows)
            Process proceso = Runtime.getRuntime().exec("taskkill /IM " + PAD);


            int exitCode = proceso.waitFor();


            if (exitCode == 0) {
                System.out.println("El programa se ha cerrado correctamente.");
            } else {
                System.out.println("No se pudo cerrar el programa. Código de salida: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void Clear(String[] Rutas) {
        if (Rutas.length !=1 ) {
            System.out.println("Uso incorrecto. Debe proporcionar solo el comando Clear");
            return;
        }

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");





    }

    }





















