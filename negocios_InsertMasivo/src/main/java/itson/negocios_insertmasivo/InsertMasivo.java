/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_insertmasivo;

import bos.BarberiaBO;
import bos.ClienteBO;
import bos.HorarioBO;
import bos.ResenaBO;
import bos.ServicioBO;
import dto.BarberiaDTO;
import dto.ClienteDTO;
import dto.HorarioDTO;
import dto.ResenaDTO;
import dto.ServicioDTO;
import dto.enums.RolUsuario;
import exceptions.BarberiaNoEncontradaException;
import exceptions.UsuarioDuplicadoException;
import interfaces.IBarberiaBO;
import interfaces.IClienteBO;
import interfaces.IHorarioBO;
import interfaces.IServicioBO;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class InsertMasivo implements IInsertMasivo {
    private final IBarberiaBO barberiaBO;
    private final IServicioBO servicioBO;
    private final IClienteBO  clienteBO;
    private final IHorarioBO  horarioBO;
    private final ResenaBO    resenaBO;

    public InsertMasivo() {
        this.barberiaBO = new BarberiaBO();
        this.servicioBO = new ServicioBO();
        this.clienteBO = new ClienteBO();
        this.horarioBO = new HorarioBO();
        this.resenaBO = new ResenaBO();
    }

    @Override
    public void insertMasivo() {
        // Primero clientes/admins — sus IDs se necesitan al crear las barberias
        // Clientes — siempre verifica por usuario
        insertarClientes();

        // Barberias
        try {
            if (barberiaBO.obtenerBarberiasActivas().isEmpty())
                insertarBarberias();
        } catch (Exception e) { insertarBarberias(); }

        // Servicios — verifica todas las barberias
        try {
            java.util.List<BarberiaDTO> todas = barberiaBO.obtenerBarberiasActivas();
            boolean sinServicios = todas.stream()
                    .allMatch(b -> servicioBO.obtenerServiciosPorBarberia(b.getId()).isEmpty());
            if (sinServicios) insertarServicios();
        } catch (Exception e) { insertarServicios(); }

        // Horarios — verifica todas las barberias
        try {
            java.util.List<BarberiaDTO> todas = barberiaBO.obtenerBarberiasActivas();
            boolean sinHorarios = todas.stream()
                    .allMatch(b -> horarioBO.obtenerHorariosPorBarberia(b.getId()).isEmpty());
            if (sinHorarios) insertarHorarios();
        } catch (Exception e) { insertarHorarios(); }

        // Resenas — verifica todas las barberias
        try {
            java.util.List<BarberiaDTO> todas = barberiaBO.obtenerBarberiasActivas();
            boolean sinResenas = todas.stream()
                    .allMatch(b -> resenaBO.obtenerPorBarberia(b.getId()).isEmpty());
            if (sinResenas) insertarResenas();
        } catch (Exception e) { insertarResenas(); }
    }

    // ----------------------------------------------------------------- Barberias

    private void insertarBarberias() {
        // Obtener los ids de los admins ya registrados
        String idElFilo    = idAdmin("admin_elfilo");
        String idSociety   = idAdmin("admin_society");
        String idDonRamon  = idAdmin("admin_donramon");
        String idKingsCut  = idAdmin("admin_kingscut");

        insertar(new BarberiaDTO("El Filo",
                "Blvd. Hidalgo 450, Col. Centro, Cd. Obregon",
                "662-210-3301",
                idElFilo,
                "Barbería clásica con más de 20 años de experiencia. "
                + "Especialistas en cortes tradicionales y afeitados con navaja."));

        insertar(new BarberiaDTO("Barber Society",
                "Av. Sonora 1120, Col. Pitic, Cd. Obregon",
                "662-318-8820",
                idSociety,
                "Estilo moderno y urbano. Especializados en cortes de tendencia, "
                + "degradados y diseños en cabello."));

        insertar(new BarberiaDTO("Don Ramón Barbershop",
                "Calle Guerrero 88, Col. San Benito, Cd. Obregon",
                "662-109-5540",
                idDonRamon,
                "Ambiente relajado y familiar. El lugar donde el estilo "
                + "clásico se encuentra con las tendencias actuales."));

        insertar(new BarberiaDTO("Kings Cut",
                "Blvd. Garcia Morales 2200, Plaza Centro, Cd. Obregon",
                "662-450-7712",
                idKingsCut,
                "Premium barbershop. Experiencia de lujo con productos "
                + "de alta gama y barberos certificados internacionalmente."));
    }

    // --------------------------------------------------------------- Reseñas

    private void insertarResenas() {
        try {
            String clienteId = clienteBO.obtenerPorUsuario("prueba").getId();

            BarberiaDTO elFilo        = barberiaBO.obtenerPorNombre("El Filo");
            BarberiaDTO barberSociety = barberiaBO.obtenerPorNombre("Barber Society");
            BarberiaDTO donRamon      = barberiaBO.obtenerPorNombre("Don Ramón Barbershop");
            BarberiaDTO kingsCut      = barberiaBO.obtenerPorNombre("Kings Cut");

            resenaBO.agregar(new ResenaDTO(clienteId, elFilo.getId(),
                    5, "Excelente servicio, el mejor corte que me han dado. Muy recomendado.",
                    "2026/04/10"));
            resenaBO.agregar(new ResenaDTO(clienteId, barberSociety.getId(),
                    4, "Buen ambiente y cortes modernos. Volvería sin duda.",
                    "2026/04/11"));
            resenaBO.agregar(new ResenaDTO(clienteId, donRamon.getId(),
                    5, "Ambiente familiar y precios justos. El corte quedó perfecto.",
                    "2026/04/12"));
            resenaBO.agregar(new ResenaDTO(clienteId, kingsCut.getId(),
                    5, "Experiencia premium, vale cada peso. El masaje incluido es increíble.",
                    "2026/04/13"));

            System.out.println("[InsertMasivo] Reseñas insertadas por barbería");
        } catch (Exception e) {
            System.out.println("Error insertando reseñas: " + e.getMessage());
        }
    }

    private String idAdmin(String usuario) {
        try {
            ClienteDTO admin = clienteBO.obtenerPorUsuario(usuario);
            if (admin != null) {
            return admin.getId();
        }
        return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void insertar(BarberiaDTO barberia) {
        try {
            barberiaBO.registrar(barberia);
        } catch (Exception e) {
            System.out.println("Error insertando barbería: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------- Servicios

    private void insertarServicios() {
        try {
            BarberiaDTO elFilo        = barberiaBO.obtenerPorNombre("El Filo");
            BarberiaDTO barberSociety = barberiaBO.obtenerPorNombre("Barber Society");
            BarberiaDTO donRamon      = barberiaBO.obtenerPorNombre("Don Ramón Barbershop");
            BarberiaDTO kingsCut      = barberiaBO.obtenerPorNombre("Kings Cut");

            // El Filo
            insertarServicio(elFilo.getId(), "Corte clásico",
                    "Corte tradicional con tijera y peine", 120.0, 30);
            insertarServicio(elFilo.getId(), "Afeitado con navaja",
                    "Afeitado clásico con navaja, toalla caliente y crema artesanal", 150.0, 30);
            insertarServicio(elFilo.getId(), "Corte + Barba",
                    "Combo completo: corte clásico y arreglo de barba", 230.0, 30);
            insertarServicio(elFilo.getId(), "Arreglo de barba",
                    "Perfilado y arreglo de barba con productos premium", 100.0, 25);

            // Barber Society
            insertarServicio(barberSociety.getId(), "Degradado moderno",
                    "Fade o degradado con máquina, acabado limpio y definido", 150.0, 30);
            insertarServicio(barberSociety.getId(), "Corte + Diseño",
                    "Corte a tu medida con diseño personalizado en las líneas", 180.0, 30);
            insertarServicio(barberSociety.getId(), "Tintura completa",
                    "Cambio de color completo con tinte profesional", 350.0, 30);
            insertarServicio(barberSociety.getId(), "Mechas / Highlights",
                    "Mechas o highlights en secciones seleccionadas", 280.0, 30);
            insertarServicio(barberSociety.getId(), "Barba + Diseño",
                    "Arreglo de barba con diseño de líneas y perfilado", 130.0, 30);

            // Don Ramón
            insertarServicio(donRamon.getId(), "Corte estándar",
                    "Corte de cabello a tu estilo con tijera o máquina", 100.0, 30);
            insertarServicio(donRamon.getId(), "Corte infantil",
                    "Corte especial para niños hasta 12 años", 80.0, 25);
            insertarServicio(donRamon.getId(), "Corte + Barba clásico",
                    "Corte de cabello y arreglo de barba en un solo servicio", 200.0, 30);
            insertarServicio(donRamon.getId(), "Tratamiento capilar",
                    "Hidratación profunda y tratamiento anticaída", 220.0, 30);

            // Kings Cut
            insertarServicio(kingsCut.getId(), "Corte premium",
                    "Corte personalizado con consulta de estilo incluida", 220.0, 30);
            insertarServicio(kingsCut.getId(), "Experiencia completa",
                    "Corte + barba + masaje de cuero cabelludo + productos premium", 450.0, 30);
            insertarServicio(kingsCut.getId(), "Barba de caballero",
                    "Afeitado real con navaja, aceites esenciales y masaje facial", 200.0, 30);
            insertarServicio(kingsCut.getId(), "Tratamiento de keratina",
                    "Tratamiento alisador y nutritivo con keratina de alta calidad", 500.0, 30);
            insertarServicio(kingsCut.getId(), "Color + Corte",
                    "Tintura profesional con corte y acabado incluido", 600.0, 30);

        } catch (BarberiaNoEncontradaException e) {
            System.out.println("Error obteniendo barbería para servicios: " + e.getMessage());
        }
    }

    private void insertarServicio(String barberiaId, String nombre,
            String descripcion, double precio, int duracion) {
        try {
            ServicioDTO s = new ServicioDTO(barberiaId, nombre, descripcion, precio, duracion);
            servicioBO.registrar(s);
        } catch (Exception e) {
            System.out.println("Error insertando servicio: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------- Clientes

    private void insertarHorarios() {
        try {
            BarberiaDTO elFilo        = barberiaBO.obtenerPorNombre("El Filo");
            BarberiaDTO barberSociety = barberiaBO.obtenerPorNombre("Barber Society");
            BarberiaDTO donRamon      = barberiaBO.obtenerPorNombre("Don Ramón Barbershop");
            BarberiaDTO kingsCut      = barberiaBO.obtenerPorNombre("Kings Cut");

            // El Filo — Lu-Sa 08:00-18:00, cerrado domingo
            insertarHorariosBarberia(elFilo.getId(),
                    new String[]{"LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO"},
                    "08:00", "18:00");

            // Barber Society — Lu-Vi 09:00-19:00, Sa 10:00-17:00, cerrado domingo
            insertarHorariosBarberia(barberSociety.getId(),
                    new String[]{"LUNES","MARTES","MIERCOLES","JUEVES","VIERNES"},
                    "09:00", "19:00");
            insertarHorariosBarberia(barberSociety.getId(),
                    new String[]{"SABADO"},
                    "10:00", "17:00");

            // Don Ramón — Ma-Sa 10:00-20:00, cerrado lunes y domingo
            insertarHorariosBarberia(donRamon.getId(),
                    new String[]{"MARTES","MIERCOLES","JUEVES","VIERNES","SABADO"},
                    "10:00", "20:00");

            // Kings Cut — todos los días 09:00-21:00
            insertarHorariosBarberia(kingsCut.getId(),
                    new String[]{"LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO","DOMINGO"},
                    "09:00", "21:00");

            System.out.println("[InsertMasivo] Horarios distintos insertados por barberia");

        } catch (exceptions.BarberiaNoEncontradaException e) {
            System.out.println("Error insertando horarios: " + e.getMessage());
        }
    }

    private void insertarHorariosBarberia(String barberiaId, String[] dias,
            String apertura, String cierre) {
        for (String dia : dias) {
            horarioBO.registrar(new HorarioDTO(barberiaId, dia, apertura, cierre));
        }
    }

    private void insertarClientes() {
        try {
            clienteBO.registrar(new ClienteDTO(
                    "Usuario", "Prueba",
                    "prueba@correo.com", "662-000-0000",
                    "prueba", "1234",
                    RolUsuario.CLIENTE));
            System.out.println("[InsertMasivo] Cliente de prueba  -> usuario: prueba | contraseña: 1234");
        } catch (UsuarioDuplicadoException e) {
            // Ya existe, no hacer nada
        }
        // Admin: si ya existe pero con rol incorrecto, lo actualiza
        insertarOActualizarAdmin();
    }

    private void insertarOActualizarAdmin() {
        // Admin global de respaldo
        try {
            clienteBO.registrar(new ClienteDTO(
                    "Admin", "Barberia",
                    "admin@barberia.com", "662-111-0000",
                    "admin", "1234",
                    RolUsuario.BARBERO));
            System.out.println("[InsertMasivo] admin       / 1234 — Admin global");
        } catch (UsuarioDuplicadoException e) {
            clienteBO.confirmarRolBarbero("admin");
        }
        // Un admin por cada barberia
        insertarAdminBarberia("admin_elfilo",   "Admin",   "ElFilo",
                "elfilo@barberia.com",   "662-001-0001", "El Filo");
        insertarAdminBarberia("admin_society",  "Admin",   "Society",
                "society@barberia.com",  "662-002-0002", "Barber Society");
        insertarAdminBarberia("admin_donramon", "Admin",   "DonRamon",
                "donramon@barberia.com", "662-003-0003", "Don Ramón Barbershop");
        insertarAdminBarberia("admin_kingscut", "Admin",   "KingsCut",
                "kingscut@barberia.com", "662-004-0004", "Kings Cut");
    }

    private void insertarAdminBarberia(String usuario, String nombre, String apellido,
            String correo, String telefono, String nombreBarberia) {
        try {
            clienteBO.registrar(new ClienteDTO(
                    nombre, apellido, correo, telefono,
                    usuario, "1234", RolUsuario.BARBERO));
            System.out.println("[InsertMasivo] " + usuario + " / 1234 — " + nombreBarberia);
        } catch (UsuarioDuplicadoException e) {
            clienteBO.confirmarRolBarbero(usuario);
        }
    }
}
