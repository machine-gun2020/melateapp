import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@QuarkusMain
public class MelateMain {

    private static final Logger log = LoggerFactory.getLogger(MelateMain.class);
    public static void main(String... args) {
        System.out.println("🚀 Iniciando Microservicio de Sorteos");
        System.out.println("Pronostico");
        Quarkus.run(args);
    }
}


