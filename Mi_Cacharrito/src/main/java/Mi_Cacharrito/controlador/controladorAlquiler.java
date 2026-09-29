package Mi_Cacharrito.controlador;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Mi_Cacharrito.modelo.Alquiler;
import Mi_Cacharrito.modelo.Lista_Vehiculos;
import Mi_Cacharrito.repositorio.AlquilerR;
import Mi_Cacharrito.repositorio.lista_vehiculos;

@RestController
@RequestMapping("/Alquiler")
@CrossOrigin(origins = "http://localhost:4200")
public class controladorAlquiler {

    @Autowired
    private AlquilerR alquilerRepository;

    @Autowired
    private lista_vehiculos vehiculoRepository;

    public static class SolicitudAlquiler {
        public String identificacionUsuario;
        public String nombreUsuario;
        public String placa;
        public String fechaInicio;   
        public String fechaEntrega;  
    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearAlquiler(@RequestBody SolicitudAlquiler solicitud) {
        Lista_Vehiculos vehiculo = vehiculoRepository.findByPlaca(solicitud.placa);

        if (vehiculo == null) {
            return ResponseEntity.badRequest().body("No existe un vehículo con esa placa.");
        }

        if (!"disponible".equalsIgnoreCase(vehiculo.getEstado())) {
            return ResponseEntity.badRequest().body("El vehículo no está disponible actualmente.");
        }

        Alquiler alquiler = new Alquiler();
        alquiler.setIdentificacionUsuario(solicitud.identificacionUsuario);
        alquiler.setNombreUsuario(solicitud.nombreUsuario);
        alquiler.setFechaInicio(LocalDate.parse(solicitud.fechaInicio));
        alquiler.setFechaEntrega(LocalDate.parse(solicitud.fechaEntrega));
        alquiler.setPlaca(vehiculo.getPlaca());
        alquiler.setTipoVehiculo(vehiculo.getTipoVehiculo());
        alquiler.setColor(vehiculo.getColor());
        alquiler.setValorAlquiler(vehiculo.getValorAlquiler());
        alquiler.setEstado("pendiente de entrega");

        alquilerRepository.save(alquiler);

        vehiculo.setEstado("pendiente de entrega");
        vehiculoRepository.save(vehiculo);

        try {
            byte[] pdf = generarPdfAlquiler(alquiler);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "alquiler-" + alquiler.getNumeroAlquiler() + ".pdf");
            headers.add("X-Numero-Alquiler", String.valueOf(alquiler.getNumeroAlquiler())); 
            headers.add("Access-Control-Expose-Headers", "X-Numero-Alquiler");

            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Alquiler creado, pero falló la generación del PDF.");
        }
    }

    @PutMapping("/cancelar/{numeroAlquiler}")
    public ResponseEntity<?> cancelarAlquiler(@PathVariable Long numeroAlquiler) {
        return alquilerRepository.findById(numeroAlquiler).map(alquiler -> {
            alquiler.setEstado("cancelado");
            alquilerRepository.save(alquiler);

            Lista_Vehiculos vehiculo = vehiculoRepository.findByPlaca(alquiler.getPlaca());
            if (vehiculo != null) {
                vehiculo.setEstado("disponible");
                vehiculoRepository.save(vehiculo);
            }

            return ResponseEntity.ok("Alquiler cancelado correctamente.");
        }).orElse(ResponseEntity.notFound().build());
    }


    //  búsqueda por placa/número de alquiler
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarAlquiler(@RequestParam String tipo, @RequestParam String valor) {
        Optional<Alquiler> alquilerOpt = Optional.empty();

        if ("numero".equalsIgnoreCase(tipo)) {
            try {
                Long numAlquiler = Long.parseLong(valor);
                alquilerOpt = alquilerRepository.findById(numAlquiler);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("El número de alquiler ingresado no es válido.");
            }
        } else if ("placa".equalsIgnoreCase(tipo)) {
            alquilerOpt = alquilerRepository.findByPlacaAndEstado(valor, "entregado");
            if (!alquilerOpt.isPresent()) {
                alquilerOpt = alquilerRepository.findByPlacaAndEstado(valor, "pendiente de entrega");
            }
        } else {
            return ResponseEntity.badRequest().body("Tipo de búsqueda no válido. Use 'numero' o 'placa'.");
        }

        if (alquilerOpt.isPresent()) {
            return ResponseEntity.ok(alquilerOpt.get());
        } else {
            return ResponseEntity.status(404).body("No se encontró ningún alquiler activo con los datos proporcionados.");
        }
    }

