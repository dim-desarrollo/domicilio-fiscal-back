package com.domicilioFiscal.df.Controllers.ImpController;


import com.domicilioFiscal.df.Controllers.InterfaceController.IntNotificationController;
import com.domicilioFiscal.df.Models.Dtos.NotificationGet;
import com.domicilioFiscal.df.Models.Dtos.NotificationPost;
import com.domicilioFiscal.df.Models.Entitys.Notification;
import com.domicilioFiscal.df.Services.InterfaceServices.FileServices;
import com.domicilioFiscal.df.Services.InterfaceServices.IntNotificationServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImpNotificationController implements IntNotificationController {

    private final IntNotificationServices intNotificationServices;
    private final ModelMapper modelMapper;

    private final FileServices fileServices;

    //@Operation(summary = "Elimina la notificacion por Id", description = "Retorna un booleano")
    @DeleteMapping(value = "/delete-by/{id}")
    public ResponseEntity DeleteById(@PathVariable("id") @Positive long id) {

        log.info(("[NotificationController - delete-by-id: id={}"), id);

        return ResponseEntity.ok(intNotificationServices.deletNoficationById(id));
    }

    //@Operation(summary = "Agrega una notificacion con pdf", description = "Retorna un Mensaje")
    @PostMapping(value = "/add-notification")
    public ResponseEntity<String> AddNotification(@RequestParam("files") MultipartFile files, @RequestParam("jsonData") String jsonData) throws JsonProcessingException {

        Date currentDate = new Date();

        // Formatear la fecha como una cadena
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String contentType = files.getContentType();

        if (contentType != null && contentType.equals("application/pdf")) {


            try {

                final NotificationPost notificationPost = objectMapper.readValue(jsonData, NotificationPost.class);

                String NameFile = files.getOriginalFilename();

                NameFile = NameFile.replace(".pdf", "");

                formattedDate = formattedDate.replace(" ", "-");

                formattedDate = formattedDate.replace(":", "-");

                NameFile = NameFile.replace(" ", "-");

                notificationPost.setPdf(NameFile + "-" + formattedDate);

                log.info(("[Nota NameFile {} + formatterdDate {}]"), NameFile,formattedDate);

                fileServices.save(files);

                Path source = Paths.get("uploads/" + files.getOriginalFilename());

                if (Files.exists(source)) {

                    // Cambia el nombre si el archivo de destino ya existe
                    Path destination = Paths.get("uploads/" + notificationPost.getPdf() + ".pdf");

                    Files.move(source, destination);

                    System.out.println("entre");

                    final Notification notification = modelMapper.map(notificationPost, Notification.class);

                    log.info(("[AddNotification - Notification {}]"), notification);

                    intNotificationServices.addNotification(notification);

                }

                return ResponseEntity.status(HttpStatus.OK).body("Notification subida con exito.");

            } catch (Exception e) {

                System.out.println(e);
                return ResponseEntity.status(HttpStatus.OK).body("Se produjo un problema a la hora se subir la Notificacion o PDF.");

            }

        } else {
            // El archivo no es de tipo PDF, devuelve una respuesta de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo no es de tipo PDF o no contiene PDF.");
        }

    }

    //@Operation(summary = "Agrega archivos sin notificacion.", description = "Retorna una mensaje de subido o no el archivo")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile files) throws Exception {

        log.info(("[uploadFiles - upload: files={}]"), files.getOriginalFilename());

        try {

            fileServices.save(files);
            return ResponseEntity.status(HttpStatus.OK).body("Los archivos fueron cargados correctamente al servidor");

        } catch (Exception e) {

            System.out.println(e);
            return ResponseEntity.status(HttpStatus.OK).body("No se pudo subir el archivo al servidor");

        }

    }

    //@Operation(summary = "Muestra el p  df.", description = "Retorna el pdf para descargar")
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {

        Resource resource = fileServices.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

    @Override
    //@Operation(summary = "Listar todas las notificaciones", description = "Retorna todas las notificacion")
    @CrossOrigin(origins = "*") // Configura el origen permitido
    @GetMapping(value = "/to-list")
    public ResponseEntity<List<NotificationGet>> GetAllNotification() {
        log.info(("[NotificationController - to-list]"));
        return ResponseEntity.ok(intNotificationServices.getNotification().stream().map(x -> modelMapper.map(x, NotificationGet.class)).toList());
    }

    //@Operation(summary = "Busca la notificaciones por Cuit", description = "Retorna una notificacion por cuit")
    @GetMapping(value = "/to-list-by/{cuit}")
    public ResponseEntity<List<NotificationGet>> getAllnotificationbyCuit(@PathVariable("cuit") @Positive @Size(min = 10, max = 10) int cuit) {
        log.info(("[NotificationController - to-list-by-cuit: cuit={}]"), cuit);

        return ResponseEntity.ok(intNotificationServices.getNotificationByCuit(cuit).stream().map(x -> modelMapper.map(x, NotificationGet.class)).toList());
    }

    //@Size(min = 10, max = 10)
//    @CrossOrigin(origins = "http://172.20.254.136:5173")
    //@Operation(summary = "Notifica como leida la notificacion", description = "Retorna un boolean = true por el momento")
    @PutMapping(value = "/notificated/{id}")
    public ResponseEntity<Boolean> modifyNotificationbyCuit(@PathVariable @Positive Long id) {

        log.info(("[NotificationController - notificated/id={}]"), id);
        intNotificationServices.Notificated(id);
        return ResponseEntity.ok(true);

    }

}


/*

    @Override
    public ResponseEntity<Collection<ChatPropiedades>> buscarTodos() {
        log.info("[ChatControlador - BuscarTodos]");

        final List<ChatPropiedades> chatPropiedadesLista = chatServicio.buscarTodos()
                .stream()
                .map(u -> modelMapper.map(u, ChatPropiedades.class))
                .toList();
        return new ResponseEntity<>(chatPropiedadesLista, HttpStatus.OK);
    }

 */