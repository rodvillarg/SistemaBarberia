package conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Manejador de conexión a MongoDB.
 * Implementa el patrón Singleton para garantizar una única instancia
 * de conexión a la base de datos durante toda la ejecución.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ManejadorConexion {

    private static ManejadorConexion instancia;

    private MongoClient cliente;
    private MongoDatabase baseDatos;

    private static final String URI = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "SistemaBarberia";

    private ManejadorConexion() {
        try {
            CodecRegistry pojoCodecRegistry = fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder()
                            .register("dominio")
                            .register("dto.enums")
                            .automatic(true)
                            .build())
            );
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(URI))
                    .codecRegistry(pojoCodecRegistry)
                    .build();
            cliente = MongoClients.create(settings);
            baseDatos = cliente.getDatabase(NOMBRE_BD);
            System.out.println("Conexión a SistemaBarberia establecida correctamente.");
        } catch (Exception ex) {
            System.err.println("Error al conectar con la base de datos: " + ex.getMessage());
            throw new RuntimeException("No se pudo conectar a la base de datos.", ex);
        }
    }

    /**
     * Retorna la instancia única del manejador de conexión (Singleton).
     *
     * @return Instancia única de ManejadorConexion.
     */
    public static synchronized ManejadorConexion getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorConexion();
        }
        return instancia;
    }

    /**
     * Retorna la base de datos activa.
     *
     * @return Instancia de MongoDatabase.
     */
    public MongoDatabase getBaseDatos() {
        return baseDatos;
    }

    /**
     * Cierra la conexión con MongoDB.
     */
    public void cerrar() {
        if (cliente != null) {
            cliente.close();
            System.out.println("Conexión a la base de datos cerrada.");
        }
    }
}