    //  Búsqueda por número de alquiler + cambio a 'disponible' + cálculo de cobro días extra
    @PostMapping("/devolucion/{numeroAlquiler}")
    public ResponseEntity<?> procesarDevolucion(@PathVariable Long numeroAlquiler) {
        Optional<Alquiler> alquilerOpt = alquilerRepository.findById(numeroAlquiler);

        if (!alquilerOpt.isPresent()) {
            return ResponseEntity.status(404).body("No existe un alquiler registrado con ese número.");
        }

        Alquiler alquiler = alquilerOpt.get();
        LocalDate fechaEntregaPactada = alquiler.getFechaEntrega();
        LocalDate fechaActual = LocalDate.now();

        long diasExtra = 0;
        double valorDia = alquiler.getValorAlquiler();
        double cobroExtra = 0.0;

        if (fechaActual.isAfter(fechaEntregaPactada)) {
            diasExtra = ChronoUnit.DAYS.between(fechaEntregaPactada, fechaActual);
            cobroExtra = diasExtra * valorDia;
        }

        Lista_Vehiculos vehiculo = vehiculoRepository.findByPlaca(alquiler.getPlaca());
        if (vehiculo != null) {
            vehiculo.setEstado("disponible");
            vehiculoRepository.save(vehiculo);
        }

        alquiler.setEstado("devuelto");
        alquilerRepository.save(alquiler);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Vehículo devuelto exitosamente. Estado actualizado a disponible.");
        respuesta.put("numeroAlquiler", alquiler.getNumeroAlquiler());
        respuesta.put("placa", alquiler.getPlaca());
        respuesta.put("diasExtra", diasExtra);
        respuesta.put("cobroExtra", cobroExtra);
        respuesta.put("totalPagar", alquiler.getValorAlquiler() + cobroExtra);

        return ResponseEntity.ok(respuesta);
    }

    // listado de vehículos no entregados 
    @GetMapping("/no-entregados")
    public List<Alquiler> listarNoEntregados() {
        return alquilerRepository.findByEstado("pendiente de entrega");
    }


    private byte[] generarPdfAlquiler(Alquiler alquiler) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contenido.beginText();
                contenido.newLineAtOffset(50, 750);
                contenido.showText("Mi Cacharrito - Comprobante de Alquiler");
                contenido.endText();

                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                float y = 700;
                float saltoLinea = 20;

                String[] lineas = {
                    "Numero de alquiler: " + alquiler.getNumeroAlquiler(),
                    "Nombre del usuario: " + alquiler.getNombreUsuario(),
                    "Identificacion: " + alquiler.getIdentificacionUsuario(),
                    "Fecha de inicio: " + alquiler.getFechaInicio().format(formato),
                    "Fecha de entrega: " + alquiler.getFechaEntrega().format(formato),
                    "Tipo de vehiculo: " + alquiler.getTipoVehiculo(),
                    "Placa: " + alquiler.getPlaca(),
                    "Color: " + alquiler.getColor(),
                    "Valor del alquiler: $" + alquiler.getValorAlquiler(),
                    "Estado: " + alquiler.getEstado()
                };

                for (String linea : lineas) {
                    contenido.beginText();
                    contenido.newLineAtOffset(50, y);
                    contenido.showText(linea);
                    contenido.endText();
                    y -= saltoLinea;
                }
            }

            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            documento.save(salida);
            return salida.toByteArray();
        }
    }
    
    //cambiar el estado a entregado en el backend
    @PutMapping("/entregar/{numeroAlquiler}")
    public ResponseEntity<?> marcarEntregado(@PathVariable Long numeroAlquiler) {
        return alquilerRepository.findById(numeroAlquiler).map(alquiler -> {
            alquiler.setEstado("entregado");
            alquilerRepository.save(alquiler);
            return ResponseEntity.ok("Vehículo marcado como entregado.");
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pdf/{numeroAlquiler}")
    public ResponseEntity<?> descargarPdf(@PathVariable Long numeroAlquiler) {
        return alquilerRepository.findById(numeroAlquiler).map(alquiler -> {
            try {
                byte[] pdf = generarPdfAlquiler(alquiler);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "alquiler-" + alquiler.getNumeroAlquiler() + ".pdf");

                return ResponseEntity.ok().headers(headers).body(pdf);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("No se pudo generar el PDF.");
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}