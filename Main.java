package Canal;

import java.util.concurrent.*;

class Canal<T> {
    private final BlockingQueue<T> queue;

    public Canal(int capacidad, boolean sincrono) {
        if (sincrono) {
            queue = new LinkedBlockingQueue<>(capacidad);
        } else {
            queue = new LinkedBlockingQueue<>();
        }
    }

    public void enviar(T mensaje) {
        try {
            queue.put(mensaje);
            System.out.println("Mensaje enviado: " + mensaje);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public T recibir() {
        try {
            T mensaje = queue.take();
            System.out.println("Mensaje recibido: " + mensaje);
            return mensaje;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Canal<String> canal = new Canal<>(10, true); // Canal sincrónico con capacidad limitada

        Thread emisor = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                canal.enviar("Mensaje " + i);
            }
        });

        Thread receptor = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                String mensaje = canal.recibir();
            }
        });

        emisor.start();
        receptor.start();
    }
}